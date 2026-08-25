package com.coremod.nextech.machine.flame;

public interface IFlameThreadMachine {

    boolean hasThreadHatch();

    void setThreadHatchPresent(boolean present);

    interface ThreadPartMarker {}
}
