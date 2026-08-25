package com.coremod.nextech;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;

public class NexTechTagPrefixes {

    public static final TagPrefix chainlet = new TagPrefix("chainlet")
            .idPattern("%s_chainlet")
            .defaultTagPath("chainlets/%s")
            .langValue("%s Chainlet")
            .materialIconType(NexTechMaterialIconTypes.chainlet)
            .materialAmount(GTValues.M / 4)
            .unificationEnabled(true)
            .generateItem(true)
            .generationCondition(material -> material.hasFlag(NexTechMaterialFlags.GENERATE_CHAINLET));

    public static final TagPrefix chainMesh = new TagPrefix("chain_mesh")
            .idPattern("%s_chain_mesh")
            .defaultTagPath("chain_meshes/%s")
            .langValue("%s Chain Mesh")
            .materialIconType(NexTechMaterialIconTypes.chainMesh)
            .materialAmount(GTValues.M * 4)
            .unificationEnabled(true)
            .generateItem(true)
            .generationCondition(material -> material.hasFlag(NexTechMaterialFlags.GENERATE_CHAIN_MESH));

    public static void init() {}
}
