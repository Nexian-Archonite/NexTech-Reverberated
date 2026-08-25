package com.coremod.nextech.machine.flame;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;

import lombok.Getter;

import java.util.List;
import java.util.Map;

public class FLAMEMachine extends WorkableElectricMultiblockMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            FLAMEMachine.class,
            WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    @Getter
    protected int temperature;

    @Getter
    private final int baseTemperature;

    private final int activeTempLoss;
    private final int dormantTempLoss;

    protected TickableSubscription tryTickSub;
    private boolean startHeatLoss;

    public FLAMEMachine(
                        IMachineBlockEntity holder,
                        int baseTemperature,
                        int activeTempLoss,
                        int dormantTempLoss,
                        Object... args) {
        super(holder, args);

        this.temperature = baseTemperature;
        this.baseTemperature = baseTemperature;
        this.activeTempLoss = activeTempLoss;
        this.dormantTempLoss = dormantTempLoss;
        this.startHeatLoss = false;
    }

    public static final Map<Material, Integer> FLAME_FUEL_HEAT = Map.of(
            GTMaterials.get("inactivated_infernality"), 150,
            GTMaterials.get("infernality_catalysm"), 400);

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();

        this.startHeatLoss = true;
        this.temperatureChanged();
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);

        textList.add(
                Component.translatable(
                        "ui.nextech.flame_crucible",
                        this.temperature));
    }

    public static Material getFLAMEHeatingLiquid(int temperature) {
        Material selectedFluid = null;
        int smallestCapAboveTemperature = Integer.MAX_VALUE;

        for (Map.Entry<Material, Integer> entry : FLAME_FUEL_HEAT.entrySet()) {
            int fluidCap = entry.getValue();

            if (fluidCap >= temperature && fluidCap < smallestCapAboveTemperature) {
                smallestCapAboveTemperature = fluidCap;
                selectedFluid = entry.getKey();
            }
        }

        return selectedFluid;
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public void onLoad() {
        super.onLoad();

        if (getLevel().isClientSide) {
            return;
        }

        tryTickSub = subscribeServerTick(
                tryTickSub,
                this::tryRemoveHeat);
    }

    @Override
    public void onUnload() {
        super.onUnload();

        if (getLevel().isClientSide) {
            return;
        }

        if (tryTickSub != null) {
            tryTickSub.unsubscribe();
            tryTickSub = null;
        }
    }

    protected void tryRemoveHeat() {
        boolean machineActive = getRecipeLogic().isWorking();
        int interval = machineActive ? 400 : 200;

        if (getOffsetTimer() % interval != 0 || !this.startHeatLoss) {
            return;
        }

        if (machineActive) {
            this.temperature = Math.max(
                    this.temperature - activeTempLoss,
                    baseTemperature);
        } else {
            this.temperature = Math.max(
                    this.temperature - dormantTempLoss,
                    baseTemperature);
        }

        this.temperatureChanged();
    }

    public void onFuelRecipeCompleted(GTRecipe recipe) {
        List<Content> content = recipe.getInputContents(
                FluidRecipeCapability.CAP);

        if (content.isEmpty()) {
            return;
        }

        for (Content entry : content) {
            if (!(entry.getContent() instanceof FluidIngredient ingredient)) {
                continue;
            }

            FluidStack[] stacks = ingredient.getStacks();

            if (stacks.length == 0) {
                continue;
            }

            FluidStack fuelStack = stacks[0];

            if (fuelStack.isEmpty()) {
                continue;
            }

            Material material = ChemicalHelper.getMaterial(
                    fuelStack.getFluid());

            Integer maxHeat = FLAME_FUEL_HEAT.get(material);

            if (maxHeat == null) {
                continue;
            }

            int heatPerUnit = fuelStack.getFluid()
                    .getFluidType()
                    .getTemperature() / 1_000_000;

            int amountInBuckets = fuelStack.getAmount() / 1000;
            int amountToAdd = heatPerUnit * amountInBuckets;

            this.temperature = Math.min(
                    this.temperature + amountToAdd,
                    maxHeat);

            this.temperatureChanged();
            return;
        }
    }

    private void temperatureChanged() {}
}
