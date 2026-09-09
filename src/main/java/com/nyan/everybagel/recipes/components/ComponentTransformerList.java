package com.nyan.everybagel.recipes.components;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.nyan.everybagel.shared.structs.OrderedPairMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ComponentTransformerList extends OrderedPairMap<ComponentTransformer, Matcher> {
//    public static final Codec<ComponentTransformerList> CODEC = Codec.mapPair(ComponentTransformers.MAP_CODEC, Ingredient.CODEC.fieldOf("ingredient")).codec().listOf().xmap(list -> of(list.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))), map -> map.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), entry.getValue())).collect(Collectors.toList()));

    public static final Codec<ComponentTransformerList> CODEC = Codec.mapPair(ComponentTransformers.MAP_CODEC, Matcher.MAP_CODEC).codec().listOf().xmap(list -> of(list.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))), map -> map.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), entry.getValue())).collect(Collectors.toList()));

    public static final StreamCodec<RegistryFriendlyByteBuf, ComponentTransformerList> STREAM_CODEC = StreamCodec.of(ComponentTransformerList::toNetwork, ComponentTransformerList::fromNetwork);

    private ComponentTransformerList(LinkedHashMap<ComponentTransformer, Matcher> backing) {
        super(backing);
    }

    private ComponentTransformerList() {
        this(new LinkedHashMap<>());
    }

    public static ComponentTransformerList of() {
        return new ComponentTransformerList();
    }

    public static ComponentTransformerList of(Map<ComponentTransformer, Matcher> backing) {
        return new ComponentTransformerList(new LinkedHashMap<>(backing));
    }

    public static ComponentTransformerList of(Collection<ComponentTransformer> backing) {
        LinkedHashMap<ComponentTransformer, Matcher> map = new LinkedHashMap<>();
        for (var transformer : backing) {
            map.put(transformer, Matcher.ALL);
        }
        return of(map);
    }

    private static void toNetwork(RegistryFriendlyByteBuf buf, ComponentTransformerList list) {
        ByteBufCodecs.INT.encode(buf, list.size());
        for (Map.Entry<ComponentTransformer, Matcher> entry : list.entrySet()) {
            ComponentTransformers.STREAM_CODEC.encode(buf, entry.getKey());
            Matcher.STREAM_CODEC.encode(buf, entry.getValue());
        }
    }

    private static ComponentTransformerList fromNetwork(RegistryFriendlyByteBuf buf) {
        int size = buf.readInt();
        ComponentTransformerList list = new ComponentTransformerList();
        for (int i = 0; i < size; i++) {
            var transformer = ComponentTransformers.STREAM_CODEC.decode(buf);
            var matcher = Matcher.STREAM_CODEC.decode(buf);
            list.add(transformer, matcher);
        }
        return list;
    }
}