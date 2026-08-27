package com.nyan.everybagel.gateau;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.gateau.powers.GateauPowers;
import com.nyan.everybagel.gateau.powers.PowerSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(modid = EverythingBagel.MOD_ID)
public class Gateaux {
    public static final ResourceKey<Registry<Gateau>> GATEAU_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "gateau"));

    public static final DataMapType<Item, GateauSet> GATEAU_BY_ITEM = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "gateau_by_item"),
            Registries.ITEM,
            GateauSet.CODEC
    ).build();

    public static final Codec<Gateau> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(Gateau::getId),
                    Gateau.Visual.CODEC.fieldOf("look").forGetter(Gateau::getLook),
                    PowerSet.CODEC.fieldOf("powers").forGetter(Gateau::getPowers)
            ).apply(instance, Gateau::new)
    );

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                GATEAU_REGISTRY_KEY,
                CODEC,
                CODEC
        );
    }

    @SubscribeEvent
    public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(GATEAU_BY_ITEM);
    }
}
