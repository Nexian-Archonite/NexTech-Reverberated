package com.coremod.nextech.machine.flame;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;

import java.util.HashMap;
import java.util.Map;

public class FLAMEFuels {

    private static final Map<Material, FLAMEFuel> FUELS = new HashMap<>();

    public static void register(Material material, int heatPerBucket) {
        FUELS.put(material, new FLAMEFuel(heatPerBucket));
    }

    public static FLAMEFuel get(Material material) {
        return FUELS.get(material);
    }

    public static boolean isFuel(Material material) {
        return FUELS.containsKey(material);
    }

    public static void init() {
        register(
                com.gregtechceu.gtceu.common.data.GTMaterials.get("inactivated_infernality"),
                25);

        register(
                com.gregtechceu.gtceu.common.data.GTMaterials.get("infernality_catalysm"),
                60);
    }
}
