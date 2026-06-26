package com.nyan.everybagel.recipes;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import java.util.ArrayList;
import java.util.List;

public class MixingBowlRecipe implements Recipe<MixingBowlRecipeInput> {
    private final NonNullList<Ingredient> itemIngredients;
    private final FluidIngredient fluidIngredient;
    private final ItemStack output;

    public MixingBowlRecipe(NonNullList<Ingredient> itemIngredients, FluidIngredient fluidIngredient, ItemStack output) {
        this.itemIngredients = itemIngredients;
        this.fluidIngredient = fluidIngredient;
        this.output = output;
    }

    public MixingBowlRecipe(List<ItemStack> items, FluidStack fluid, ItemStack output) {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (ItemStack itemStack : items) {
            ingredients.add(Ingredient.of(itemStack));
        }
        this.itemIngredients = ingredients;
        this.fluidIngredient = FluidIngredient.of(fluid);
        this.output = output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return getItemIngredients();
    }

    @Override
    public boolean matches(MixingBowlRecipeInput input, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        var items = new ArrayList<ItemStack>();
        for (var item : input.items()) {
            if (!item.isEmpty()) {
                items.add(item);
            }
        }
        var matches = RecipeMatcher.findMatches(items, this.itemIngredients);
        if (matches == null) {
            return false;
        }

        return fluidIngredient.test(input.fluid());
//        return matches != null; // "condition is always true" ??
    }

    @Override
    public ItemStack assemble(MixingBowlRecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MIXING_BOWL_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MIXING_BOWL_TYPE.get();
    }

    public NonNullList<Ingredient> getItemIngredients() { return itemIngredients; }
    public FluidIngredient getFluidIngredient() { return fluidIngredient; }
    public ItemStack getOutput() { return output; }

    public static class Serializer implements RecipeSerializer<MixingBowlRecipe> {
        public static final MapCodec<MixingBowlRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY
                        .listOf()
                        .fieldOf("items")
                        .flatXmap(
                                ingredient -> {
                                    Ingredient[] arr = ingredient.toArray(Ingredient[]::new);
                                    if (arr.length == 0) {
                                        return DataResult.error(() -> "No ingredients for Mixing Bowl recipe");
                                    }
                                    else {
                                        return DataResult.success(NonNullList.of(Ingredient.EMPTY, arr));
                                    }
                                },
                                DataResult::success
                        )
                        .forGetter(MixingBowlRecipe::getItemIngredients),
                FluidIngredient.CODEC.fieldOf("fluid").forGetter(MixingBowlRecipe::getFluidIngredient),
                ItemStack.CODEC.fieldOf("result").forGetter(MixingBowlRecipe::getOutput)
        ).apply(inst, MixingBowlRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, MixingBowlRecipe> STREAM_CODEC = StreamCodec.of(
                MixingBowlRecipe.Serializer::toNetwork, MixingBowlRecipe.Serializer::fromNetwork
        );

        private static MixingBowlRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            int length = buf.readInt();
            NonNullList<Ingredient> items = NonNullList.withSize(length, Ingredient.EMPTY);
            items.replaceAll(i -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            FluidIngredient fluid = FluidIngredient.STREAM_CODEC.decode(buf);
            var result = ItemStack.STREAM_CODEC.decode(buf);
            return new MixingBowlRecipe(items, fluid, result);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, MixingBowlRecipe recipe) {
            buf.writeInt(recipe.itemIngredients.size());
            for (Ingredient ingredient : recipe.itemIngredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }
            FluidIngredient.STREAM_CODEC.encode(buf, recipe.fluidIngredient);
            ItemStack.STREAM_CODEC.encode(buf, recipe.output);
        }

        @Override
        public MapCodec<MixingBowlRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MixingBowlRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
