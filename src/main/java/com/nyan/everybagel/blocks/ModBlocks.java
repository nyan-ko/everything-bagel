package com.nyan.everybagel.blocks;

import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EverythingBagel.MOD_ID);

    public static final DeferredBlock<Block> DOUGH_BLOCK = registerBlock("dough_block", () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));

    // TODO properties
    public static final DeferredBlock<MixingBowlBlock> MIXING_BOWL_BLOCK = registerBlock("mixing_bowl", () -> new MixingBowlBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<MillstoneBlock> MILLSTONE_BLOCK = registerBlock("millstone", () -> new MillstoneBlock(BlockBehaviour.Properties.of()));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> supplier) {
        DeferredBlock<T> block = BLOCKS.register(name, supplier);
        registerBlockItem(name, block);
        return block;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
