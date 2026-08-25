package com.coremod.nextech;

import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.category.GTRecipeCategory;
import com.gregtechceu.gtceu.common.data.GTRecipeCategories;

public class NexTechRecipeCategories {

    public static GTRecipeCategory FLAME_HEATING = GTRecipeCategories
            .register("flame_heating", NexTechRecipeTypes.FLAME_RECIPES)
            .setIcon(GuiTextures.PROGRESS_BAR_BOILER_FUEL.get(true).getSubTexture(0, 0.5, 1, 0.5));

    public static void init() {}
}
