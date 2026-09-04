package com.coremod.nextech.machine.partAbility;

import com.coremod.nextech.machine.flame.FLAMEMachine;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableFluidTank;

public class SingularityPlasmaHatch extends TieredIOPartMachine {

    public final NotifiableFluidTank fluidTank;

    public SingularityPlasmaHatch(IMachineBlockEntity holder, int tier, IO io) {
        super(holder, tier, io);

        this.fluidTank = new NotifiableFluidTank(
                this,
                1,
                16000,
                io);

        this.fluidTank.setFilter(stack -> {
            if (stack.isEmpty()) {
                return false;
            }

            return FLAMEMachine.FLAME_FUEL_HEAT.keySet().stream()
                    .anyMatch(material -> material.getFluid().isSame(stack.getFluid()));
        });

        attachTraits(this.fluidTank);
    }
}
