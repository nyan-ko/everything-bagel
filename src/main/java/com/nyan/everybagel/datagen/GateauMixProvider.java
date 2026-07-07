package com.nyan.everybagel.datagen;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.gateau.Gateau;
import com.nyan.everybagel.gateau.Gateaux;
import com.nyan.everybagel.gateau.mixes.GateauMix;
import com.nyan.everybagel.gateau.mixes.GateauMixes;
import com.nyan.everybagel.gateau.powers.GateauPower;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GateauMixProvider implements DataProvider {
    private final PackOutput packOutput;

    public GateauMixProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    public static Map.Entry<GateauMix.Inputs, GateauMix.Outputs> squish(List<ResourceKey<Gateau>> inputs, List<ResourceKey<Gateau>> outputs) {
        return Map.entry(new GateauMix.Inputs(inputs), new GateauMix.Outputs(outputs));
    }

    private static Map<GateauMix.Inputs, GateauMix.Outputs> getContents() {
        return ImmutableMap.<GateauMix.Inputs, GateauMix.Outputs>builder()
//                .put(squish(List.of(Gateaux.DEFAULT, Gateaux.DEFAULT2), List.of(Gateaux.DEFAULT2)))
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
        return "Gateau Mixes: " + EverythingBagel.MOD_ID;
    }
}
