package com.nyan.everybagel;

import com.mojang.serialization.Codec;
import com.nyan.everybagel.gateau.Gateau;
import com.nyan.everybagel.gateau.GateauSet;
import com.nyan.everybagel.gateau.Gateaux;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

public class ModComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, EverythingBagel.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> INGREDIENT = register("ingredients", builder -> builder.persistent(Codec.sizeLimitedString(20)));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> INGREDIENT_TINT = register("ingredient_tint", builder -> builder.persistent(Codec.INT));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<GateauSet>> GATEAU = register("gateau", builder -> builder.persistent(GateauSet.CODEC));

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPE.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus bus) {
        DATA_COMPONENT_TYPE.register(bus);
    }
}

