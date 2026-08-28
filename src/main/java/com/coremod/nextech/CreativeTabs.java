package com.coremod.nextech;

import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import com.tterrag.registrate.util.entry.RegistryEntry;

import static com.coremod.nextech.NexTech.NEXTECH_REGISTRATE;
import static com.coremod.nextech.NexTechItems.LIVING_DATA_DISK;
import static com.coremod.nextech.machine.flame.flame.FRACTO_LIMINAL_ABYSS_METAL_ENGINE;

public class CreativeTabs {

    public static RegistryEntry<CreativeModeTab> NEXTECH_MACHINES_TAB = NEXTECH_REGISTRATE
            .defaultCreativeTab(NexTech.MOD_ID + "_machines",
                    builder -> builder
                            .displayItems(
                                    new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(
                                            NexTech.MOD_ID + "_machines",
                                            NEXTECH_REGISTRATE))
                            .title(NEXTECH_REGISTRATE.addLang(
                                    "itemGroup", new ResourceLocation(NexTech.MOD_ID, "creative_tab_machines"),
                                    "NexTech Machines"))
                            .icon(FRACTO_LIMINAL_ABYSS_METAL_ENGINE::asStack)
                            .build())
            .register();

    public static RegistryEntry<CreativeModeTab> NEXTECH_ITEMS_TAB = NEXTECH_REGISTRATE
            .defaultCreativeTab(NexTech.MOD_ID + "_items",
                    builder -> builder
                            .displayItems(
                                    new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(
                                            NexTech.MOD_ID + "_items",
                                            NEXTECH_REGISTRATE))
                            .title(NEXTECH_REGISTRATE.addLang(
                                    "itemGroup", new ResourceLocation(NexTech.MOD_ID, "creative_tab_items"),
                                    "NexTech Items"))
                            .icon(LIVING_DATA_DISK::asStack)
                            .build())
            .register();

    public static void init() {}
}
