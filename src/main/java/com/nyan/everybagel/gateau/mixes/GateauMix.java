package com.nyan.everybagel.gateau.mixes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyan.everybagel.gateau.Gateau;
import com.nyan.everybagel.gateau.Gateaux;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class GateauMix {
//    public static class Input implements Collection<Gateau.Key> {
//
//        @Override
//        public int size() {
//            return 0;
//        }
//
//        @Override
//        public boolean isEmpty() {
//            return false;
//        }
//
//        @Override
//        public boolean contains(Object o) {
//            return false;
//        }
//
//        @Override
//        public @NotNull Iterator<Gateau.Key> iterator() {
//            return null;
//        }
//
//        @Override
//        public @NotNull Object[] toArray() {
//            return new Object[0];
//        }
//
//        @Override
//        public @NotNull <T> T[] toArray(@NotNull T[] a) {
//            return null;
//        }
//
//        @Override
//        public boolean add(Gateau.Key key) {
//            return false;
//        }
//
//        @Override
//        public boolean remove(Object o) {
//            return false;
//        }
//
//        @Override
//        public boolean containsAll(@NotNull Collection<?> c) {
//            return false;
//        }
//
//        @Override
//        public boolean addAll(@NotNull Collection<? extends Gateau.Key> c) {
//            return false;
//        }
//
//        @Override
//        public boolean removeAll(@NotNull Collection<?> c) {
//            return false;
//        }
//
//        @Override
//        public boolean retainAll(@NotNull Collection<?> c) {
//            return false;
//        }
//
//        @Override
//        public void clear() {
//
//        }
//    }
//    public static class Input extends ArrayList<Gateau.Key> {
//        private final String key;
//
//        public Input() {
//            super();
//        }
//
//        public Input(List<Gateau.Key> inputs) {
//            super(inputs);
//
//            this.key = stream().sorted().map(Gateau.Key::toString).collect(Collectors.joining(","));
//        }
//
////        public Input(List<ResourceKey<Gateau>> inputs) {
////            super(inputs);
////
////            var temp = new ArrayList<>(this.inputs);
////            this.key = temp.stream().sorted().map(ResourceKey::toString).collect(Collectors.joining(", "));
////        }
//
//        public static Codec<Input> CODEC = RecordCodecBuilder.create(inst -> inst.group(
//                Gateau.Key.CODEC.listOf().fieldOf("inputs").forGetter()
////                ResourceKey.codec(Gateaux.GATEAU_REGISTRY_KEY).listOf().fieldOf("inputs").forGetter(Input::inputs)
//        ).apply(inst, Input::new));
//
//        @Override
//        public boolean equals(Object obj) {
//            return obj instanceof Input && this.key.equals(((Input) obj).key);
//        }
//
//        @Override
//        public int hashCode() {
//            return key.hashCode();
//        }
//
//        public List<ResourceKey<Gateau>> inputs() { return this.; }
//
//    }

    public record Output(List<ResourceKey<Gateau>> outputs) {
        public static Codec<Output> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceKey.codec(Gateaux.GATEAU_REGISTRY_KEY).listOf().fieldOf("outputs").forGetter(Output::outputs)
        ).apply(instance, Output::new));
    }
}
