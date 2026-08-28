package com.coremod.nextech.machine.flame;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.network.chat.Component;

import lombok.Getter;

import java.util.List;
import java.util.Map;

public class FLAMEMachine extends CoilWorkableElectricMultiblockMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            FLAMEMachine.class,
            CoilWorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    @Getter
    protected int temperature;

    @Getter
    private final int baseTemperature;

    private final int activeTempLoss;
    private final int dormantTempLoss;

    protected TickableSubscription tryTickSub;
    private boolean startHeatLoss;

    public int maxParallels = 1;

    public FLAMEMachine(
                        IMachineBlockEntity holder,
                        int baseTemperature,
                        int activeTempLoss,
                        int dormantTempLoss,
                        Object... args) {
        super(holder);

        this.temperature = baseTemperature;
        this.baseTemperature = baseTemperature;
        this.activeTempLoss = activeTempLoss;
        this.dormantTempLoss = dormantTempLoss;
        this.startHeatLoss = false;
    }

    @Override
    protected RecipeLogic createRecipeLogic(Object... args) {
        return new FLAMERecipeLogic(this);
    }

    public static final Map<Material, Integer> FLAME_FUEL_HEAT = Map.of(
            GTMaterials.get("inactivated_infernality"), 150,
            GTMaterials.get("infernality_catalysm"), 400);

    public double getHeatingEfficiencyMultiplier() {
        int coilTemperature = getCoilType().getCoilTemperature();

        int efficiencySteps = 0;
        int threshold = 1024;

        while (coilTemperature >= threshold) {
            efficiencySteps++;
            threshold *= 4;
        }

        return Math.pow(1.05D, efficiencySteps);
    }

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

            if (fluidCap >= temperature &&
                    fluidCap < smallestCapAboveTemperature) {
                smallestCapAboveTemperature = fluidCap;
                selectedFluid = entry.getKey();
            }
        }

        return selectedFluid;
    }

    public void setTemperature(int temperature) {
        this.temperature = Math.max(
                temperature,
                this.baseTemperature);

        this.temperatureChanged();
    }

    public void addTemperature(int amount, int heatCap) {
        this.temperature = Math.min(
                this.temperature + amount,
                heatCap);

        this.temperatureChanged();
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
        int interval = machineActive ? 100 : 50;
        if (getOffsetTimer() % interval != 0 ||
                !this.startHeatLoss) {
            return;
        }
        if (machineActive) {
            double parallelMultiplier = maxParallels > 1 ? Math.log(maxParallels) / Math.log(2) : 1.0;
            int loss = (int) (activeTempLoss * parallelMultiplier);
            this.temperature = Math.max(
                    this.temperature - loss,
                    baseTemperature);
        } else {
            this.temperature = Math.max(
                    this.temperature - dormantTempLoss,
                    baseTemperature);
        }
        this.temperatureChanged();
    }

    private void temperatureChanged() {}
}
