package com.nyan.everybagel.gateau;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyan.everybagel.PairedListSet;
import com.nyan.everybagel.SimpleRegistryResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.*;
import java.util.stream.Collectors;

public class GateauSet extends PairedListSet<Gateau.Resource, GateauSet.QualityQuantityPair> {
    private int xor;
    private String printName;

    public static final Codec<GateauSet> CODEC = Codec.pair(
            Gateau.Resource.CODEC,
            QualityQuantityPair.CODEC
    ).listOf().xmap(list -> of(list.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))), map -> map.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), entry.getValue())).collect(Collectors.toList()));

    public static final GateauSet EMPTY = of();

    private GateauSet(TreeMap<Gateau.Resource, QualityQuantityPair> map) {
        super(map);
    }

    public static GateauSet of(Map<Gateau.Resource, QualityQuantityPair> map) {
        return new GateauSet(new TreeMap<>(map));
    }

    public static GateauSet of() {
        return of(List.of());
    }

    public static GateauSet of(Collection<Gateau.Resource> keys) {
        TreeMap<Gateau.Resource, QualityQuantityPair> map = new TreeMap<>();
        for (Gateau.Resource key : keys) {
            map.put(key, new QualityQuantityPair(1, 1));
        }
        return of(map);
    }

    public static GateauSet of(Gateau.Resource... keys) {
        return of(Arrays.asList(keys));
    }

    public static GateauSet of(GateauDefaults... defaults) {
        return of(Arrays.stream(defaults).map(GateauDefaults::getGateauKey).toList());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GateauSet other) {
            return this.keySet().equals(other.keySet());
        }
        return false;
    }

    @Override
    public int hashCode() {
        reXor();
        return xor;
    }

    private void reXor() {
        xor = 0;
        for (Gateau.Resource key : keySet()) {
            xor ^= key.hashCode();
        }
    }

    protected void updateName() {
        if (size() > 1) {
            printName = "many";
        }
        else if (size() == 1) {
            var resource = entrySet().iterator().next().getKey();
            var match = SimpleRegistryResolver.INSTANCE.resolve(resource.getKey(), Gateaux.GATEAU_REGISTRY_KEY);
            match.ifPresent(gateau -> printName = gateau.getId());
        }
        else {
            printName = "none";
        }
    }

    public String getName() {
        updateName();
        return printName;
    }

    @VisibleForTesting
    public void setName(String name) {
        printName = name;
    }

    @Override
    public String toString() {
        return "GateauSet{" +
                "printName='" + printName + '\'' +
                ", map=" + entrySet() +
                '}';
    }

    public record QualityQuantityPair(int quality, int quantity) {
        public static final Codec<QualityQuantityPair> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.optionalFieldOf("quality", 1).forGetter(QualityQuantityPair::quality),
                Codec.INT.optionalFieldOf("quantity", 1).forGetter(QualityQuantityPair::quantity)
        ).apply(instance, QualityQuantityPair::new));

        public QualityQuantityPair add(QualityQuantityPair other) {
            return new QualityQuantityPair(Math.max(quality, other.quality), quantity + other.quantity);
        }
    }
}
