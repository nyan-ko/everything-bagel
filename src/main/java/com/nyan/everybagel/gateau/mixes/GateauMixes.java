package com.nyan.everybagel.gateau.mixes;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.nyan.everybagel.gateau.GateauSet;

import java.util.*;
import java.util.stream.Collectors;

public class GateauMixes {
    private static Map<GateauSet, GateauSet> toMap(List<Pair<GateauSet, GateauSet>> list) {
        return list.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }

    private static List<Pair<GateauSet, GateauSet>> toList(Map<GateauSet, GateauSet> map) {
        List<Pair<GateauSet, GateauSet>> list = new ArrayList<>();
        map.forEach((a, b) -> list.add(Pair.of(a, b)));
        return list;
    }

    public static final Codec<Map<GateauSet, GateauSet>> CODEC = Codec.pair(
            GateauSet.CODEC.fieldOf("inputs").codec(),
            GateauSet.CODEC.fieldOf("outputs").codec()
    ).listOf().xmap(GateauMixes::toMap, GateauMixes::toList);

    public static final GateauMixLoader MIXES = GateauMixLoader.INSTANCE;
}
