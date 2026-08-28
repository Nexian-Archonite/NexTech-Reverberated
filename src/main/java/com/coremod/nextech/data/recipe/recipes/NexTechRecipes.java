package com.coremod.nextech.data.recipe.recipes;

import com.coremod.nextech.NexTechMaterialFlags;
import com.coremod.nextech.NexTechTagPrefixes;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.FORGE_HAMMER_RECIPES;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.FORMING_PRESS_RECIPES;

public class NexTechRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        materialFlagRecipes(provider);
    }

    public static void materialFlagRecipes(Consumer<FinishedRecipe> provider) {
        for (Material material : GTCEuAPI.materialManager.getRegisteredMaterials()) {
            if (material.hasFlag(MaterialFlags.DISABLE_MATERIAL_RECIPES)) {
                continue;
            }

            if (material.hasFlag(NexTechMaterialFlags.GENERATE_CHAINLET)) {
                FORGE_HAMMER_RECIPES.recipeBuilder(String.format("hammer_%s_chainlet", material.getName()))
                        .inputItems(TagPrefix.ring, material)
                        .outputItems(NexTechTagPrefixes.chainlet, material)
                        .duration((int) (material.getMass() * 0.125))
                        .EUt(GTValues.VA[GTValues.LV])
                        .save(provider);
            }

            if (material.hasFlag(NexTechMaterialFlags.GENERATE_CHAIN_MESH)) {
                FORMING_PRESS_RECIPES.recipeBuilder(String.format("press_%s_chain_mesh", material.getName()))
                        .inputItems(NexTechTagPrefixes.chainlet, material, 16)
                        .outputItems(NexTechTagPrefixes.chainMesh, material)
                        .duration((int) (material.getMass() * 4))
                        .EUt(GTValues.VA[GTValues.LV])
                        .save(provider);
            }
        }
    }
}
