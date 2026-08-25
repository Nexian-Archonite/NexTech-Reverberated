package com.coremod.nextech;

import com.coremod.nextech.machine.flame.FLAMEHeatingLogic;
import com.coremod.nextech.machine.flame.FLAMEMachine;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;
import com.lowdragmc.lowdraglib.utils.LocalizationUtils;

import net.minecraft.network.chat.Component;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.register;

public class NexTechRecipeTypes {

    public static final GTRecipeType FLAME_RECIPES = register("fractal_metal_alloyment", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(2, 1, 7, 1)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .addCustomRecipeLogic(new FLAMEHeatingLogic())
            .addDataInfo(data -> {
                int temp = data.getInt("ebf_temp");
                if (temp > 0) {
                    return LocalizationUtils.format("nextech.recipe.temperature", FormattingUtil.formatNumbers(temp));
                }
                return "";
            })
            .addDataInfo(data -> {
                int temp = data.getInt("ebf_temp");
                if (temp > 0) {
                    var requiredFluid = FLAMEMachine.getFLAMEHeatingLiquid(temp);
                    if (requiredFluid != null) {
                        return Component.translatable("nextech.recipe.heating_fluid",
                                requiredFluid.getLocalizedName().getString()).getString();
                    }
                }
                return "";
            })
            .addDataInfo(data -> {
                int heat = data.getInt("heat_per_cycle");
                int cap = data.getInt("heat_cap");
                if (heat > 0) {
                    return LocalizationUtils.format("nextech.recipe.heat_per_cycle", heat, cap);
                }
                return "";
            })
            .setUiBuilder((recipe, widgetGroup) -> {})
            .setSound(GTSoundEntries.FURNACE);

    public static void init() {}
}
