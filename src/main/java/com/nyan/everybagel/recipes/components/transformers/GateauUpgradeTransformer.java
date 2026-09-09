package com.nyan.everybagel.recipes.components.transformers;

import com.mojang.serialization.MapCodec;
import com.nyan.everybagel.ModComponents;
import com.nyan.everybagel.gateau.GateauAssembler;
import com.nyan.everybagel.gateau.mixes.GateauMixes;
import com.nyan.everybagel.recipes.components.ComponentTransformer;
import net.minecraft.world.item.ItemStack;

public record GateauUpgradeTransformer() implements ComponentTransformer {
    public static final MapCodec<GateauUpgradeTransformer> CODEC = MapCodec.unit(GateauUpgradeTransformer::new);

    @Override
    public void apply(ItemStack result, ItemStack unused) {
        var gateaux = result.get(ModComponents.GATEAU.get());
        if (gateaux == null || gateaux.isEmpty()) {
            return;
        }
        var output = GateauAssembler.computeOutput(gateaux, GateauMixes.MIXES.getMixes());
        gateaux.addAll(output);
        result.set(ModComponents.GATEAU.get(), gateaux);
    }

    @Override
    public MapCodec<? extends ComponentTransformer> codec() {
        return CODEC;
    }
}
