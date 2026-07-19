package com.nyan.everybagel.gateau;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GateauSet implements Set<Gateau.Key> {
    private final TreeSet<Gateau.Key> set;
    private boolean dirty;
    private int xor;

    public static final Codec<GateauSet> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Gateau.Key.CODEC.listOf().xmap(list -> new TreeSet<>(list), tree -> new ArrayList<>(tree)).fieldOf("contents").forGetter(GateauSet::getSet)
    ).apply(inst, GateauSet::new));

    public static final GateauSet EMPTY = new GateauSet();

//    public static final Codec<TreeSet<Gateau.Key>> CODEC = Gateau.Key.CODEC.listOf().xmap(list -> new TreeSet<>(list), tree -> new ArrayList<>(tree));

    public GateauSet() {
        this.set = new TreeSet<>();
        this.dirty = false;
        this.xor = 0;
    }

    public GateauSet(TreeSet<Gateau.Key> set) {
        this.set = set;
        this.dirty = false;
        this.xor = 0;
        reXor();
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

    public TreeSet<Gateau.Key> getSet() { return set; }
}
