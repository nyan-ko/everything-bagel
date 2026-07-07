package com.nyan.everybagel.gateau.powers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyan.everybagel.EverythingBagel;
import net.minecraft.resources.ResourceLocation;

public record JaggedPower(float minDamage, float maxDamage, float hpThreshold) implements GateauPower {
    public static final MapCodec<JaggedPower> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("min_damage").forGetter(JaggedPower::minDamage),
                    Codec.FLOAT.fieldOf("max_damage").forGetter(JaggedPower::maxDamage),
                    Codec.FLOAT.fieldOf("hp_threshold").forGetter(JaggedPower::hpThreshold)
            ).apply(instance, JaggedPower::new));

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "jagged_power");

    @Override
    public MapCodec<?> codec() {
        return CODEC;
    }

    @Override
    public ResourceLocation base() {
        return ID;
    }
}
