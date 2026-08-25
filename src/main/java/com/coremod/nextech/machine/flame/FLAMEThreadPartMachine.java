package com.coremod.nextech.machine.flame;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;

public class FLAMEThreadPartMachine
                                    extends TieredIOPartMachine
                                    implements IFlameThreadMachine.ThreadPartMarker {

    public FLAMEThreadPartMachine(
                                  IMachineBlockEntity holder,
                                  int tier,
                                  Object... args) {
        super(holder, tier, IO.NONE);
    }

    @Override
    public void addedToController(IMultiController controller) {
        super.addedToController(controller);

        if (controller instanceof IFlameThreadMachine flame) {
            flame.setThreadHatchPresent(true);
        }
    }

    @Override
    public void removedFromController(IMultiController controller) {
        super.removedFromController(controller);

        if (!(controller instanceof IFlameThreadMachine flame)) {
            return;
        }

        boolean anotherThreadPart = false;

        for (var part : controller.getParts()) {
            if (part instanceof IFlameThreadMachine.ThreadPartMarker) {
                anotherThreadPart = true;
                break;
            }
        }

        flame.setThreadHatchPresent(anotherThreadPart);
    }
}
