package com.nyan.everybagel.recipes.components;

import com.nyan.everybagel.recipes.SimpleRecipeBuilder;
import net.minecraft.world.item.ItemStack;

public abstract class ComponentRecipeBuilder<T extends ComponentRecipe<?>> extends SimpleRecipeBuilder<T> {
    protected final ComponentTransformerList transformers;

    protected ComponentRecipeBuilder(ItemStack output) {
        super(output);

        this.transformers = new ComponentTransformerList();
    }

    public ComponentRecipeBuilder<T> addTransformer(ComponentTransformer transformer) {
        this.transformers.add(transformer);
        return this;
    }
}
