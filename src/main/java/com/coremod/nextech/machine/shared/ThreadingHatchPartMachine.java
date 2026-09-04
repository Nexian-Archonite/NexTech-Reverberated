package com.coremod.nextech.machine.shared;

import com.coremod.nextech.api.IThreadedMultiblock;
import com.coremod.nextech.machine.partAbility.PartAbilities;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;

public class ThreadingHatchPartMachine extends TieredIOPartMachine {

    private final int threadCount;

    public ThreadingHatchPartMachine(IMachineBlockEntity holder, int tier, Object... args) {
        super(holder, tier, IO.NONE);
        this.threadCount = tier - 7;
    }

    public int getThreadCount() {
        return this.threadCount;
    }

    @Override
    public void addedToController(IMultiController controller) {
        super.addedToController(controller);
        if (controller instanceof IThreadedMultiblock threaded) {
            threaded.setThreadHatch(this);
        }
    }

    @Override
    public void removedFromController(IMultiController controller) {
        super.removedFromController(controller);
        if (controller instanceof IThreadedMultiblock threaded) {
            if (threaded.getThreadHatch() == this) {
                threaded.setThreadHatch(null);
            }
        }
    }

    @Override
    public Widget createUIWidget() {
        var group = new WidgetGroup(0, 0, 120, 20);
        group.addWidget(new LabelWidget(5, 5, () -> "Aux Threads: §b+" + this.threadCount));
        return group;
    }
}