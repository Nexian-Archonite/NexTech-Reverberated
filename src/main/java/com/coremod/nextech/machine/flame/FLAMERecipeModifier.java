package com.coremod.nextech.machine.flame;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;

public class FLAMERecipeModifier {

    public static final RecipeModifier FLAME_OC = FLAMERecipeModifier::flameOverclock;

    public static ModifierFunction flameOverclock(
                                                  MetaMachine machine,
                                                  GTRecipe recipe) {
        if (!(machine instanceof FLAMEMachine flameMachine)) {
            return RecipeModifier.nullWrongType(
                    FLAMEMachine.class,
                    machine);
        }

        /*
         * Fuel recipes NEVER receive this modifier.
         */
        if (recipe.data.contains("flame_fuel") &&
                recipe.data.getBoolean("flame_fuel")) {

            return ModifierFunction.IDENTITY;
        }

        /*
         * Processing recipes need ebf_temp.
         */
        if (!recipe.data.contains("ebf_temp")) {
            return ModifierFunction.IDENTITY;
        }

        int flameTemp = flameMachine.getTemperature();

        int recipeTemp = recipe.data.getInt("ebf_temp");

        /*
         * Not hot enough.
         */
        if (recipeTemp > flameTemp) {
            return ModifierFunction.NULL;
        }

        int intervals = Math.max(
                0,
                (flameTemp - recipeTemp) / 100);

        /*
         * 2^n parallels.
         */
        int parallelIntervals = Math.min(intervals, 5);
        int requestedParallels = 1 << parallelIntervals;

        int maxParallels = ParallelLogic.getParallelAmountWithoutEU(
                machine,
                recipe,
                requestedParallels);
        flameMachine.maxParallels = maxParallels; // set it on the machine

        /*
         * 0.95^n duration.
         */
        double durationMultiplier = Math.pow(0.95D, intervals);

        return ModifierFunction.builder()
                .modifyAllContents(
                        ContentModifier.multiplier(
                                maxParallels))
                .parallels(maxParallels)
                .durationMultiplier(durationMultiplier)
                .build();
    }
}
