package com.coremod.nextech.machine.flame;

import com.coremod.nextech.CreativeTabs;
import com.coremod.nextech.NexTech;
import com.coremod.nextech.NexTechRecipeTypes;
import com.coremod.nextech.machine.partAbility.FLAMEPartAbilities;

import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import static com.coremod.nextech.NexTech.NEXTECH_REGISTRATE;

public class flame {

    static {
        NexTech.NEXTECH_REGISTRATE.creativeModeTab(() -> CreativeTabs.NEXTECH_MACHINES_TAB);
    }

    public static Block getKjsBlock(String name) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation("kubejs", name));
    }

    public static final MultiblockMachineDefinition FRACTO_LIMINAL_ABYSS_METAL_ENGINE = NEXTECH_REGISTRATE
            .multiblock("fracto_liminal_abyss_metal_engine",
                    holder -> new FLAMEMachine(holder, 0, 1, 50))
            .appearanceBlock(() -> {
                Block b = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("gtceu", "abyssal_netherite_casing"));
                return b;
            })
            .recipeModifiers(FLAMERecipeModifier.FLAME_OC)
            .allowExtendedFacing(false)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(NexTechRecipeTypes.FLAME_RECIPES)
            .pattern(definition -> FactoryBlockPattern.start()
                    // spotless:off
                    .aisle("    B   B    ", "    B   B    ", "    B   B    ", "    B   B    ", "    BBBBB    ", "    CCCCC    ", "             ", "             ", "             ", "      D      ", "    DDDDD    ", "      D      ", "             ", "             ", "             ", "    CCCCC    ", "    NNNNN    ", "             ", "    NNNNN    ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "      E      ", "      E      ", "      E      ", "             ")
                    .aisle(" B  BBBBB  B ", " B  FEEEF  B ", " B  FEGEF  B ", " B  FEEEF  B ", " BBBBBBBBBBB ", " CCCEEEEECCC ", "    EEEEE    ", "     EEE     ", "             ", "             ", "  DD     DD  ", "             ", "             ", "     EEE     ", "    EEEEE    ", " CCCEEEEECCC ", " NNNHHHHHNNN ", "    FFFFF    ", " NNN     NNN ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "      E      ", "    E   E    ", "    E   E    ", "        E    ", "      E      ")
                    .aisle("  BBFBBBFBB  ", "  FF     FF  ", "  FF     FF  ", "  FF     FF  ", " BBBFFFFFBBB ", " CHHHHHHHHHC ", "  HHHHHHHHH  ", "    HHEHH    ", "     E E     ", "             ", " D         D ", "             ", "     E E     ", "    HHEHH    ", "  HHHHHHHHH  ", " CHHHHHHHHHC ", " NHHIIIIIHHN ", "  FF     FF  ", " N         N ", "             ", "             ", "             ", "    DDDDD    ", "             ", "             ", "             ", "      E E    ", "    E E E    ", "             ", "             ", "    E        ", "        E    ")
                    .aisle("  BFFBBBFFB  ", "  F  III  F  ", "  F  III  F  ", "  F  III  F  ", " BBFFIIIFFBB ", " CHHHIIIHHHC ", "  HHHIIIHHH  ", "   HHIIIHH   ", "   HHJJJHH   ", "   HE J EH   ", " D  E   E  D ", "   HE J EH   ", "   HHJJJHH   ", "   HHIIIHH   ", "  HHHIIIHHH  ", " CHHHIIIHHHC ", " NHIIIIIIIHN ", "  F  JJJ  F  ", " N   III   N ", "     JJJ     ", "             ", "             ", "   D     D   ", "             ", "             ", "     E E     ", "    EEHEH    ", "    E        ", "         E   ", "         E   ", "             ", "             ")
                    .aisle("BBFFBBBBBFFBB", "BF  GIFIG  FB", "BF  GJ JG  FB", "BF  GJ JG  FB", "BBFFG   GFFBB", "CEHHG   GHHEC", " EHHG   GHHE ", "  HHG   GHH  ", "   HGBCBGH   ", "   EGBJBGE   ", "D  EGBDBGE  D", "   EGBJBGE   ", "   HGBCBGH   ", "  HHG   GHH  ", " EHHG   GHHE ", "CEHHG   GHHEC", "NHIIG   GIIHN", " F  JJ JJ  F ", "N   IJ JI   N", "    JEBEJ    ", "    JEBEJ    ", "     EBE     ", "  D  EBH  D  ", "     EBE     ", "     EBE     ", "    EEEEE    ", "    HEEEEE   ", "         E   ", "             ", "             ", "         E   ", "             ")
                    .aisle(" BFBBBBBBBFB ", " E IIIIIII E ", " E IJ   JI E ", " E IJ   JI E ", "BBFI     IFBB", "CEHI     IHEC", " EHI     IHE ", " EHI     IHE ", "  EJB C BJE  ", "    B   B    ", "D   B   B   D", "    B   B    ", "  EJB C BJE  ", " EHI     IHE ", " EHI     IHE ", "CEHI     IHEC", "NHII     IIHN", " F JJ   JJ F ", "N  IJ   JI  N", "   JE   EJ   ", "    E B E    ", "    E   E    ", "  D E   E D  ", "    H   E    ", "    EEEEE    ", "    EBBBEE   ", "        HE   ", "             ", "             ", "             ", "             ", "             ")
                    .aisle(" BFBBBBBBBFB ", " E IFIIIFI E ", " G I  G  I G ", " E I  G  I E ", "BBFI  G  IFBB", "CEHI  G  IHEC", " EHI     IHE ", " EEI     IEE ", "   JCCECCJ   ", "D  JJ E JJ  D", "D   D E D   D", "D  JJ E JJ  D", "   JCCECCJ   ", " EEI     IEE ", " EHI     IHE ", "CEHI     IHEC", "NHII     IIHN", " F J     J F ", "N  I     I  N", "   JB   BJ   ", "    B B B    ", "    B B B    ", "  D B   B D  ", "    B   B    ", "    BEEEB    ", "     BBBEE   ", "             ", "             ", "             ", "             ", "      M      ", "             ")
                    .aisle(" BFBBBBBBBFB ", " E IIIIIII E ", " E IJ   JI E ", " E IJ   JI E ", "BBFI     IFBB", "CEHI     IHEC", " EHI     IHE ", " EHI     IHE ", "  EJB C BJE  ", "    B   B    ", "D   B   B   D", "    B   B    ", "  EJB C BJE  ", " EHI     IHE ", " EHI     IHE ", "CEHI     IHEC", "NHII     IIHN", " F JJ   JJ F ", "N  IJ   JI  N", "   JE   EJ   ", "    E   E    ", "    E   E    ", "  D E   E D  ", "    H   H    ", "    EEBEEE   ", "        HEE  ", "         EE  ", "          E  ", "          E  ", "             ", "             ", "             ")
                    .aisle("BBFFBBBBBFFBB", "BF  GIFIG  FB", "BF  GJ JG  FB", "BF  GJ JG  FB", "BBFFG   GFFBB", "CEHHG   GHHEC", " EHHG   GHHE ", "  HHG   GHH  ", "   HGBCBGH   ", "   EGBJBGE   ", "D  EGBDBGE  D", "   EGBJBGE   ", "   HGBCBGH   ", "  HHG   GHH  ", " EHHG   GHHE ", "CEHHG   GHHEC", "NHIIG   GIIHN", " F  JJ JJ  F ", "N   IJ JI   N", "    JEBEJ    ", "    JEBEJ    ", "     EBE     ", "  D  HBE  D  ", "     EBE     ", "     EBE     ", "             ", "             ", "             ", "             ", "             ", "             ", "             ")
                    .aisle("  BFFBBBFFB  ", "  F  III  F  ", "  F  III  F  ", "  F  III  F  ", " BBFFIIIFFBB ", " CHHHIIIHHHC ", "  HHHIIIHHH  ", "   HHIIIHH   ", "   HHJJJHH   ", "   HE J EH   ", " D  E   E  D ", "   HE J EH   ", "   HHJJJHH   ", "   HHIIIHH   ", "  HHHIIIHHH  ", " CHHHIIIHHHC ", " NHIIIIIIIHN ", "  F  JJJ  F  ", " N   III   N ", "     JJJ     ", "             ", "             ", "   D     D   ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ")
                    .aisle("  BBFFFFFBB  ", "  FF     FF  ", "  FF     FF  ", "  FF     FF  ", " BBBFFFFFBBB ", " CHHHHHHHHHC ", "  HHHHHHHHH  ", "    HHEHH    ", "     E E     ", "             ", " D         D ", "             ", "     E E     ", "    HHEHH    ", "  HHHHHHHHH  ", " CHHHHHHHHHC ", " NHHIIIIIHHN ", "  FF     FF  ", " N         N ", "             ", "             ", "             ", "    DDDDD    ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ")
                    .aisle(" B  BBBBB  B ", " B  FEEEF  B ", " B  FE@EF  B ", " B  FEEEF  B ", " BBBBBBBBBBB ", " CCCEEEEECCC ", "    EEEEE    ", "     EEE     ", "             ", "             ", "  DD     DD  ", "             ", "             ", "     EEE     ", "    EEEEE    ", " CCCEEEEECCC ", " NNNHHHHHNNN ", "    FFFFF    ", " NNN     NNN ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ")
                    .aisle("    B   B    ", "    B   B    ", "    B   B    ", "    B   B    ", "    BBBBB    ", "    CCCCC    ", "             ", "             ", "             ", "      D      ", "    DDDDD    ", "      D      ", "             ", "             ", "             ", "    CCCCC    ", "    NNNNN    ", "             ", "    NNNNN    ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ")
                    // spotless:on
                    .where(" ", Predicates.any())
                    .where("B",
                            Predicates.blocks(ForgeRegistries.BLOCKS
                                    .getValue(new ResourceLocation("gtceu", "abyssal_netherite_casing"))))
                    .where("C",
                            Predicates.blocks(ForgeRegistries.BLOCKS
                                    .getValue(new ResourceLocation("gtceu", "activated_nexian_pyrite_frame"))))
                    .where("D", Predicates.blocks(getKjsBlock("aetherite_glass")))
                    .where("E",
                            Predicates
                                    .blocks(ForgeRegistries.BLOCKS
                                            .getValue(new ResourceLocation("gtceu", "abyssal_netherite_casing")))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8)
                                            .setPreviewCount(0))
                                    .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(2)
                                            .setPreviewCount(0))
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(2)
                                            .setPreviewCount(0))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setMaxGlobalLimited(2)
                                            .setPreviewCount(0))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                    .or(Predicates.abilities(FLAMEPartAbilities.FLAME_FUEL_HATCH).setExactLimit(1)))
                    .where("F",
                            Predicates.blocks(ForgeRegistries.BLOCKS
                                    .getValue(new ResourceLocation("gtceu", "abyssal_netherite_heat_escape_casing"))))
                    .where("G",
                            Predicates.blocks(ForgeRegistries.BLOCKS
                                    .getValue(new ResourceLocation("gtceu", "sulvan_steel_pipe_casing"))))
                    .where("H",
                            Predicates.blocks(ForgeRegistries.BLOCKS
                                    .getValue(new ResourceLocation("gtceu", "chithion-flame_casing"))))
                    .where("I",
                            Predicates.blocks(ForgeRegistries.BLOCKS
                                    .getValue(new ResourceLocation("gtceu", "unreal_engine_intake"))))
                    .where("J",
                            Predicates.blocks(
                                    ForgeRegistries.BLOCKS.getValue(new ResourceLocation("gtceu", "atomic_casing"))))
                    .where("M", Predicates.blocks(getKjsBlock("eternal_hell_core")))
                    .where("N", Predicates.heatingCoils())
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(
                    new ResourceLocation("nextech", "block/casings/abyssal/casing"),
                    new ResourceLocation("nextech", "block/multiblock/implosion_compressor"))
            .register();

    public static void init() {}
}
