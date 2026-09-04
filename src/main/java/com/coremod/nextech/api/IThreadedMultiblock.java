package com.coremod.nextech.api;

import com.coremod.nextech.machine.shared.ThreadingHatchPartMachine;
import org.jetbrains.annotations.Nullable;

public interface IThreadedMultiblock {

    /**
     * Number of distinct aux logic types this machine has.
     * CRV = 2 (heating + pressure)
     * Vacuum = 1 (vacuum)
     * FLAME = 1 (heating)
     * Singularity = 2 (heating + pressure)
     */
    int getBaseAuxCount();

    @Nullable ThreadingHatchPartMachine getThreadHatch();
    void setThreadHatch(@Nullable ThreadingHatchPartMachine hatch);

    /**
     * Multiplier from hatch. tier - 7, so:
     * no hatch = 1 (identity)
     * ZPM(7)   = 1
     * UV(8)    = 2
     * UHV(9)   = 3
     * UEV(10)  = 4
     * ...
     */
    default int getAuxMultiplier() {
        ThreadingHatchPartMachine hatch = getThreadHatch();
        return hatch != null ? Math.max(1, hatch.getThreadCount()) : 1;
    }

    /**
     * Total aux slots = base aux types * multiplier.
     * Round robin distributes these across aux logic types.
     */
    default int getAuxThreadCount() {
        return getBaseAuxCount() * getAuxMultiplier();
    }

    /**
     * Slot 0 = main recipe, always.
     * Slots 1..N = aux, round robin.
     */
    default int getTotalThreadCount() {
        return 1 + getAuxThreadCount();
    }
}