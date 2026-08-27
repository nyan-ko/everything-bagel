package com.nyan.everybagel.recipes.components;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public abstract class ComponentCarryingRecipe<T extends ComponentRecipeInput> implements Recipe<T> {
    protected final ComponentTransformerList transformers;
    protected final ItemStack output;

    protected ComponentCarryingRecipe(ItemStack output) {
        this.transformers = new ComponentTransformerList();
        this.output = output;
    }

    protected ComponentCarryingRecipe(ComponentTransformerList transformers, ItemStack output) {
        this.transformers = transformers;
        this.output = output;
    }

    protected DataComponentPatch.Builder getPatchBuilder() {
        return DataComponentPatch.builder();
    }
}
