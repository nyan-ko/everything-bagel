package com.nyan.everybagel.recipes.components;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ComponentTransformerList extends ArrayList<ComponentTransformer> {
    public ComponentTransformerList() {
        super();
    }

    public ComponentTransformerList(Collection<? extends ComponentTransformer> c) {
        super(c);
    }

    public static final Codec<ComponentTransformerList> CODEC = ComponentTransformers.CODEC.listOf().xmap(ComponentTransformerList::new, list -> list);
    public static final StreamCodec<RegistryFriendlyByteBuf, ComponentTransformerList> STREAM_CODEC = StreamCodec.of(ComponentTransformerList::toNetwork, ComponentTransformerList::fromNetwork);

    private static void toNetwork(RegistryFriendlyByteBuf buf, ComponentTransformerList list) {
        ByteBufCodecs.INT.encode(buf, list.size());
        for (ComponentTransformer transformer : list) {
            ComponentTransformers.STREAM_CODEC.encode(buf, transformer);
        }
    }

    private static ComponentTransformerList fromNetwork(RegistryFriendlyByteBuf buf) {
        int size = buf.readInt();
        ComponentTransformerList list = new ComponentTransformerList();
        for (int i = 0; i < size; i++) {
            list.add(ComponentTransformers.STREAM_CODEC.decode(buf));
        }
        return list;
    }
}
