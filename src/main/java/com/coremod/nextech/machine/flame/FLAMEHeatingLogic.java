package com.coremod.nextech.machine.flame;

import com.coremod.nextech.NexTechRecipeCategories;
import com.coremod.nextech.NexTechRecipeTypes;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType.ICustomRecipeLogic;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;

import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FLAMEHeatingLogic implements ICustomRecipeLogic {

    public static final FLAMEHeatingLogic INSTANCE = new FLAMEHeatingLogic();

    @Override
    public void buildRepresentativeRecipes() {
        FLAMEMachine.FLAME_FUEL_HEAT.forEach((material, heat) -> {
            GTRecipe heatingRecipe = NexTechRecipeTypes.FLAME_RECIPES
                    .recipeBuilder(material.getName() + "_flame_heating")
                    .input(FluidRecipeCapability.CAP, FluidIngredient.of(material.getFluid(1000)))
                    .duration(64)
                    .EUt(GTValues.V[GTValues.UHV])
                    .addData("flame_fuel", true)
                    .addData("heat_per_cycle",
                            material.getFluid(1).getFluid().getFluidType().getTemperature() / 1_000_000)
                    .addData("heat_cap", heat)
                    .buildRawRecipe();

            heatingRecipe.setId(heatingRecipe.getId().withPrefix("/"));

            NexTechRecipeTypes.FLAME_RECIPES.addToCategoryMap(
                    NexTechRecipeCategories.FLAME_HEATING,
                    heatingRecipe);
        });
    }

    @Override
    public @Nullable GTRecipe createCustomRecipe(IRecipeCapabilityHolder holder) {
        if (!(holder instanceof FLAMEMachine flameMachine)) {
            return null;
        }

        List<FluidStack> fluids = holder
                .getCapabilitiesFlat(IO.IN, FluidRecipeCapability.CAP)
                .stream()
                .filter(capability -> capability instanceof com.gregtechceu.gtceu.api.machine.trait.NotifiableFluidTank)
                .map(capability -> (com.gregtechceu.gtceu.api.machine.trait.NotifiableFluidTank) capability)
                .flatMap(tank -> {
                    List<FluidStack> stacks = new ArrayList<>();

                    for (int i = 0; i < tank.getTanks(); i++) {
                        FluidStack stack = tank.getFluidInTank(i);

                        if (!stack.isEmpty()) {
                            stacks.add(stack.copy());
                        }
                    }

                    return stacks.stream();
                })
                .toList();

        double efficiencyMultiplier = flameMachine.getHeatingEfficiencyMultiplier();

        for (FluidStack fluid : fluids) {
            Material material = ChemicalHelper.getMaterial(fluid.getFluid());

            Integer baseHeatCap = FLAMEMachine.FLAME_FUEL_HEAT.get(material);

            if (baseHeatCap == null) {
                continue;
            }

            int baseHeatPerCycle = fluid.getFluid()
                    .getFluidType()
                    .getTemperature() / 1_000_000;

            int heatPerCycle = (int) Math.ceil(
                    baseHeatPerCycle * efficiencyMultiplier);

            int heatCap = (int) Math.ceil(
                    baseHeatCap * efficiencyMultiplier);

            if (flameMachine.getTemperature() + heatPerCycle > heatCap) {
                continue;
            }

            int fluidAmount = (int) Math.ceil(
                    1000D / efficiencyMultiplier);

            FluidStack recipeFluid = fluid.copy();
            recipeFluid.setAmount(fluidAmount);

            return NexTechRecipeTypes.FLAME_RECIPES
                    .recipeBuilder("heating")
                    .input(FluidRecipeCapability.CAP, FluidIngredient.of(recipeFluid))
                    .duration(64)
                    .EUt(GTValues.V[GTValues.UHV])
                    .addData("flame_fuel", true)
                    .addData("heat_per_cycle", heatPerCycle)
                    .addData("heat_cap", heatCap)
                    .buildRawRecipe();
        }

        return null;
    }
}
