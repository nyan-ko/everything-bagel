package com.nyan.everybagel.recipes.components;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public abstract class ComponentCookingRecipe<T extends ComponentRecipeInput> extends AbstractCookingRecipe {
    protected final ComponentTransformerList transformers;

    protected ComponentCookingRecipe(RecipeType<?> type, String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        this(type, group, category, ingredient, result, experience, cookingTime, new ComponentTransformerList());
    }

    protected ComponentCookingRecipe(RecipeType<?> type, String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime, ComponentTransformerList transformers) {
        super(type, group, category, ingredient, result, experience, cookingTime);

        this.transformers = transformers;
    }

    protected DataComponentPatch.Builder getPatchBuilder() {
        return DataComponentPatch.builder();
    }
}
