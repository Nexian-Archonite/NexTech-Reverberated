package com.coremod.nextech.machine.hpca;

import com.coremod.nextech.NexTech;

import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.network.chat.Component;

import java.util.function.Function;

import static com.coremod.nextech.NexTech.NEXTECH_REGISTRATE;
import static com.coremod.nextech.configs.NexicConfigs.*;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.machines.GTResearchMachines.OVERHEAT_TOOLTIPS;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.*;

public class NexTechResearchMachines {

    public static MachineDefinition OMNIC_COMPUTATION_COMPONENT;
    public static MachineDefinition NEXIC_COMPUTATION_COMPONENT;
    public static MachineDefinition OMNIC_COOLER_COMPONENT;
    public static MachineDefinition NEXIC_COOLER_COMPONENT;

    static {
        // Here we define your new custom HPCA part.
        OMNIC_COMPUTATION_COMPONENT = registerComputationHPCAPart(
                "omnic_computation_component", "Omnic Computation Component",
                // The constructor now uses your custom class.
                OmnicComputationPartMachine::new, "reinforced_computation", false)
                .tooltips(
                        // Update the tooltips to reflect the new part's values.
                        Component.translatable("gtceu.machine.hpca.component_general.upkeep_eut",
                                VA[INSTANCE.features.OCUEutUpkeep]),
                        Component.translatable("gtceu.machine.hpca.component_general.max_eut",
                                VA[INSTANCE.features.OCUMaxEUt]),
                        Component.translatable("gtceu.machine.hpca.component_type.computation_cwut",
                                INSTANCE.features.OCUStrength),
                        Component.translatable("gtceu.machine.hpca.component_type.computation_cooling",
                                INSTANCE.features.OCUCoolantUsed),
                        Component.translatable("gtceu.part_sharing.disabled"))
                .tooltipBuilder(OVERHEAT_TOOLTIPS)
                .register();
        NEXIC_COMPUTATION_COMPONENT = registerComputationHPCAPart(
                "nexic_computation_component", "Nexic Computation Component",
                // The constructor now uses your custom class.
                NexicComputationPartMachine::new, "advanced_computation_component", true)
                .tooltips(
                        // Update the tooltips to reflect the new part's values.
                        Component.translatable("gtceu.machine.hpca.component_general.upkeep_eut",
                                VA[INSTANCE.features.NCUEutUpkeep]),
                        Component.translatable("gtceu.machine.hpca.component_general.max_eut",
                                VA[INSTANCE.features.NCUMaxEUt]),
                        Component.translatable("gtceu.machine.hpca.component_type.computation_cwut",
                                INSTANCE.features.NCUStrength),
                        Component.translatable("gtceu.machine.hpca.component_type.computation_cooling",
                                INSTANCE.features.PCUCoolantUsed),
                        Component.translatable("gtceu.part_sharing.disabled"))
                .tooltipBuilder(OVERHEAT_TOOLTIPS)
                .register();

        // Standard version of your custom cooler
        OMNIC_COOLER_COMPONENT = registerCoolingHPCAPart(
                "omnic_heat_sink_component", "Omnic Heat Sink Component",
                // Use a lambda to correctly pass the 'advanced' boolean
                holder -> new OmnicCoolerPartMachine(holder, false),
                "advanced_heat_sink", false) // Pass false for the advanced parameter
                .tooltips(
                        Component.translatable("gtceu.machine.hpca.component_general.upkeep_eut",
                                INSTANCE.features.HeatSinkEutUpkeep),
                        Component.translatable("gtceu.machine.hpca.component_type.cooler_passive"),
                        Component.translatable("gtceu.machine.hpca.component_type.cooler_cooling",
                                INSTANCE.features.HeatSinkStrength),
                        Component.translatable("gtceu.part_sharing.disabled"))
                .register();

        NEXIC_COOLER_COMPONENT = registerCoolingHPCAPart(
                "nexic_cooling_component", "Active Nexic Cooling Component",
                // Use a lambda to correctly pass the 'advanced' boolean
                holder -> new NexicCoolerPartMachine(holder, true),
                "advanced_active_cooler", true) // Pass true for the advanced parameter
                .tooltips(
                        Component.translatable("gtceu.machine.hpca.component_general.upkeep_eut",
                                INSTANCE.features.ActiveCoolerEutUpkeep),
                        Component.translatable("gtceu.machine.hpca.component_type.cooler_active"),
                        Component.translatable("gtceu.machine.hpca.component_type.cooler_active_coolant",
                                INSTANCE.features.ActiveCoolerCoolantUse,
                                // This is already correct
                                GTMaterials.get(INSTANCE.features.ActiveCoolerCoolantBase).getLocalizedName()),
                        // This is the line you need to fix
                        // First, get the localized name for the configured material
                        Component.translatable("gtceu.tooltip.uses_custom_coolant",
                                GTMaterials.get(INSTANCE.features.ActiveCoolerCoolantBase).getLocalizedName()),
                        Component.translatable("gtceu.machine.hpca.component_type.cooler_cooling",
                                INSTANCE.features.ActiveCoolerStrength),
                        Component.translatable("gtceu.part_sharing.disabled"))
                .register();
    }

    private static MachineBuilder<MachineDefinition, ?> registerCoolingHPCAPart(String name, String displayName,
                                                                                Function<IMachineBlockEntity, MetaMachine> constructor,
                                                                                String texture, boolean isAdvanced) {
        return NEXTECH_REGISTRATE.machine(name, constructor)
                .langValue(displayName)
                .rotationState(RotationState.ALL)
                .abilities(PartAbility.HPCA_COMPONENT)
                .modelProperty(GTMachineModelProperties.IS_FORMED, false)
                .modelProperty(GTMachineModelProperties.IS_HPCA_PART_DAMAGED, false)
                .modelProperty(GTMachineModelProperties.IS_ACTIVE, false)
                .model(createHPCAPartModel(isAdvanced,
                        NexTech.id("block/overlay/machine/hpca/cooling/" + texture),
                        NexTech
                                .id("block/overlay/machine/hpca/damaged" + (isAdvanced ? "_nexic" : ""))));
    }

    private static MachineBuilder<MachineDefinition, ?> registerComputationHPCAPart(String name, String displayName,
                                                                                    Function<IMachineBlockEntity, MetaMachine> constructor,
                                                                                    String texture,
                                                                                    boolean isAdvanced) {
        return NEXTECH_REGISTRATE.machine(name, constructor)
                .langValue(displayName)
                .rotationState(RotationState.ALL)
                .abilities(PartAbility.HPCA_COMPONENT)
                .modelProperty(GTMachineModelProperties.IS_FORMED, false)
                .modelProperty(GTMachineModelProperties.IS_HPCA_PART_DAMAGED, false)
                .modelProperty(GTMachineModelProperties.IS_ACTIVE, false)
                .model(createHPCAPartModel(isAdvanced,
                        NexTech.id("block/machine/part/hpca/computation/" + texture),
                        NexTech
                                .id("block/machine/part/hpca/computation/damaged" + (isAdvanced ? "_nexic" : ""))));
    }

    public static void init() {}
}
