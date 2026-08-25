package com.coremod.nextech.machine.flame;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;

public class FLAMERecipeModifier {

    public static final RecipeModifier FLAME_OC = FLAMERecipeModifier::flameOverclock;

    public static ModifierFunction flameOverclock(MetaMachine machine, GTRecipe recipe) {
        if (!(machine instanceof FLAMEMachine flameMachine)) {
            return RecipeModifier.nullWrongType(FLAMEMachine.class, machine);
        }

        if (!recipe.data.contains("ebf_temp")) {
            return ModifierFunction.IDENTITY;
        }

        int flameTemp = flameMachine.getTemperature();
        int recipeTemp = recipe.data.getInt("ebf_temp");

        if (recipeTemp > flameTemp) {
            return ModifierFunction.NULL;
        }

        double intervals = Math.floor(Math.max(0.0, (flameTemp - recipeTemp) / 50.0));

        // parallels: 2^n
        int parallels = (int) Math.pow(2.0, intervals);
        int maxParallels = ParallelLogic.getParallelAmountWithoutEU(machine, recipe, parallels);

        // duration reduction: 0.95^n compounding
        double durationMultiplier = Math.pow(0.95, intervals);

        return ModifierFunction.builder()
                .modifyAllContents(ContentModifier.multiplier(maxParallels))
                .parallels(maxParallels)
                .durationMultiplier(durationMultiplier)
                .build();
    }
}
