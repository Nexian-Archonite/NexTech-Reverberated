package com.coremod.nextech.integration.jei;

import com.coremod.nextech.NexTechRecipeTypes;
import com.coremod.nextech.machine.flame.flame;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.common.data.GTRecipeCategories;
import com.gregtechceu.gtceu.integration.jei.recipe.GTRecipeJEICategory;

import net.minecraft.resources.ResourceLocation;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class NexTechJeiPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation("nextech", "jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        if (GTCEu.Mods.isREILoaded() || GTCEu.Mods.isEMILoaded()) return;
        registration.addRecipeCatalyst(
                flame.FRACTO_LIMINAL_ABYSS_METAL_ENGINE.asStack(),
                GTRecipeJEICategory.TYPES.apply(NexTechRecipeTypes.FLAME_RECIPES.getCategory()),
                GTRecipeJEICategory.TYPES.apply(GTRecipeCategories.get("flame_heating")));
    }
}
