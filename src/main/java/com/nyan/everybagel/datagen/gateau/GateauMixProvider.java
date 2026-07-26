package com.nyan.everybagel.datagen.gateau;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.gateau.GateauDefaults;
import com.nyan.everybagel.gateau.GateauSet;
import com.nyan.everybagel.gateau.mixes.GateauMixes;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GateauMixProvider implements DataProvider {
    private final PackOutput packOutput;

    public GateauMixProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    private static Map<GateauSet, GateauSet> getContents() {
        return ImmutableMap.<GateauSet, GateauSet>builder()
                .put(GateauSet.of(GateauDefaults.WOOD, GateauDefaults.STONE), GateauSet.of(GateauDefaults.COAL))
                .put(GateauSet.of(GateauDefaults.COPPER, GateauDefaults.IRON), GateauSet.of(GateauDefaults.AMETHYST))
                .put(GateauSet.of(GateauDefaults.DIAMOND, GateauDefaults.IRON), GateauSet.of(GateauDefaults.AMETHYST))
                .build();
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        JsonElement encoded = GateauMixes.CODEC.encodeStart(JsonOps.INSTANCE, getContents()).getOrThrow(IllegalStateException::new);

        Path path = packOutput.getOutputFolder().resolve("data/everybagel/everybagel/gateau_mixes/gateau_mixes.json");

        return DataProvider.saveStable(output, encoded, path);
    }

    @Override
    public String getName() {
        return EverythingBagel.MOD_ID + ": Gateau Mixes";
    }
}
