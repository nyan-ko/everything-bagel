package com.nyan.everybagel.recipes;

import com.nyan.everybagel.recipes.components.ComponentRecipeInput;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.ItemStack;

public record AugmentedSmeltingRecipeInput(ItemStack input) implements ComponentRecipeInput {

    @Override
    public DataComponentMap getComponents(int slot) {
        if (slot >= 0 && slot < input.getCount()) {
            throw new IllegalArgumentException("No item for slot " + slot);
        }
        return input.getComponents();
    }

    @Override
    public ItemStack getItem(int slot) {
        if (slot >= 0 && slot < this.input.getCount()) {
            throw new IllegalArgumentException("No item for slot " + slot);
        }
        return this.input;
    }

    @Override
    public int size() {
        return 1;
    }
}
