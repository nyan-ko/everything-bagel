package com.nyan.everybagel.gateau;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyan.everybagel.gateau.powers.GateauPower;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Gateau {
    private final String id;
    private final Visual look;
    private final List<ResourceKey<GateauPower>> powers;

    public Gateau(String id, Visual look, List<ResourceKey<GateauPower>> powers) {
        this.id = id;
        this.look  = look;
        this.powers = powers;
    }

    public String getId() { return id; }
    public Visual getLook() { return look; }
    public List<ResourceKey<GateauPower>> getPowers() { return powers; }

    public record Visual(int color, String variation) {
        public static final Codec<Visual> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("color").forGetter(Visual::color),
                Codec.STRING.fieldOf("variation").forGetter(Visual::variation)
        ).apply(instance, Visual::new));

        public static final Visual PLACEHOLDER = new Visual(FastColor.ARGB32.color(0, 0, 0), "base");

        public static Visual of(int r, int g, int b, String variation) {
            return new Visual(FastColor.ARGB32.color(r, g, b), variation);
        }
    }

    public record Key(ResourceKey<Gateau> key, int quality, int quantity) implements Comparable<Key> {
            public static final Codec<Key> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                    ResourceKey.codec(Gateaux.GATEAU_REGISTRY_KEY).fieldOf("key").forGetter(Key::key),
                    Codec.INT.optionalFieldOf("quality", 1).forGetter(Key::quality),
                    Codec.INT.optionalFieldOf("quantity", 1).forGetter(Key::quantity)
            ).apply(inst, Key::new));

            public Key(ResourceKey<Gateau> key) {
                this(key, 1, 1);
            }

            @Override
            public int compareTo(@NotNull Gateau.Key o) {
                var opath = o.key.location().getPath();
                var mepath = this.key.location().getPath();
                return opath.compareTo(mepath);
            }

            @Override
            public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;
                Key key1 = (Key) o;
                return Objects.equals(key, key1.key);
            }

            @Override
            public int hashCode() {
                return key.location().hashCode();
            }

            @Override
            public String toString() {
                return key.toString() + " " + quality + " " + quantity;
            }
        }
}
