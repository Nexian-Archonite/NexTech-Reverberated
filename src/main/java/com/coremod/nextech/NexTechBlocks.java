package com.coremod.nextech;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import org.jetbrains.annotations.NotNull;

import static com.coremod.nextech.NexTech.NEXTECH_REGISTRATE;

@SuppressWarnings("unused")
public class NexTechBlocks {

    private static @NotNull BlockEntry<Block> registerSimpleBlock(String name, String id, String texture,
                                                                  NonNullBiFunction<Block, Item.Properties, ? extends BlockItem> func) {
        return NEXTECH_REGISTRATE
                .block(id, Block::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(),
                        prov.models().cubeAll(ctx.getName(), NexTech.id("block/" + texture))))
                .lang(name)
                .item(func)
                .build()
                .register();
    }

    public static BlockEntry<Block> MORNILOY_HIGH_POWER_CASING = registerSimpleBlock(
            "&[morniloy]Morniloy-13§r High Power Casing", "morniloy-13_high_power_casing",
            "casings/high_power_casing", BlockItem::new);
    public static BlockEntry<Block> STURVENE_COMPUTER_CASING = registerSimpleBlock(
            "&[sturvene]Sturvene-7 Computer Casing", "sturvene-7_computer_casing",
            "casings/computer_casing/computer_casing", BlockItem::new);
    public static BlockEntry<Block> NEXIC_COMPUTER_CASING = registerSimpleBlock(
            "§bNexic Computer Casing", "nexic_computer_casing",
            "casings/advanced_computer_casing/advanced_computer_casing", BlockItem::new);
    public static BlockEntry<Block> STURVENE_COMPUTER_HEAT_VENT = registerSimpleBlock(
            "&[sturvene]Sturvene-7 Computer Heat Vent", "sturvene-7_computer_heat_vent",
            "casings/heat_vent/computer_heat_vent", BlockItem::new);

    public static void init() {}
}
