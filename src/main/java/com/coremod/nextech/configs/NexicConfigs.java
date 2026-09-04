package com.coremod.nextech.configs;

import com.coremod.nextech.NexTechAddon;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.ConfigHolder;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;

@Config(id = NexTechAddon.MOD_ID)
public class NexicConfigs {

    public static NexicConfigs INSTANCE;

    public static ConfigHolder<NexicConfigs> CONFIG_HOLDER;

    public static void init() {
        CONFIG_HOLDER = Configuration.registerConfig(NexicConfigs.class, ConfigFormats.yaml());
        INSTANCE = CONFIG_HOLDER.getConfigInstance();
    }

    @Configurable
    public FeatureConfigs features = new FeatureConfigs();

    public static class FeatureConfigs {

        // Features

        @Configurable
        @Configurable.Comment({ "How powerful the Omnic Computation Unit is (CWU/t)" })
        public int OCUStrength = 32;
        @Configurable
        @Configurable.Comment({ "How powerful the Nexic Computation Unit is (CWU/t)" })
        public int NCUStrength = 64;
        @Configurable
        @Configurable.Comment({
                "How much coolant the Omnic Computation Unit uses" })
        public int OCUCoolantUsed = 4;
        @Configurable
        @Configurable.Comment({
                "How much coolant the Nexic Computation Unit uses" })
        public int PCUCoolantUsed = 8;
        @Configurable
        @Configurable.Comment({ "How powerful the Omnic Computation Unit is (CWU/t) when damaged" })
        public int damagedOCUStrength = 16;
        @Configurable
        @Configurable.Comment({ "How powerful the Nexic Computation Unit is (CWU/t) when damaged" })
        public int damagedNCUStrength = 32;
        @Configurable
        @Configurable.Comment({
                "How much EU the Omnic Computation uses per tick while not providing CWU/t (Goes off GTValues, ULV is 0, LV is 1, MV is 2, etc)" })
        public int OCUEutUpkeep = 7;
        @Configurable
        @Configurable.Comment({
                "How much EU the Omnic Computation can use at max (Goes off GTValues, ULV is 0, LV is 1, MV is 2, etc)" })
        public int OCUMaxEUt = 9;
        @Configurable
        @Configurable.Comment({
                "How much EU the Nexic Computation uses per tick while not providing CWU/t (Goes off GTValues, ULV is 0, LV is 1, MV is 2, etc)" })
        public int NCUEutUpkeep = 9;
        @Configurable
        @Configurable.Comment({
                "How much EU the Nexic Computation can use at max (Goes off GTValues, ULV is 0, LV is 1, MV is 2, etc)" })
        public int NCUMaxEUt = 11;

        // COOLING
        @Configurable
        @Configurable.Comment({ "How powerful the Omnic Heat Sink is (Cooling Provided)" })
        public int HeatSinkStrength = 4;
        @Configurable
        @Configurable.Comment({ "How powerful the Omnic Active Cooler is (Cooling Provided)" })
        public int ActiveCoolerStrength = 8;
        @Configurable
        @Configurable.Comment({
                "How much EU the Omnic Heat Sink uses per tick (Goes off GTValues, ULV is 0, LV is 1, MV is 2, etc)" })
        public int HeatSinkEutUpkeep = 2;
        @Configurable
        @Configurable.Comment({
                "How much EU the Active Omnic Cooler uses per tick (Goes off GTValues, ULV is 0, LV is 1, MV is 2, etc)" })
        public int ActiveCoolerEutUpkeep = 8;
        @Configurable
        @Configurable.Comment({ "How much coolant the Active Omnic Cooler can use at max in milibuckets" })
        public int ActiveCoolerCoolantUse = 10;
        @Configurable
        @Configurable.Comment({
                "What Base Coolant the Active Omnic Cooler uses while in the PHPCA (Gt or GT Kubejs Material)" })
        public String ActiveCoolerCoolantBase = "pcb_coolant";
        @Configurable
        @Configurable.Comment({
                "What Stronger Coolant the Active Omnic Cooler uses while in the PHPCA  (Gt or GT Kubejs Material)" })
        public String ActiveCoolerCoolant1 = "cryothetic_zero";
        @Configurable
        @Configurable.Comment({
                "What Strongest Coolant the Active Omnic Cooler uses when in the PHPCA (Gt or GT Kubejs Material)" })
        public String ActiveCoolerCoolant2 = "bose-einstein_oganesson-xenon_trifluoride_condensate_plasma";
        @Configurable
        @Configurable.Comment({
                "Base CWU/t multiplier with no special coolant" })
        public double BaseCoolantBoost = 1.0;
        @Configurable
        @Configurable.Comment({
                "How much ActiveCoolerCoolant1 boosts base CWU/t" })
        public double CoolantBoost1 = 1.3;
        @Configurable
        @Configurable.Comment({
                "How much ActiveCoolerCoolant2 boosts base CWU/t" })
        public double CoolantBoost2 = 1.6;
    }
}
