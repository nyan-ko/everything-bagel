package com.nyan.everybagel.recipes.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.recipes.components.transformers.IdentityTransformer;
import com.nyan.everybagel.recipes.components.transformers.ProjectionTransformer;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class ComponentTransformers {
    public static final ResourceKey<Registry<MapCodec<? extends ComponentTransformer>>> COMPONENT_TRANSFORMER_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "component_transformer"));

    public static final Registry<MapCodec<? extends ComponentTransformer>> COMPONENT_TRANSFORMER_TYPES = new RegistryBuilder<>(COMPONENT_TRANSFORMER_REGISTRY_KEY).create();

    public static final DeferredRegister<MapCodec<? extends ComponentTransformer>> COMPONENT_TRANSFORMER = DeferredRegister.create(COMPONENT_TRANSFORMER_TYPES, EverythingBagel.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends ComponentTransformer>, MapCodec<IdentityTransformer>> IDENTITY = COMPONENT_TRANSFORMER.register("identity_transformer", () -> IdentityTransformer.CODEC);
    public static final DeferredHolder<MapCodec<? extends ComponentTransformer>, MapCodec<ProjectionTransformer<?>>> PROJECTION = COMPONENT_TRANSFORMER.register("projection_transformer", () -> ProjectionTransformer.CODEC);

    public static final Codec<ComponentTransformer> CODEC = COMPONENT_TRANSFORMER_TYPES.byNameCodec().dispatch(ComponentTransformer::codec, mapCodec -> mapCodec);
    public static final StreamCodec<RegistryFriendlyByteBuf, ComponentTransformer> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);

    public static void register(IEventBus bus) {
        COMPONENT_TRANSFORMER.register(bus);
    }
}
