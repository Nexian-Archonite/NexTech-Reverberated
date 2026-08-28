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
                .multiblock("the_omnience", NexTechHPCAMachine::new)
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
                        .aisle("BBBBCCCBBBB", "CDDCCCCCDDC", "CCDCCCCCDCC", "CCCCCCCCCCC", "CCCCCCCCCCC",
                                "CCCCCCCCCCC", "CCCCCCCCCCC", "CCCCCCCCCCC", "CCCCCCCCCCC", "CCCCCCCCCCC")
                        .aisle("BEEBBBBBEEB", "DEEEFFFEEED", "DEEFGGGFEED", "DDDFGGGFDDD", "CBBFGGGFBBC",
                                "CBBFGGGFBBC", "CCBFGGGFBCC", "CCBEFFFEBCC", "CCBBBBBBBCC", "CCBBBBBBBCC")
                        .aisle("BBHHIIIHHBB", "CDDAAAAADDC", "CIDAAAAADIC", "CIAAAAAAAIC", "CIAAAAAAAIC",
                                "CEAAAAAAAEC", "CEAAAAAAAEC", "CEAAAAAAAEC", "CEAAAAAAAEC", "CBBIIIIIBBC")
                        .aisle("CBIIIIIIIBC", "CJAAAKAAAJC", "CJAAAKAAAJC", "CJAAAKAAAJC", "CFAAAKAAAFC",
                                "CLAAAKAAALC", "CLAAAKAAALC", "CLAAAKAAALC", "CFAAAAAAAFC", "CBIIIIIIIBC")
                        .aisle("CBIIIIIIIBC", "CJAAKMKAAJC", "CJAAKMKAAJC", "CJAAKMKAAJC", "CFAAKMKAAFC",
                                "CLAAKMKAALC", "CLAAKMKAALC", "CLAAKMKAALC", "CFAAAAAAAFC", "CBIIIIIIIBC")
                        .aisle("CBIIIIIIIBC", "CJAAAKAAAJC", "CJAAAKAAAJC", "CJAAAKAAAJC", "CFAAAKAAAFC",
                                "CLAAAKAAALC", "CLAAAKAAALC", "CLAAAKAAALC", "CFAAAAAAAFC", "CBIIIIIIIBC")
                        .aisle("BBHHIIIHHBB", "CDDAAAAADDC", "CIDAAAAADIC", "CIAAAAAAAIC", "CIAAAAAAAIC",
                                "CEAAAAAAAEC", "CEAAAAAAAEC", "CEAAAAAAAEC", "CEAAAAAAAEC", "CBBIIIIIBBC")
                        .aisle("BEEBBNBBEEB", "DEEEFFFEEED", "DEEOJJJOEED", "DDDOJJJODDD", "CBBOJJJOBBC",
                                "CBBOJJJOBBC", "CCBBJJJBBCC", "CCBBJJJBBCC", "CCBBBBBBBCC", "CCBBBBBBBCC")
                        .aisle("BBBBCCCBBBB", "CDDCCCCCDDC", "CCDCCCCCDCC", "CCCCCCCCCCC", "CCCCCCCCCCC",
                                "CCCCCCCCCCC", "CCCCCCCCCCC", "CCCCCCCCCCC", "CCCCCCCCCCC", "CCCCCCCCCCC")
                        .where("A", air())
                        .where("B",
                                Predicates.blocks(ForgeRegistries.BLOCKS
                                        .getValue(new ResourceLocation("gtceu", "abyssal_netherite_casing"))))
                        .where('C', any())
                        .where("D",
                                Predicates.blocks(ForgeRegistries.BLOCKS
                                        .getValue(new ResourceLocation("gtceu", "cryonull_casing"))))
                        .where('E', blocks(NexTechBlocks.STURVENE_COMPUTER_CASING.get()).setMinGlobalLimited(20)
                                .or(abilities(PartAbility.INPUT_ENERGY).setMinGlobalLimited(1)
                                        .setMaxGlobalLimited(2, 1))
                                .or(abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(1))
                                .or(abilities(PartAbility.COMPUTATION_DATA_TRANSMISSION).setExactLimit(1))
                                .or(autoAbilities(true, false, false)))
                        .where('F', blocks(NexTechBlocks.STURVENE_COMPUTER_HEAT_VENT.get()))
                        .where('G', blocks(NexTechResearchMachines.OMNIC_COMPUTATION_COMPONENT.get())
                                .or(blocks(NexTechResearchMachines.NEXIC_COMPUTATION_COMPONENT.get()))
                                .or(blocks(GTResearchMachines.HPCA_ADVANCED_COMPUTATION_COMPONENT.get()))
                                .or(blocks(GTResearchMachines.HPCA_EMPTY_COMPONENT.get()))
                                .or(blocks(GTResearchMachines.HPCA_COMPUTATION_COMPONENT.get())))
                        .where('H', blocks(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING.get()))
                        .where('I', blocks(NexTechBlocks.MORNILOY_HIGH_POWER_CASING.get()))
                        .where('J', blocks(FUSION_GLASS.get()))
                        .where("K",
                                Predicates.blocks(ForgeRegistries.BLOCKS
                                        .getValue(new ResourceLocation("gtceu", "transcendent_dilithide_coil_block"))))
                        .where('L', blocks(NexTechResearchMachines.OMNIC_COOLER_COMPONENT.get())
                                .or(blocks(NexTechResearchMachines.ACTIVE_NEXIC_COOLER_COMPONENT.get()))
                                .or(blocks(GTResearchMachines.HPCA_EMPTY_COMPONENT.get()))
                                .or(blocks(GTResearchMachines.HPCA_BRIDGE_COMPONENT.get()))
                                .or(blocks(GTResearchMachines.HPCA_ACTIVE_COOLER_COMPONENT.get()))
                                .or(blocks(GTResearchMachines.HPCA_HEAT_SINK_COMPONENT.get())))
                        .where("M",
                                Predicates.blocks(ForgeRegistries.BLOCKS
                                        .getValue(new ResourceLocation("gtceu", "nitinol_casing"))))
                        .where('N', controller(blocks(definition.getBlock())))
                        .where('O', blocks(GCYMBlocks.HEAT_VENT.get()))
                        .build())
                .workableCasingModel(NexTech.id("block/casings/advanced_computer_casing/advanced_computer_casing"),
                        GTCEu.id("block/multiblock/hpca"))
                .register();
    }

    public static void init() {}
}
