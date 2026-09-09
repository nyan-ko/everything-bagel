package com.nyan.everybagel.recipes.components;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public abstract class ComponentRecipe<T extends ComponentRecipeInput> implements Recipe<T> {
    protected final ComponentTransformerList transformers;
    protected final ItemStack output;

    protected ComponentRecipe(ItemStack output) {
        this.transformers = ComponentTransformerList.of();
        this.output = output;
    }

    protected ComponentRecipe(ComponentTransformerList transformers, ItemStack output) {
        this.transformers = transformers;
        this.output = output;
    }

    @Override
    public ItemStack assemble(T input, HolderLookup.Provider provider) {
        var result = output.copy();

        for (var pair : transformers) {
            var transformer = pair.getKey();
            var matcher = pair.getValue();

            if (matcher.none()) {
                transformer.apply(result, ItemStack.EMPTY);
            }
            else {
                for (int i = 0; i < input.size(); i++) {
                    var item = input.getItem(i);
                    if (matcher.test(item)) {
                        transformer.apply(result, item);
                    }
                }
            }
        }
        return result;
    }
}
