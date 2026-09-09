package com.nyan.everybagel.recipes.components;

import com.nyan.everybagel.recipes.AugmentedSmeltingRecipeBuilder;
import com.nyan.everybagel.recipes.SimpleRecipeBuilder;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class ComponentCookingRecipeBuilder<T extends ComponentCookingRecipe<?>> extends SimpleRecipeBuilder<T> {
    protected String group;
    protected CookingBookCategory category;
    protected Ingredient ingredient;
    protected ItemStack result;
    protected float experience;
    protected int cookingTime;
    protected final ComponentTransformerList transformers;

    protected ComponentCookingRecipeBuilder(ItemStack output) {
        super(output);

        this.result = output;
        this.transformers = ComponentTransformerList.of();
    }

    public ComponentCookingRecipeBuilder<T> group(String group) {
        this.group = group;
        return this;
    }

    public ComponentCookingRecipeBuilder<T> addTransformer(ComponentTransformer transformer) {
        this.transformers.add(transformer, Matcher.ALL);
        return this;
    }

    public ComponentCookingRecipeBuilder<T> addTransformer(ComponentTransformer transformer, Matcher matcher) {
        this.transformers.add(transformer, matcher);
        return this;
    }

    public ComponentCookingRecipeBuilder<T> category(CookingBookCategory category) {
        this.category = category;
        return this;
    }

    public ComponentCookingRecipeBuilder<T> ingredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public ComponentCookingRecipeBuilder<T> result(ItemStack result) {
        this.result = result;
        return this;
    }

    public ComponentCookingRecipeBuilder<T> experience(float experience) {
        this.experience = experience;
        return this;
    }

    public ComponentCookingRecipeBuilder<T> cookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
        return this;
    }
}
