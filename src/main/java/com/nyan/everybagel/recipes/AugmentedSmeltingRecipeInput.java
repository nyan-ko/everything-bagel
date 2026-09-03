package com.nyan.everybagel.recipes;

import com.nyan.everybagel.recipes.components.ComponentRecipeInput;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.ItemStack;

public record AugmentedSmeltingRecipeInput(ItemStack input) implements ComponentRecipeInput {

    @Override
    public DataComponentMap getComponents() {
        return input.getComponents();
    }

    @Override
    public ItemStack getItem(int i) {
        if (i >= 0 && i < this.input.getCount()) {
            throw new IllegalArgumentException("No item for index " + i);
        }
        return this.input;
    }

    @Override
    public int size() {
        return 1;
    }
}
