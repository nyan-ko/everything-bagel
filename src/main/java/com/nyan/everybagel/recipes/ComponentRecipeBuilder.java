package com.nyan.everybagel.recipes;

import com.nyan.everybagel.recipes.components.ComponentCarryingRecipe;
import com.nyan.everybagel.recipes.components.ComponentTransformer;
import com.nyan.everybagel.recipes.components.ComponentTransformerList;
import net.minecraft.world.item.ItemStack;

public abstract class ComponentRecipeBuilder<T extends ComponentCarryingRecipe<?>> extends SimpleRecipeBuilder<T> {
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
