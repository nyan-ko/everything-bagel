package com.nyan.everybagel.recipes.components.transformers;

import com.mojang.serialization.MapCodec;
import com.nyan.everybagel.ModComponents;
import com.nyan.everybagel.gateau.GateauSet;
import com.nyan.everybagel.recipes.components.ComponentTransformer;
import com.nyan.everybagel.shared.utils.GateauUtils;
import net.minecraft.world.item.ItemStack;

public record GateauAggregationTransformer() implements ComponentTransformer {
    public static final MapCodec<GateauAggregationTransformer> CODEC = MapCodec.unit(GateauAggregationTransformer::new);

    @Override
    public void apply(ItemStack result, ItemStack input) {
        result.update(ModComponents.GATEAU, GateauSet.EMPTY, GateauUtils.getGateau(input), this::update);
    }

    private GateauSet update(GateauSet original, GateauSet input) {
        if (input == null || input.isEmpty()) {
            return original;
        }

        original.addAll(input);
        return original;
    }

    @Override
    public MapCodec<? extends ComponentTransformer> codec() {
        return CODEC;
    }
}
