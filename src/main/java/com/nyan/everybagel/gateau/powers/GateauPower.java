package com.nyan.everybagel.gateau.powers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import org.jetbrains.annotations.NotNull;

public abstract class GateauPower {
    protected GateauPower() {

    }

    public static class Effect<D extends Data> extends MobEffect {
        protected Effect(MobEffectCategory category, int color) {
            super(category, color);
        }

    }

    public abstract ResourceLocation getBase();

    public interface Data { }

    public record Resource(ResourceKey<GateauPower> key) implements Comparable<Resource> {
        public static final Codec<Resource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceKey.codec(GateauPowers.GATEAU_POWER_REGISTRY_KEY).fieldOf("key").forGetter(Resource::key)
        ).apply(instance, Resource::new));

        @Override
        public int compareTo(@NotNull GateauPower.Resource o) {
            return key.compareTo(o.key);
        }
    }
}