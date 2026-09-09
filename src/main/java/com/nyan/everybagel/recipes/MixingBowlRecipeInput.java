package com.nyan.everybagel.recipes;

import com.nyan.everybagel.recipes.components.ComponentRecipeInput;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public record MixingBowlRecipeInput(List<ItemStack> items, FluidStack fluid) implements ComponentRecipeInput {
    public FluidStack getFluid() {
        return fluid;
    }

    @Override
    public ItemStack getItem(int index) {
        return items.get(index);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public DataComponentMap getComponents(int slot) {
        if (slot < 0 || slot >= items.size()) {
            throw new IllegalArgumentException("No item for slot " + slot);
        }
        return getItem(slot).getComponents();
    }
}
