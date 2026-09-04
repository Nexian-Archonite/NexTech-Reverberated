package com.coremod.nextech.machine.reaction;

import com.coremod.nextech.NexTech;
import com.coremod.nextech.NexTechRecipeTypes;

import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.data.RotationState;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import static com.coremod.nextech.NexTech.NEXTECH_REGISTRATE;

public class ChemicalReactionVat {

    public static final MultiblockMachineDefinition CHEMICAL_REACTION_VAT =
            NEXTECH_REGISTRATE
                    .multiblock("chemical_reaction_vat",
                            holder -> new CoilWorkableElectricMultiblockMachine(holder))
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeTypes(
                            NexTechRecipeTypes.CHEMICAL_VAT,
                            com.gregtechceu.gtceu.common.data.GTRecipeTypes.LARGE_CHEMICAL_REACTOR_RECIPES)
                    .recipeModifiers(
                            com.gregtechceu.gtceu.api.recipe.modifier.GTRecipeModifiers.PARALLEL_HATCH,
                            ReactionModifiers.VAT_TEMPERATURE,
                            ReactionModifiers.PRESSURE,
                            com.gregtechceu.gtceu.api.recipe.modifier.GTRecipeModifiers::pyrolyseOvenOverclock)
                    .appearanceBlock(() -> ForgeRegistries.BLOCKS
                            .getValue(new ResourceLocation("gtceu", "inert_machine_casing")))
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle("AABBBBBAA", "AABAAABAA", "ABBAAABBA", "ABAAAAABA", "ABAAAAABA",
                                    "ABAAAAABA", "ABBAAABBA", "AABAAABAA", "AABBBBBAA")
                            .aisle("ABBBBBBBA", "ABBBBBBBA", "BBAAAAABB", "BBAAAAABB", "BBAAAAABB",
                                    "BBAAAAABB", "BBAAAAABB", "AABBBBBAA", "AABBBBBAA")
                            .aisle("BBBBBBBBB", "BBBCCCBBB", "BADDDDDAB", "AADEEEDAA", "AADEEEDAA",
                                    "AADEEEDAA", "BADDDDDAB", "BBBCCCBBB", "BBBBBBBBB")
                            .aisle("BBBBBBBBB", "ABCCCCCBA", "AADFFFDAA", "AAEAAAEAA", "AAEAAAEAA",
                                    "AAEAAAEAA", "AADFFFDAA", "ABCCCCCBA", "BBBBBBBBB")
                            .aisle("BBBBBBBBB", "ABCCCCCBA", "AADFHFDAA", "AAEAHAEAA", "AAEAHAEAA",
                                    "AAEAHAEAA", "AADFHFDAA", "ABCCCCCBA", "BBBBBBBBB")
                            .aisle("BBBBBBBBB", "ABCCCCCBA", "AADFFFDAA", "AAEAAAEAA", "AAEAAAEAA",
                                    "AAEAAAEAA", "AADFFFDAA", "ABCCCCCBA", "BBBBBBBBB")
                            .aisle("BBBBBBBBB", "BBBCCCBBB", "BADDDDDAB", "AADEEEDAA", "AADEEEDAA",
                                    "AADEEEDAA", "BADDDDDAB", "BBBCCCBBB", "BBBBBBBBB")
                            .aisle("ABBBBBBBA", "ABBBBBBBA", "BBAAAAABB", "BBAAAAABB", "BBAAAAABB",
                                    "BBAAAAABB", "BBAAAAABB", "AABBBBBAA", "AABBBBBAA")
                            .aisle("AABGBBAA", "AABAAABAA", "ABBAAABBA", "ABAAAAABA", "ABAAAAABA",
                                    "ABAAAAABA", "ABBAAABBA", "AABAAABAA", "AABBBBBAA")

                            .where("A", Predicates.any())
                            .where("B",
                                    Predicates.blocks(ForgeRegistries.BLOCKS
                                                    .getValue(new ResourceLocation("gtceu", "inert_machine_casing")))
                                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                                            .or(Predicates.abilities(PartAbility.MAINTENANCE)
                                                    .setExactLimit(1))
                                            .or(Predicates.abilities(PartAbility.PARALLEL_HATCH)
                                                    .setMaxGlobalLimited(1)))
                            .where("C",
                                    Predicates.blocks(ForgeRegistries.BLOCKS
                                            .getValue(new ResourceLocation("gtceu", "signalum_casing"))))
                            .where("D",
                                    Predicates.blocks(ForgeRegistries.BLOCKS
                                            .getValue(new ResourceLocation("gtceu", "nitinol_casing"))))
                            .where("E",
                                    Predicates.blocks(getKjsBlock("enderium_glass")))
                            .where("F",
                                    Predicates.blocks(ForgeRegistries.BLOCKS
                                            .getValue(new ResourceLocation("gtceu", "stable_machine_casing"))))
                            .where("G",
                                    Predicates.controller(Predicates.blocks(definition.get())))
                            .where("H", Predicates.heatingCoils())

                            .build())
                    .workableCasingModel(
                            new ResourceLocation("gtceu",
                                    "block/casings/solid/machine_casing_inert_ptfe"),
                            new ResourceLocation("gtceu",
                                    "block/machines/chemical_reactor"))
                    .register();

    public static void init() {}
}