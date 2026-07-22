package com.nyan.everybagel.blocks.entities;

import com.nyan.everybagel.blocks.entities.shared.SimpleFluidTank;
import com.nyan.everybagel.blocks.entities.shared.SimpleItemInventory;
import com.nyan.everybagel.recipes.MixingBowlRecipe;
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
    public static final int ITEM_SLOTS = 5;
    public static final int FLUID_CAPACITY = 4000;
    public static final int RECIPE_COMPLETE = 120;

    private final ItemStackHandler inventory;
    private final FluidTank fluidTank;
    private MixingBowlRecipe currentRecipe;
    private int recipeProgress = 0;

    public MixingBowlBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MIXING_BOWL_BE.get(), pos, blockState);
        this.inventory = new SimpleItemInventory(this, ITEM_SLOTS);
        this.fluidTank = new SimpleFluidTank(this, FLUID_CAPACITY);
    }

    public boolean mix(int increment) {
        recipeProgress = recipeProgress + increment;
        return recipeProgress >= RECIPE_COMPLETE;
    }

    public void resetProgress() {
        recipeProgress = 0;
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

    @Override
    public String toString() {
        return "MixingBowlBlockEntity{" +
                "inventory=" + inventory +
                ", fluidTank=" + fluidTank +
                ", currentRecipe=" + currentRecipe +
                ", recipeProgress=" + recipeProgress +
                '}';
    }

    public final ItemStackHandler getInventory() { return inventory; }
    public final FluidTank getFluidTank() { return fluidTank; }
    public final MixingBowlRecipe getCurrentRecipe() { return currentRecipe; }
    public final int getRecipeProgress() { return recipeProgress; }
}
