package com.coremod.nextech;

import com.coremod.nextech.machine.flame.FLAMEHeatingLogic;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;
import com.lowdragmc.lowdraglib.utils.LocalizationUtils;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.register;

public class NexTechRecipeTypes {

    public static final GTRecipeType FLAME_RECIPES = register(
            "fractal_metal_alloyment",
            GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(2, 1, 7, 1)
            .setEUIO(IO.IN)
            .setProgressBar(
                    GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,
                    ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .addCustomRecipeLogic(FLAMEHeatingLogic.INSTANCE)
            .addDataInfo(data -> {
                int temp = data.getInt("ebf_temp");
                return temp > 0 ? LocalizationUtils.format(
                        "nextech.recipe.temperature",
                        FormattingUtil.formatNumbers(temp)) : "";
            })
            .setSound(GTSoundEntries.FURNACE);

    public static void init() {}
}
