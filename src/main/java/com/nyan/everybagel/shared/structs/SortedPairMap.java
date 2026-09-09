package com.nyan.everybagel.shared.structs;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SortedPairMap<D1 extends Comparable<D1>, D2> implements Collection<Map.Entry<D1, D2>> {
    private final TreeMap<D1, D2> backing;

    protected SortedPairMap(TreeMap<D1, D2> backing) {
        this.backing = backing;
    }

    @Override
    public int size() {
        return backing.size();
    }

    @Override
    public boolean isEmpty() {
        return backing.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return o != null && backing.containsKey(o);
    }

    @Override
    public @NotNull Iterator<Map.Entry<D1, D2>> iterator() {
        return backing.entrySet().iterator();
    }

    @Override
    public @NotNull Object[] toArray() {
        return backing.entrySet().toArray();
    }

    @Override
    public @NotNull <T> T[] toArray(@NotNull T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(Map.Entry<D1, D2> d1D2Entry) {
        return add(d1D2Entry.getKey(), d1D2Entry.getValue());
    }

    public boolean add(D1 key, D2 value) {
        backing.put(key, value);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Map.Entry)) {
            throw new UnsupportedOperationException();
        }
        var entry = (Map.Entry<D1, D2>) o;
        return backing.remove(entry.getKey(), entry.getValue());
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return backing.keySet().containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Map.Entry<D1, D2>> c) {
        for (var entry : c) {
            add(entry);
        }
        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return backing.keySet().removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return backing.keySet().retainAll(c);
    }

    @Override
    public void clear() {
        backing.clear();
    }

    public D2 get(D1 key) {
        return backing.get(key);
    }

    public D2 getOrDefault(D1 key, D2 defaultValue) {
        return backing.getOrDefault(key, defaultValue);
    }

    public Set<Map.Entry<D1, D2>> entrySet() {
        return backing.entrySet();
    }

    public Set<D1> keySet() {
        return backing.keySet();
    }
}
