package com.coremod.nextech.machine.reaction;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;

public class ReactionModifiers {

    public static final RecipeModifier VAT_TEMPERATURE =
            ReactionModifiers::vatTemperature;

    public static final RecipeModifier PRESSURE =
            ReactionModifiers::pressure;

    public static ModifierFunction vatTemperature(
            MetaMachine machine,
            GTRecipe recipe) {

        if (!(machine instanceof CoilWorkableElectricMultiblockMachine coilMachine)) {
            return RecipeModifier.nullWrongType(
                    CoilWorkableElectricMultiblockMachine.class,
                    machine);
        }

        int temperature =
                coilMachine.getCoilType().getCoilTemperature();

        int recipeTemp =
                recipe.data.getInt("Temp");

        if (recipeTemp * 2.718 > temperature) {
            return ModifierFunction.NULL;
        }

        return ModifierFunction.IDENTITY;
    }

    public static ModifierFunction pressure(
            MetaMachine machine,
            GTRecipe recipe) {

        return ModifierFunction.IDENTITY;
    }
}