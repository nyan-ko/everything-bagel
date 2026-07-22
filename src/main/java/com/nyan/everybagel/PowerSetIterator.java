package com.nyan.everybagel;

import java.util.*;
import java.util.function.Supplier;

public class PowerSetIterator<T> implements Iterator<Set<T>> {
    private final int n;
    private final List<T> list;
    private static final Map<Integer, Integer> BIT_TO_INDEX = Map.of(
            1, 0,
            2, 1,
            4, 2,
            8, 3,
            16, 4,
            32, 5,
            64, 6,
            128, 7,
            256, 8
    );

    private int i;
    private int prev;
    private final Set<T> cur;

    public PowerSetIterator(Collection<T> objs, Supplier<? extends Set<T>> constructor) {
        this.n = objs.size();
        this.list = new ArrayList<>(objs);

        this.i = 1;
        this.prev = 0;
        this.cur = constructor.get();
    }

    @Override
    public boolean hasNext() {
        return i < (1 << n);
    }

    @Override
    public Set<T> next() {
        int grey = i ^ (i >> 1);
        int diff = grey ^ prev;

        if ((grey & diff) > 0) {
            cur.add(list.get(BIT_TO_INDEX.getOrDefault(diff, 0)));
        }
        else {
            cur.remove(list.get(BIT_TO_INDEX.getOrDefault(diff, 0)));
        }

        prev = grey;
        i++;
        return cur;
    }
}