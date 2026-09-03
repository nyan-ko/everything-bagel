package com.nyan.everybagel.recipes;

import com.nyan.everybagel.EverythingBagel;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, EverythingBagel.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, EverythingBagel.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MixingBowlRecipe>> MIXING_BOWL_SERIALIZER = SERIALIZERS.register("mixing_bowl", MixingBowlRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MixingBowlRecipe>> MIXING_BOWL_TYPE = TYPES.register("mixing_bowl", () -> new RecipeType<MixingBowlRecipe>() {
        @Override
        public String toString() {
            return "mixing_bowl";
        }
    });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MillstoneRecipe>> MILLSTONE_SERIALIZER = SERIALIZERS.register("millstone", MillstoneRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MillstoneRecipe>> MILLSTONE_TYPE = TYPES.register(
            "millstone", () -> new RecipeType<MillstoneRecipe>() {
                @Override
                public String toString() {
                    return "millstone";
                }
            }
    );

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AugmentedCookingRecipe>> AUGMENTED_SMELTING_SERIALIZER = SERIALIZERS.register("augmented_smelting", AugmentedCookingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<AugmentedCookingRecipe>> AUGMENTED_SMELTING_TYPE = TYPES.register("augmented_smelting", () -> new RecipeType<AugmentedCookingRecipe>() {
        @Override
        public String toString() {
            return "augmented_smelting";
        }
    });

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
        TYPES.register(bus);
    }
}

