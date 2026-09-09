package com.nyan.everybagel.recipes.components;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.crafting.RecipeInput;

public interface ComponentRecipeInput extends RecipeInput {
    DataComponentMap getComponents(int slot);
}
