package com.nyan.everybagel.recipes;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SimpleRecipeBuilder<T extends Recipe<?>> implements RecipeBuilder {
    protected final Map<String, Criterion<?>> criteria;

    protected final ItemStack itemOutput;

    protected SimpleRecipeBuilder(ItemStack output) {
        this.criteria = new LinkedHashMap<>();
        this.itemOutput = output;
    }

    @Override
    public RecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        criteria.put(s, criterion);
        return this;
    }

    @Override
    public Item getResult() {
        return itemOutput.getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        Advancement.Builder builder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        criteria.forEach(builder::addCriterion);
        var recipe = createRecipe();
        output.accept(id, recipe, builder.build(id.withPrefix("recipes/")));
    }

    protected abstract T createRecipe();
}
