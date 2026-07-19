package com.nyan.everybagel.gateau;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class GateauSet implements Set<Gateau.Key> {
    private final TreeSet<Gateau.Key> set;
    private boolean dirty;
    private int xor;

    public static final Codec<GateauSet> CODEC = Gateau.Key.CODEC.listOf().xmap(GateauSet::of, set -> set.stream().toList());

    private GateauSet(TreeSet<Gateau.Key> set) {
        this.set = set;
        this.dirty = false;
        this.xor = 0;
        reXor();
    }

    public static GateauSet of() {
        return of(Collections.emptySet());
    }

    public static GateauSet of(Collection<Gateau.Key> keys) {
        return new GateauSet(new TreeSet<>(keys));
    }

    public static GateauSet of(Gateau.Key... keys) {
        return of(Arrays.asList(keys));
    }

    public static GateauSet of(GateauDefaults... defaults) {
        return of(Arrays.stream(defaults).map(GateauDefaults::getKey).collect(Collectors.toList()));
    }

    private void reXor() {
        xor = 0;
        for (Gateau.Key key : this.set) {
            xor ^= key.hashCode();
        }
    }

    @Override
    public int hashCode() {
        if (dirty) {
            reXor();
            dirty = false;
        }
        return xor;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GateauSet && obj.hashCode() == this.hashCode();
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    public @NotNull Iterator<Gateau.Key> iterator() {
        return set.iterator();
    }

    @Override
    public @NotNull Object[] toArray() {
        return set.toArray();
    }

    @Override
    public @NotNull <T> T[] toArray(@NotNull T[] a) {
        return set.toArray(a);
    }

    @Override
    public boolean add(Gateau.Key key) {
        if (set.add(key)) {
            xor ^= key.hashCode();
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (set.remove(o)) {
            xor ^= o.hashCode();
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return set.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Gateau.Key> c) {
        dirty = set.addAll(c);
        return dirty;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        dirty = set.retainAll(c);
        return dirty;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        dirty = set.removeAll(c);
        return dirty;
    }

    @Override
    public void clear() {
        dirty = false;
        xor = 0;
        set.clear();
    }

    @Override
    public String toString() {
        if (size() == 1) {
            return set.getFirst().toString();
        }
        else {
            return "" + size();
        }
    }

    public TreeSet<Gateau.Key> getSet() { return set; }
}
