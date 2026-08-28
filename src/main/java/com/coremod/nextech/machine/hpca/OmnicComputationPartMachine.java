package com.coremod.nextech.machine.hpca;

import com.coremod.nextech.configs.NexicConfigs;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.IHPCAComputationProvider;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.common.machine.multiblock.part.hpca.HPCAComputationPartMachine;

import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture;

import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class OmnicComputationPartMachine extends HPCAComputationPartMachine implements IHPCAComputationProvider {

    public OmnicComputationPartMachine(IMachineBlockEntity holder) {
        super(holder, true);
    }

    @Override
    public ResourceTexture getComponentIcon() {
        if (isDamaged()) {
            return GuiTextures.HPCA_ICON_DAMAGED_COMPUTATION_COMPONENT;
        }
        return GuiTextures.HPCA_ICON_ADVANCED_COMPUTATION_COMPONENT;
    }

    @Override
    public int getUpkeepEUt() {
        return GTValues.VA[NexicConfigs.INSTANCE.features.OCUEutUpkeep];
    }

    @Override
    public int getMaxEUt() {
        return GTValues.VA[NexicConfigs.INSTANCE.features.OCUMaxEUt];
    }

    @Override
    public int getCWUPerTick() {
        if (isDamaged()) return NexicConfigs.INSTANCE.features.damagedOCUStrength;
        return NexicConfigs.INSTANCE.features.OCUStrength;
    }
}
