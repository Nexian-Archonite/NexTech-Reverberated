package com.coremod.nextech.machine.hpca;

import com.coremod.nextech.CreativeTabs;
import com.coremod.nextech.NexTech;
import com.coremod.nextech.NexTechBlocks;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.common.data.machines.GTResearchMachines;
import com.gregtechceu.gtceu.data.lang.LangHandler;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import static com.coremod.nextech.NexTech.NEXTECH_REGISTRATE;
import static com.coremod.nextech.configs.NexicConfigs.*;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;

@SuppressWarnings("all")
public class Omnience {

    public static MachineDefinition OMNIENCE = null;
    static {
        NEXTECH_REGISTRATE.creativeModeTab(() -> CreativeTabs.NEXTECH_MACHINES_TAB);
    }
    static {
        OMNIENCE = NEXTECH_REGISTRATE
                .multiblock("omnience", NexTechHPCAMachine::new)
                .langValue("&[rainbow]The Omnience §r(Ω)")
                .tooltips(Component.translatable("nextech.tooltip.hyper_machine_purpose",
                        GTMaterials.get(INSTANCE.features.ActiveCoolerCoolantBase).getLocalizedName()
                                .withStyle(style -> style.withColor(TextColor.fromRgb(GTMaterials
                                        .get(INSTANCE.features.ActiveCoolerCoolantBase).getMaterialARGB()))),
                        GTMaterials.get(INSTANCE.features.ActiveCoolerCoolant1).getLocalizedName()
                                .withStyle(style -> style.withColor(TextColor.fromRgb(GTMaterials
                                        .get(INSTANCE.features.ActiveCoolerCoolant1).getMaterialARGB()))),
                        GTMaterials.get(INSTANCE.features.ActiveCoolerCoolant2).getLocalizedName()
                                .withStyle(style -> style.withColor(TextColor.fromRgb(GTMaterials
                                        .get(INSTANCE.features.ActiveCoolerCoolant2).getMaterialARGB())))),
                        Component.translatable("nextech.tooltip.hyper_machine_1"),
                        Component
                                .translatable("nextech.tooltip.hyper_machine_coolant_base",
                                        GTMaterials.get(INSTANCE.features.ActiveCoolerCoolantBase)
                                                .getLocalizedName(),
                                        INSTANCE.features.BaseCoolantBoost)
                                .withStyle(style -> style.withColor(TextColor.fromRgb(GTMaterials
                                        .get(INSTANCE.features.ActiveCoolerCoolantBase).getMaterialARGB()))),
                        Component.translatable("nextech.tooltip.hyper_machine_coolant2",
                                GTMaterials.get(INSTANCE.features.ActiveCoolerCoolant1).getLocalizedName(),
                                INSTANCE.features.CoolantBoost1)
                                .withStyle(style -> style.withColor(TextColor.fromRgb(GTMaterials
                                        .get(INSTANCE.features.ActiveCoolerCoolant1).getMaterialARGB()))),
                        Component
                                .translatable("nextech.tooltip.hyper_machine_coolant3",
                                        GTMaterials.get(INSTANCE.features.ActiveCoolerCoolant2).getLocalizedName(),
                                        INSTANCE.features.CoolantBoost2)
                                .withStyle(style -> style.withColor(TextColor.fromRgb(GTMaterials
                                        .get(INSTANCE.features.ActiveCoolerCoolant2).getMaterialARGB()))))
                .rotationState(RotationState.NON_Y_AXIS)
                .appearanceBlock(NexTechBlocks.STURVENE_COMPUTER_CASING)
                .recipeType(GTRecipeTypes.DUMMY_RECIPES)
                .tooltips(LangHandler.getMultiLang("gtceu.machine.high_performance_computation_array.tooltip"))
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("aaaaaaaaa", "acccaccca", "accchccca", "acccaccca", "aaaaaaaaa")
                        .aisle("accccccca", "bdddfdddb", "bdddedddb", "bdddfdddb", "accccccca")
                        .aisle("accccccca", "beeefeeeb", "beeeeeeeb", "beeefeeeb", "accccccca")
                        .aisle("accccccca", "bdddfdddb", "bdddedddb", "bdddfdddb", "accccccca")
                        .aisle("accccccca", "afffffffa", "afffefffa", "afffffffa", "accccccca")
                        .aisle("accccccca", "bdddfdddb", "bdddedddb", "bdddfdddb", "accccccca")
                        .aisle("accccccca", "beeefeeeb", "beeeeeeeb", "beeefeeeb", "accccccca")
                        .aisle("accccccca", "bdddfdddb", "bdddedddb", "bdddfdddb", "accccccca")
                        .aisle("accccccca", "afffffffa", "afffefffa", "afffffffa", "accccccca")
                        .aisle("accccccca", "bdddfdddb", "bdddedddb", "bdddfdddb", "accccccca")
                        .aisle("accccccca", "beeefeeeb", "beeeeeeeb", "beeefeeeb", "accccccca")
                        .aisle("accccccca", "bdddedddb", "bdddedddb", "bdddedddb", "accccccca")
                        .aisle("aaaagaaaa", "afffffffa", "afffifffa", "afffffffa", "aaaaaaaaa")

                        .where('a', blocks(NexTechBlocks.STURVENE_COMPUTER_CASING.get()))
                        .where('b', blocks(FUSION_GLASS.get()))
                        .where('c', blocks(NexTechBlocks.MORNILOY_HIGH_POWER_CASING.get()))
                        .where('d', blocks(NexTechResearchMachines.OMNIC_COOLER_COMPONENT.get())
                                .or(blocks(NexTechResearchMachines.NEXIC_COOLER_COMPONENT.get()))
                                .or(blocks(GTResearchMachines.HPCA_EMPTY_COMPONENT.get()))
                                .or(blocks(GTResearchMachines.HPCA_BRIDGE_COMPONENT.get()))
                                .or(blocks(NexTechResearchMachines.NEXIC_COMPUTATION_COMPONENT.get()))
                                .or(blocks(NexTechResearchMachines.OMNIC_COMPUTATION_COMPONENT.get()))
                        )
                        .where("e", Predicates.blocks(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("gtceu", "abyssal_netherite_pipe_casing"))))
                        .where('f', any())
                        .where('g', controller(blocks(definition.getBlock())))
                        .where('h', blocks(NexTechBlocks.STURVENE_COMPUTER_CASING.get())
                                .or(Predicates.abilities(PartAbility.COMPUTATION_DATA_TRANSMISSION).setMaxGlobalLimited(1)))
                        .where('i', Predicates.blocks(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", "stripped_jungle_wood"))))
                        .build())

                .workableCasingModel(NexTech.id("block/casings/advanced_computer_casing/advanced_computer_casing"),
                        GTCEu.id("block/multiblock/hpca"))
                .register();
    }

    public static void init() {}
}
