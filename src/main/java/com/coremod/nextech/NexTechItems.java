package com.coremod.nextech;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.common.item.DataItemBehavior;
import com.gregtechceu.gtceu.common.item.TooltipBehavior;

import net.minecraft.network.chat.Component;

import com.tterrag.registrate.util.entry.ItemEntry;

import static com.gregtechceu.gtceu.common.data.GTItems.attach;

public class NexTechItems {

    public static ItemEntry<ComponentItem> LIVING_DATA_DISK;

    public static void init() {
        LIVING_DATA_DISK = NexTech.NEXTECH_REGISTRATE
                .item("living_data_disk", ComponentItem::create)
                .lang("§4Living Data Disk")
                .onRegister(attach(new DataItemBehavior(true, 448)))
                .onRegister(attach(new TooltipBehavior(lines -> {
                    lines.add(Component.translatable("item.nextech.living_data_disk.tooltip"));
                })))
                .register();
    }
}
