package com.nyan.everybagel.gateau;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class GateauSet implements Collection<Map.Entry<Gateau.Key, GateauSet.QualityQuantity>> {
    private final TreeMap<Gateau.Key, QualityQuantity> map;
    private boolean dirty;
    private String printName;
    private int xor;

    public static final Codec<GateauSet> CODEC = Codec.pair(
            Gateau.Key.CODEC,
            QualityQuantity.CODEC
    ).listOf().xmap(list -> of(list.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))), map -> map.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), entry.getValue())).collect(Collectors.toList()));

    public static final GateauSet EMPTY = of();

    private GateauSet(TreeMap<Gateau.Key, QualityQuantity> map) {
        this.map = map;
        this.dirty = true;
        this.printName = getName();
        this.xor = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GateauSet) {
            GateauSet other = (GateauSet) obj;
            return this.map.keySet().equals(other.map.keySet());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (dirty) {
            reXor();
            dirty = false;
        }
        return xor;
    }

    private void reXor() {
        xor = 0;
        for (Gateau.Key key : map.keySet()) {
            xor ^= key.hashCode();
        }
    }

    public static GateauSet of(Map<Gateau.Key, QualityQuantity> map) {
        return new GateauSet(new TreeMap<>(map));
    }

    public static GateauSet of() {
        return of(List.of());
    }

    public static GateauSet of(Collection<Gateau.Key> keys) {
        TreeMap<Gateau.Key, QualityQuantity> map = new TreeMap<>();
        for (Gateau.Key key : keys) {
            map.put(key, new QualityQuantity(1, 1));
        }
        return of(map);
    }

    public static GateauSet of(Gateau.Key... keys) {
        return of(Arrays.asList(keys));
    }

    public static GateauSet of(GateauDefaults... defaults) {
        return of(Arrays.stream(defaults).map(GateauDefaults::getGateauKey).toList());
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return o instanceof Gateau.Key && map.containsKey(o);
    }

    @Override
    public @NotNull Iterator<Map.Entry<Gateau.Key, QualityQuantity>> iterator() {
        return map.entrySet().iterator();
    }

    @Override
    public @NotNull Object[] toArray() {
        return map.entrySet().toArray();
    }

    @Override
    public @NotNull <T> T[] toArray(@NotNull T[] a) {
        throw new UnsupportedOperationException();
    }

    public void put(Gateau.Key key, QualityQuantity value) {
        dirty = true;
        map.merge(key, value, QualityQuantity::add);
    }

    @Override
    public boolean add(Map.Entry<Gateau.Key, QualityQuantity> entry) {
        put(entry.getKey(), entry.getValue());
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Map.Entry)) {
            throw new UnsupportedOperationException();
        }
        var entry = (Map.Entry<Gateau.Key, QualityQuantity>) o;
        dirty = true;
        return map.remove(entry.getKey(), entry.getValue());
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return map.keySet().containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Map.Entry<Gateau.Key, QualityQuantity>> c) {
        dirty = true;
        for (Map.Entry<Gateau.Key, QualityQuantity> entry : c) {
            put(entry.getKey(), entry.getValue());
        }
        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        dirty = true;
        return map.keySet().removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        dirty = true;
        return map.keySet().retainAll(c);
    }

    @Override
    public void clear() {
        dirty = true;
        map.clear();
    }

    public Set<Map.Entry<Gateau.Key, QualityQuantity>> entrySet() {
        return map.entrySet();
    }

    public String getName() {
        if (dirty) {
            // todo actual naming
            if (size() > 1) {
                printName = "many";
            }
            else if (size() == 1) {
                var key = map.keySet().iterator().next();

                var connection = Minecraft.getInstance().getConnection();
                if (connection != null) {
                    var registry = connection.registryAccess().registry(Gateaux.GATEAU_REGISTRY_KEY);
                    registry.ifPresent(r -> printName = r.get(key.key()).getId());
                }
                else {
                    printName = "error";
                }
            }
            else {
                printName = "none";
            }
        }
        return printName;
    }

    @Override
    public String toString() {
        return "GateauSet{" +
                "map=" + map +
                '}';
    }

    // todo need a better name
    public record QualityQuantity(int quality, int quantity) {
        public static final Codec<QualityQuantity> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.INT.optionalFieldOf("quality", 1).forGetter(QualityQuantity::quality),
                Codec.INT.optionalFieldOf("quantity", 1).forGetter(QualityQuantity::quantity)
        ).apply(inst, QualityQuantity::new));

        public QualityQuantity add(QualityQuantity other) {
            return new QualityQuantity(Math.max(quality, other.quality), quantity + other.quantity);
        }

        public QualityQuantity subtract(QualityQuantity other) {
            return new QualityQuantity(Math.max(quality, other.quality), quantity - other.quantity);
        }
    }
}
