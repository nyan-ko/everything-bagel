package com.nyan.everybagel.recipes.components;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.ItemStack;

public interface ComponentTransformer {
    void apply(ItemStack result, ItemStack input);

    MapCodec<? extends ComponentTransformer> codec();
}
