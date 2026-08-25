package com.coremod.nextech;

import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlag;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;

public class NexTechMaterialFlags {

    public static final MaterialFlag GENERATE_CHAINLET = new MaterialFlag.Builder("generate_chainlet")
            .requireFlags(MaterialFlags.GENERATE_RING)
            .build();

    public static final MaterialFlag GENERATE_CHAIN_MESH = new MaterialFlag.Builder("generate_chain_mesh")
            .requireFlags(GENERATE_CHAINLET)
            .build();

    public static void init() {}
}
