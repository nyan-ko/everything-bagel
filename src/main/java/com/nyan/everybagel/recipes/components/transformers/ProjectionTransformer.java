package com.nyan.everybagel.recipes.components.transformers;

import com.mojang.serialization.MapCodec;
import com.nyan.everybagel.recipes.components.ComponentTransformer;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record ProjectionTransformer<T>(DataComponentType<T> query) implements ComponentTransformer {
    public static final MapCodec<ProjectionTransformer<?>> CODEC = DataComponentType.CODEC.fieldOf("query").xmap(ProjectionTransformer::new, ProjectionTransformer::query);

    @Override
    public void apply(ItemStack result, ItemStack input) {
        if (input.has(query)) {
            result.set(query, input.get(query));
        }
    }

    @Override
    public MapCodec<? extends ComponentTransformer> codec() {
        return CODEC;
    }
}
