package com.nyan.everybagel;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;

public class PowerSet<T extends Comparable<T>, C extends Collection<T>> implements Iterable<C> {
    private final Supplier<C> constructor;
    private TreeSet<T> set;

    public PowerSet(Collection<T> c, Supplier<C> constructor) {
        this.set = new TreeSet<>(c);
        this.constructor = constructor;
    }

    public boolean add(T t) {
        return set.add(t);
    }

    public boolean contains(T t) {
        return set.contains(t);
    }

    @Override
    public @NotNull Iterator<C> iterator() {
        return new PowerSetIterator(set.size());
    }

    public class PowerSetIterator implements Iterator<C> {
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
        private C cur;

        private PowerSetIterator(int n) {
            this.n = n;
            this.list = new ArrayList<>(set);

            this.i = 1;
            this.prev = 0;
            this.cur = constructor.get();
        }

        @Override
        public boolean hasNext() {
            return i < (1 << n);
        }

        @Override
        public C next() {
            int grey = i ^ (i >> 1);
            int diff = grey ^ prev;

            if ((grey & diff) == 1) {
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
}
