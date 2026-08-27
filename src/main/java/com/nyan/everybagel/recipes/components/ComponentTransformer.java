package com.nyan.everybagel.recipes.components;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public interface ComponentTransformer {
    void apply(DataComponentPatch.Builder patch, DataComponentMap components, ItemStack input);

    MapCodec<? extends ComponentTransformer> codec();
}
