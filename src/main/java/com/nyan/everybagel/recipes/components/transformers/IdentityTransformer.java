package com.nyan.everybagel.recipes.components.transformers;

import com.mojang.serialization.MapCodec;
import com.nyan.everybagel.recipes.components.ComponentTransformer;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record IdentityTransformer() implements ComponentTransformer {
    public static final MapCodec<IdentityTransformer> CODEC = MapCodec.unit(IdentityTransformer::new);

    @Override
    public void apply(ItemStack result, ItemStack input) {
        for (var component : input.getComponents()) {
            result.set((DataComponentType) component.type(), component.value());
        }
    }

    @Override
    public MapCodec<IdentityTransformer> codec() {
        return CODEC;
    }
}
