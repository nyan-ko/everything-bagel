package com.nyan.everybagel.recipes.components;

import com.nyan.everybagel.recipes.SimpleRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class ComponentRecipeBuilder<T extends ComponentRecipe<?>> extends SimpleRecipeBuilder<T> {
    protected final ComponentTransformerList transformers;

    protected ComponentRecipeBuilder(ItemStack output) {
        super(output);

        this.transformers = ComponentTransformerList.of();
    }

    public ComponentRecipeBuilder<T> addTransformer(ComponentTransformer transformer) {
        this.transformers.add(transformer, Matcher.ALL);
        return this;
    }

    public ComponentRecipeBuilder<T> addTransformer(ComponentTransformer transformer, Matcher matcher) {
        this.transformers.add(transformer, matcher);
        return this;
    }
}
