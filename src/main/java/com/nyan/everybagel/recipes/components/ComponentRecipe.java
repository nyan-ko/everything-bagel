package com.nyan.everybagel.recipes.components;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public abstract class ComponentRecipe<T extends ComponentRecipeInput> implements Recipe<T> {
    protected final ComponentTransformerList transformers;
    protected final ItemStack output;

    protected ComponentRecipe(ItemStack output) {
        this.transformers = new ComponentTransformerList();
        this.output = output;
    }

    protected ComponentRecipe(ComponentTransformerList transformers, ItemStack output) {
        this.transformers = transformers;
        this.output = output;
    }

    protected DataComponentPatch.Builder getPatchBuilder() {
        return DataComponentPatch.builder();
    }
}
