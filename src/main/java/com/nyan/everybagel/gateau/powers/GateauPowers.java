package com.nyan.everybagel.gateau.powers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.nyan.everybagel.EverythingBagel;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import java.util.Map;

@EventBusSubscriber(modid = EverythingBagel.MOD_ID)
public class GateauPowers {
    public static final ResourceKey<Registry<GateauPower>> GATEAU_POWER_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "gateau_powers"));

    public static final ResourceKey<GateauPower> POWER11 = createPowerResourceKey("power11");

    public static final ResourceKey<GateauPower> JAGGED_WEAK = createPowerResourceKey("jagged_weak");
    public static final ResourceKey<GateauPower> JAGGED_MEDIUM = createPowerResourceKey("jagged_medium");
    public static final ResourceKey<GateauPower> JAGGED_STRONG = createPowerResourceKey("jagged_strong");

    private static ResourceKey<GateauPower> createPowerResourceKey(String key) {
        return ResourceKey.create(GATEAU_POWER_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, key));
    }

    public static final Map<ResourceLocation, MapCodec<? extends GateauPower>> POWER_CODECS_BY_RESOURCE = Map.of(
            JaggedPower.ID, JaggedPower.CODEC
    );

    public static final Codec<GateauPower> CODEC = ResourceLocation.CODEC.dispatch(
            "type",
            GateauPower::base,
            POWER_CODECS_BY_RESOURCE::get
    );

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                GATEAU_POWER_REGISTRY_KEY,
                CODEC,
                CODEC
        );
    }
}
