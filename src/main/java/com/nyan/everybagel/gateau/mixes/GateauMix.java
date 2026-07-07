package com.nyan.everybagel.gateau.mixes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyan.everybagel.gateau.Gateau;
import com.nyan.everybagel.gateau.Gateaux;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GateauMix {
    public static class Inputs {
        private final List<ResourceKey<Gateau>> inputs;
        private final String key;

        public Inputs(List<ResourceKey<Gateau>> inputs) {
            this.inputs = inputs;
            var temp = new ArrayList<>(this.inputs);
            this.key = temp.stream().sorted().map(ResourceKey::toString).collect(Collectors.joining(", "));
        }

        public static Codec<Inputs> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ResourceKey.codec(Gateaux.GATEAU_REGISTRY_KEY).listOf().fieldOf("inputs").forGetter(Inputs::inputs)
        ).apply(inst, Inputs::new));

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Inputs && this.key.equals(((Inputs) obj).key);
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        public List<ResourceKey<Gateau>> inputs() { return this.inputs; }

    }
    public record Outputs(List<ResourceKey<Gateau>> outputs) {
        public static Codec<Outputs> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ResourceKey.codec(Gateaux.GATEAU_REGISTRY_KEY).listOf().fieldOf("outputs").forGetter(Outputs::outputs)
        ).apply(inst, Outputs::new));
    }
}
