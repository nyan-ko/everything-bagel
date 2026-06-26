package com.nyan.everybagel.recipes;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MixingBowlRecipeBuilder implements RecipeBuilder {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    private ItemStack itemOutput;
    private FluidIngredient fluidInput;
    private NonNullList<Ingredient> itemInputs;

    public MixingBowlRecipeBuilder(NonNullList<Ingredient> itemInputs, FluidIngredient fluidInput, ItemStack itemOutput) {
        this.itemInputs = itemInputs;
        this.itemOutput = itemOutput;
        this.fluidInput = fluidInput;
    }

    public static MixingBowlRecipeBuilder of(ItemStack itemOutput) {
        return new MixingBowlRecipeBuilder(NonNullList.create(), FluidIngredient.empty(), itemOutput);
    }

    public MixingBowlRecipeBuilder requireItem(Ingredient item) {
        this.itemInputs.add(item);
        return this;
    }

    public MixingBowlRecipeBuilder requireItems(List<Ingredient> items) {
        this.itemInputs.addAll(items);
        return this;
    }

    public MixingBowlRecipeBuilder requireFluid(FluidIngredient fluid) {
        this.fluidInput = fluid;
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
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
        MixingBowlRecipe recipe = new MixingBowlRecipe(itemInputs, fluidInput, itemOutput);
        output.accept(id, recipe, builder.build(id.withPrefix("recipes/")));
    }
}
