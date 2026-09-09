package com.nyan.everybagel.recipes.components;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public abstract class ComponentCookingRecipe<T extends ComponentRecipeInput> extends AbstractCookingRecipe {
    protected final ComponentTransformerList transformers;

    protected ComponentCookingRecipe(RecipeType<?> type, String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        this(type, group, category, ingredient, result, experience, cookingTime, ComponentTransformerList.of());
    }

    protected ComponentCookingRecipe(RecipeType<?> type, String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime, ComponentTransformerList transformers) {
        super(type, group, category, ingredient, result, experience, cookingTime);

        this.transformers = transformers;
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider provider) {
        var output = result.copy();

        for (var pair : transformers) {
            var transformer = pair.getKey();
            var matcher = pair.getValue();

            var item = input.getItem(0);
            if (matcher.none()) {
                transformer.apply(output, ItemStack.EMPTY);
            }
            else if (matcher.test(item)) {
                transformer.apply(output, item);
            }
        }
        return output;
    }
}
