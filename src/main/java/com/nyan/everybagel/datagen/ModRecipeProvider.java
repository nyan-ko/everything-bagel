package com.nyan.everybagel.datagen;

import com.nyan.everybagel.ModComponents;
import com.nyan.everybagel.ModItemTags;
import com.nyan.everybagel.items.ModItems;
import com.nyan.everybagel.recipes.AugmentedSmeltingRecipeBuilder;
import com.nyan.everybagel.recipes.MillstoneRecipeBuilder;
import com.nyan.everybagel.recipes.MixingBowlRecipeBuilder;
import com.nyan.everybagel.recipes.components.Matcher;
import com.nyan.everybagel.recipes.components.transformers.GateauAggregationTransformer;
import com.nyan.everybagel.recipes.components.transformers.GateauUpgradeTransformer;
import com.nyan.everybagel.recipes.components.transformers.ProjectionTransformer;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        MixingBowlRecipeBuilder.of(ModItems.DOUGH.toStack())
                .requireItem(Ingredient.of(ModItems.FLOUR.get()))
                .requireFluid(FluidIngredient.of(Fluids.WATER))
                .addTransformer(new GateauAggregationTransformer(), Matcher.ALL)
                .addTransformer(new GateauUpgradeTransformer(), Matcher.NONE)
                .unlockedBy("has_flour", has(ModItems.FLOUR))
                .save(recipeOutput);
        MillstoneRecipeBuilder.of(Ingredient.of(ModItemTags.MILLSTONE_INPUT), ModItems.FLOUR.toStack())
                .unlockedBy("has_millstone", has(ModItems.FLOUR))
                .save(recipeOutput);
        AugmentedSmeltingRecipeBuilder.basic(Ingredient.of(ModItems.DOUGH), ModItems.BREAD.toStack())
                .addTransformer(new ProjectionTransformer<>(ModComponents.GATEAU.get()))
                .unlockedBy("has_bread", has(ModItems.BREAD))
                .save(recipeOutput);

    }
}
