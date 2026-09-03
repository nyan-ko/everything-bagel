package com.nyan.everybagel.recipes;

import com.nyan.everybagel.recipes.components.ComponentCookingRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;

public class AugmentedSmeltingRecipeBuilder extends ComponentCookingRecipeBuilder<AugmentedCookingRecipe> {
    private AugmentedSmeltingRecipeBuilder(ItemStack output) {
        super(output);
    }

    public static AugmentedSmeltingRecipeBuilder basic(Ingredient input, ItemStack output) {
        return (AugmentedSmeltingRecipeBuilder) new AugmentedSmeltingRecipeBuilder(output).group("").category(CookingBookCategory.FOOD).cookingTime(200).experience(0.5f).ingredient(input);
    }

    @Override
    protected AugmentedCookingRecipe createRecipe() {
        return new AugmentedCookingRecipe(group, category, ingredient, result, experience, cookingTime, transformers);
    }
}
