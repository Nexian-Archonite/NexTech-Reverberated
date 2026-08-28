package com.coremod.nextech;

import com.coremod.nextech.data.recipe.recipes.NexTechRecipes;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.recipe.category.GTRecipeCategory;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

@SuppressWarnings("unused")
@GTAddon
public class NexTechAddon implements IGTAddon {

    public static final String MOD_ID = "nextech";

    @Override
    public GTRegistrate getRegistrate() {
        return NexTech.NEXTECH_REGISTRATE;
    }

    @Override
    public void initializeAddon() {}

    private void registerRecipeCategories(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeCategory> event) {
        NexTechRecipeCategories.init();
    }

    @Override
    public String addonModId() {
        return NexTech.MOD_ID;
    }

    @Override
    public void registerTagPrefixes() {
        NexTechTagPrefixes.init();
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        NexTechRecipes.init(provider);
    }

    @Override
    public void registerElements() {
        // CustomElements.init();
    }

    // If you have custom ingredient types, uncomment this & change to match your capability.
    // KubeJS WILL REMOVE YOUR RECIPES IF THESE ARE NOT REGISTERED.
    /*
     * public static final ContentJS<Double> PRESSURE_IN = new ContentJS<>(NumberComponent.ANY_DOUBLE,
     * CustomRecipeCapabilities.PRESSURE, false);
     * public static final ContentJS<Double> PRESSURE_OUT = new ContentJS<>(NumberComponent.ANY_DOUBLE,
     * CustomRecipeCapabilities.PRESSURE, true);
     * 
     * @Override
     * public void registerRecipeKeys(KJSRecipeKeyEvent event) {
     * event.registerKey(CustomRecipeCapabilities.PRESSURE, Pair.of(PRESSURE_IN, PRESSURE_OUT));
     * }
     */
}
