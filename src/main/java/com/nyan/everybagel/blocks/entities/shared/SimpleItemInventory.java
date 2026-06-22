package com.nyan.everybagel.blocks.entities.shared;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;

public class SimpleItemInventory extends ItemStackHandler {
    private final BlockEntity be;

    public SimpleItemInventory(BlockEntity holder, int slots) {
        super(slots);
        this.be = holder;
    }

    @Override
    protected int getStackLimit(int slot, ItemStack stack) {
        return 64;
    }

    @Override
    protected void onContentsChanged(int slot) {
        be.setChanged();
        if (be.getLevel() != null && !be.getLevel().isClientSide()) {
            be.getLevel().sendBlockUpdated(be.getBlockPos(), be.getBlockState(), be.getBlockState(), 3);
        }
    }
}
