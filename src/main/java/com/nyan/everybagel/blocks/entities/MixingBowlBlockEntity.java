package com.nyan.everybagel.blocks.entities;

import com.nyan.everybagel.blocks.entities.shared.SimpleItemInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class MixingBowlBlockEntity extends BlockEntity {
    public static final int ITEM_CAPACITY = 5;
    public static final int FLUID_CAPACITY = 4000;

    private final ItemStackHandler inventory;
    private final FluidTank fluidTank;

    public MixingBowlBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MIXING_BOWL_BE.get(), pos, blockState);
        this.inventory = new SimpleItemInventory(this, ITEM_CAPACITY);
        this.fluidTank = new FluidTank(FLUID_CAPACITY);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        fluidTank.writeToNBT(registries, tag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        fluidTank.readFromNBT(registries, tag);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    public final ItemStackHandler getInventory() { return inventory; }
    public final FluidTank getFluidTank() { return fluidTank; }
}
