package com.nyan.everybagel.gateau.powers;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyan.everybagel.shared.structs.SortedPairMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class PowerSet extends SortedPairMap<GateauPower.Resource, PowerSet.RankAmplifierPair> {
    public static final Codec<PowerSet> CODEC = Codec.pair(
            GateauPower.Resource.CODEC,
            RankAmplifierPair.CODEC
    ).listOf().xmap(list -> of(list.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))), map -> map.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), entry.getValue())).collect(Collectors.toList()));

    public static final PowerSet EMPTY = of();

    private PowerSet(TreeMap<GateauPower.Resource, RankAmplifierPair> map) {
        super(map);
    }

    public static PowerSet of(Map<GateauPower.Resource, RankAmplifierPair> map) {
        return new PowerSet(new TreeMap<>(map));
    }

    public static PowerSet of(Collection<GateauPower.Resource> keys) {
        TreeMap<GateauPower.Resource, RankAmplifierPair> map = new TreeMap<>();
        for (GateauPower.Resource resource : keys) {
            map.put(resource, new RankAmplifierPair(1, 1));
        }
        return new PowerSet(map);
    }

    public static PowerSet of(GateauPower.Resource... keys) {
        return of(Arrays.asList(keys));
    }

    public record RankAmplifierPair(int rank, int amplifier) {
        public static final Codec<RankAmplifierPair> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.optionalFieldOf("rank", 1).forGetter(RankAmplifierPair::rank),
                Codec.INT.optionalFieldOf("amplifier", 1).forGetter(RankAmplifierPair::amplifier)
        ).apply(instance, RankAmplifierPair::new));
    }
}
