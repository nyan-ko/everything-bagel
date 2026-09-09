package com.nyan.everybagel.recipes;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyan.everybagel.recipes.components.ComponentCookingRecipe;
import com.nyan.everybagel.recipes.components.ComponentTransformerList;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class AugmentedCookingRecipe extends ComponentCookingRecipe<AugmentedSmeltingRecipeInput> {
    public AugmentedCookingRecipe(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime, ComponentTransformerList transformers) {
        super(RecipeType.SMELTING, group, category, ingredient, result, experience, cookingTime, transformers);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.AUGMENTED_SMELTING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.SMELTING;
    }

    public ComponentTransformerList getTransformers() { return this.transformers; }

    public static class Serializer implements RecipeSerializer<AugmentedCookingRecipe> {
        public static final MapCodec<AugmentedCookingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(AbstractCookingRecipe::getGroup),
                CookingBookCategory.CODEC.optionalFieldOf("category", CookingBookCategory.MISC).forGetter(AbstractCookingRecipe::category),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(r -> r.ingredient),
                ItemStack.CODEC.fieldOf("result").forGetter(r -> r.result),
                Codec.FLOAT.optionalFieldOf("experience",0.0f).forGetter(AbstractCookingRecipe::getExperience),
                Codec.INT.optionalFieldOf("cookingtime", 200).forGetter(AbstractCookingRecipe::getCookingTime),
                ComponentTransformerList.CODEC.fieldOf("transformers").forGetter(AugmentedCookingRecipe::getTransformers)
        ).apply(instance, AugmentedCookingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, AugmentedCookingRecipe> STREAM_CODEC = StreamCodec.of(
                AugmentedCookingRecipe.Serializer::toNetwork, AugmentedCookingRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<AugmentedCookingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AugmentedCookingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static AugmentedCookingRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            String group = buf.readUtf();
            CookingBookCategory category = buf.readEnum(CookingBookCategory.class);
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buf);
            float experience = buf.readFloat();
            int cookingTime = buf.readVarInt();
            ComponentTransformerList transformers = ComponentTransformerList.STREAM_CODEC.decode(buf);
            return new AugmentedCookingRecipe(group, category, ingredient, itemstack, experience, cookingTime, transformers);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, AugmentedCookingRecipe recipe) {
            buf.writeUtf(recipe.group);
            buf.writeEnum(recipe.category());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.ingredient);
            ItemStack.STREAM_CODEC.encode(buf, recipe.result);
            buf.writeFloat(recipe.experience);
            buf.writeVarInt(recipe.cookingTime);
            ComponentTransformerList.STREAM_CODEC.encode(buf, recipe.transformers);
        }
    }
}
