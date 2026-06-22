package com.nyan.everybagel.blocks.entities.shared;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class SimpleFluidInventory extends FluidTank {
    private final BlockEntity be;

    public SimpleFluidInventory(BlockEntity holder, int capacity) {
        super(capacity);
        this.be = holder;
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return true;
    }

    @Override
    protected void onContentsChanged() {
        be.setChanged();
        if (be.getLevel() != null && !be.getLevel().isClientSide()) {
            be.getLevel().sendBlockUpdated(be.getBlockPos(), be.getBlockState(), be.getBlockState(), 3);
        }
    }
}
