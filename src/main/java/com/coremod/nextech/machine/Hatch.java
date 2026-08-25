package com.coremod.nextech.machine;

import com.coremod.nextech.NexTech;
import com.coremod.nextech.machine.partAbility.FLAMEFuelHatch;
import com.coremod.nextech.machine.partAbility.FLAMEPartAbilities;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties;

public class Hatch {

    public static final MachineDefinition FLAME_FUEL_HATCH = NexTech.NEXTECH_REGISTRATE
            .machine("flame_fuel_hatch",
                    holder -> new FLAMEFuelHatch(holder, GTValues.LV, IO.IN))
            .langValue("FLAME Fuel Hatch")
            .rotationState(RotationState.ALL)
            .tier(GTValues.LV)
            .modelProperty(GTMachineModelProperties.IS_FORMED, false)
            .colorOverlayTieredHullModel(
                    GTCEu.id("block/overlay/machine/overlay_pipe_in_emissive"),
                    null,
                    GTCEu.id("block/overlay/machine/overlay_item_hatch"))
            .abilities(FLAMEPartAbilities.FLAME_FUEL_HATCH)
            .register();

    public static void init() {}
}
