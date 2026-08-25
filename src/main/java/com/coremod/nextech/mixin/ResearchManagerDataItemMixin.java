package com.coremod.nextech.mixin;

import com.coremod.nextech.NexTechItems;

import com.gregtechceu.gtceu.utils.ResearchManager;

import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ResearchManager.class, remap = false)
public class ResearchManagerDataItemMixin {

    @Inject(method = "getDefaultResearchStationItem", at = @At("HEAD"), cancellable = true)
    private static void onGetDefaultResearchStationItem(int cwut, CallbackInfoReturnable<ItemStack> cir) {
        if (cwut >= 144 && cwut < 320) {
            cir.setReturnValue(NexTechItems.LIVING_DATA_DISK.asStack());
        }
    }
}
