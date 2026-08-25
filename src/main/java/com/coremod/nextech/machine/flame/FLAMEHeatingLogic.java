package com.coremod.nextech.machine.flame;

import com.coremod.nextech.NexTechRecipeTypes;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableFluidTank;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType.ICustomRecipeLogic;
import com.gregtechceu.gtceu.common.data.GTRecipeCategories;

import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FLAMEHeatingLogic implements ICustomRecipeLogic {

    @Override
    public void buildRepresentativeRecipes() {
        FLAMEMachine.fluidsMap.forEach((material, heat) -> {
            FluidStack heatingFluidInput = material.getFluid(1000);
            GTRecipe heatingRecipe = NexTechRecipeTypes.FLAME_RECIPES
                    .recipeBuilder(material.getName() + "_flame_heating")
                    .inputFluids(heatingFluidInput)
                    .duration(64)
                    .EUt(GTValues.V[GTValues.UHV])
                    .addData("heat_per_cycle",
                            material.getFluid(1).getFluid().getFluidType().getTemperature() / 1_000_000)
                    .addData("heat_cap", heat)
                    .buildRawRecipe();
            heatingRecipe.setId(heatingRecipe.getId().withPrefix("/"));
            NexTechRecipeTypes.FLAME_RECIPES.addToCategoryMap(
                    GTRecipeCategories.get("flame_heating"),
                    heatingRecipe);
        });
    }

    @Override
    public @Nullable GTRecipe createCustomRecipe(IRecipeCapabilityHolder holder) {
        List<NotifiableFluidTank> handlers = Objects
                .requireNonNullElseGet(holder.getCapabilitiesFlat(IO.IN, FluidRecipeCapability.CAP),
                        Collections::emptyList)
                .stream()
                .filter(NotifiableFluidTank.class::isInstance)
                .map(NotifiableFluidTank.class::cast)
                .filter(i -> i.getTanks() >= 1)
                .toList();

        if (handlers.isEmpty()) return null;

        for (NotifiableFluidTank handler : handlers) {
            GTRecipe recipe = createHeatingRecipe(handler);
            if (recipe != null) return recipe;
        }

        return null;
    }

    private GTRecipe createHeatingRecipe(NotifiableFluidTank handler) {
        for (int i = 0; i < handler.getTanks(); ++i) {
            FluidStack fluidInSlot = handler.getFluidInTank(i);

            if (!fluidInSlot.isEmpty()) {
                Material fluidMaterial = ChemicalHelper.getMaterial(fluidInSlot.getFluid());
                System.out.println(
                        "[NexTech] heating fluid: " + fluidInSlot.getFluid() + " -> material: " + fluidMaterial);

                if (FLAMEMachine.fluidsMap.containsKey(fluidMaterial)) {
                    FluidStack fluidInput = fluidInSlot.copy();
                    fluidInput.setAmount(1000);

                    return NexTechRecipeTypes.FLAME_RECIPES
                            .recipeBuilder("heating")
                            .inputFluids(fluidInput)
                            .duration(64)
                            .EUt(GTValues.V[GTValues.UHV])
                            .buildRawRecipe();
                }
            }
        }

        return null;
    }
}
