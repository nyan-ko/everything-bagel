package com.nyan.everybagel.gateau.mixes;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GateauMixes {
    private static Map<GateauMix.Inputs, GateauMix.Outputs> toMap(List<Pair<GateauMix.Inputs, GateauMix.Outputs>> list) {
        return list.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }

    private static List<Pair<GateauMix.Inputs, GateauMix.Outputs>> toList(Map<GateauMix.Inputs, GateauMix.Outputs> map) {
        List<Pair<GateauMix.Inputs, GateauMix.Outputs>> list = new ArrayList<>();
        map.forEach((a, b) -> list.add(Pair.of(a, b)));
        return list;
    }

    public static final Codec<Map<GateauMix.Inputs, GateauMix.Outputs>> CODEC = Codec.pair(
            GateauMix.Inputs.CODEC,
            GateauMix.Outputs.CODEC
    ).listOf().xmap(GateauMixes::toMap, GateauMixes::toList);

    public static final GateauMixLoader mixes = GateauMixLoader.INSTANCE;
}
