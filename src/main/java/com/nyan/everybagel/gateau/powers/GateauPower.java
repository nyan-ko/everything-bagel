package com.nyan.everybagel.gateau.powers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyan.everybagel.EverythingBagel;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public interface GateauPower {
    MapCodec<?> codec();

    ResourceLocation base();

//    record Power1(int param1) implements GateauPower {
//        public static final MapCodec<Power1> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
//                Codec.INT.fieldOf("param1").forGetter(Power1::param1)
//        ).apply(instance, Power1::new));
//
//        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "power_1");
//
//        @Override
//        public MapCodec<Power1> codec() { return CODEC; }
//
//        @Override
//        public ResourceLocation base() { return ID; }
//    }
//
//    record Power2(int param1, Optional<Integer> param2) implements GateauPower {
//        public static final MapCodec<Power2> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
//                Codec.INT.fieldOf("param1").forGetter(Power2::param1),
//                Codec.INT.optionalFieldOf("param2").forGetter(Power2::param2)
//        ).apply(instance, Power2::new));
//
//        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "power_2");
//
//        @Override
//        public MapCodec<Power2> codec() { return CODEC; }
//
//        @Override
//        public ResourceLocation base() { return ID; }
//    }
}
