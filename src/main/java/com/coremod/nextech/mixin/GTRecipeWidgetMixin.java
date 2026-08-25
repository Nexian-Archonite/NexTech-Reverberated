package com.coremod.nextech.mixin;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.integration.xei.widgets.GTRecipeWidget;

import net.minecraft.nbt.CompoundTag;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.function.Function;

@Mixin(value = GTRecipeWidget.class, remap = false)
public abstract class GTRecipeWidgetMixin {

    @Shadow
    private GTRecipe recipe;

    @Redirect(
              method = "setRecipeWidget",
              at = @At(
                       value = "INVOKE",
                       target = "Lcom/gregtechceu/gtceu/api/recipe/GTRecipeType;getDataInfos()Ljava/util/List;",
                       remap = false),
              remap = false)
    private List<Function<CompoundTag, String>> nextech$filterEmptyDataInfos(
                                                                             GTRecipeType recipeType) {
        return recipeType.getDataInfos().stream()
                .filter(dataInfo -> {
                    String text = dataInfo.apply(this.recipe.data);
                    return text != null && !text.isEmpty();
                })
                .toList();
    }
}
