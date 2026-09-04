package com.coremod.nextech.machine;

import com.coremod.nextech.CreativeTabs;
import com.coremod.nextech.NexTech;
import com.coremod.nextech.machine.partAbility.FLAMEPlasmaHatch;
import com.coremod.nextech.machine.partAbility.PartAbilities;

import com.coremod.nextech.machine.partAbility.FLAMEPlasmaHatch;
import com.coremod.nextech.machine.partAbility.SingularityPlasmaHatch;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties;

public class Hatch {

    static {
        NexTech.NEXTECH_REGISTRATE.creativeModeTab(() -> CreativeTabs.NEXTECH_MACHINES_TAB);
    }

    public static final MachineDefinition FLAME_PLASMA_HATCH = NexTech.NEXTECH_REGISTRATE
            .machine("flame_plasma_hatch",
                    holder -> new FLAMEPlasmaHatch(holder, GTValues.LV, IO.IN))
            .langValue("F.L.A.M.E Plasma Hatch")
            .rotationState(RotationState.ALL)
            .tier(GTValues.LV)
            .modelProperty(GTMachineModelProperties.IS_FORMED, false)
            .colorOverlayTieredHullModel(
                    GTCEu.id("block/overlay/machine/overlay_plasma_emissive"),
                    null,
                    GTCEu.id("block/overlay/machine/overlay_plasma_hatch"))
            .abilities(PartAbilities.FLAME_PLASMA_HATCH)
            .register();

    public static final MachineDefinition SINGULARITY_PLASMA_HATCH = NexTech.NEXTECH_REGISTRATE
            .machine("singularity_plasma_hatch",
                    holder -> new SingularityPlasmaHatch(holder, GTValues.LV, IO.IN))
            .langValue("Chemical Singularity Plasma Hatch")
            .rotationState(RotationState.ALL)
            .tier(GTValues.LV)
            .modelProperty(GTMachineModelProperties.IS_FORMED, false)
            .colorOverlayTieredHullModel(
                    GTCEu.id("block/overlay/machine/overlay_plasma_emissive"),
                    null,
                    GTCEu.id("block/overlay/machine/overlay_plasma_hatch"))
            .abilities(PartAbilities.SINGULARITY_PLASMA_HATCH)
            .register();

    public static void init() {}
}
