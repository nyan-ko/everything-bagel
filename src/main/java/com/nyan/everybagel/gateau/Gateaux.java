package com.nyan.everybagel.gateau;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.gateau.powers.GateauPower;
import com.nyan.everybagel.gateau.powers.GateauPowers;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import java.util.Map;

@EventBusSubscriber(modid = EverythingBagel.MOD_ID)
public class Gateaux {
    public static final ResourceKey<Registry<Gateau>> GATEAU_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "gateau"));

    public static final ResourceKey<Gateau> WOOD = createGateauResourceKey("wood");
    public static final ResourceKey<Gateau> STONE = createGateauResourceKey("stone");
    public static final ResourceKey<Gateau> GRAVEL = createGateauResourceKey("gravel");
    public static final ResourceKey<Gateau> SAND = createGateauResourceKey("sand");
    public static final ResourceKey<Gateau> DIRT = createGateauResourceKey("dirt");

    public static final ResourceKey<Gateau> FLINT = createGateauResourceKey("flint");
    public static final ResourceKey<Gateau> BONE = createGateauResourceKey("bone");
    public static final ResourceKey<Gateau> SCULK = createGateauResourceKey("sculk");

    public static final ResourceKey<Gateau> COPPER = createGateauResourceKey("copper");
    public static final ResourceKey<Gateau> IRON = createGateauResourceKey("iron");
    public static final ResourceKey<Gateau> GOLD = createGateauResourceKey("gold");
    public static final ResourceKey<Gateau> NETHERITE = createGateauResourceKey("netherite");

    public static final ResourceKey<Gateau> COAL = createGateauResourceKey("coal");
    public static final ResourceKey<Gateau> AMETHYST = createGateauResourceKey("amethyst");
    public static final ResourceKey<Gateau> REDSTONE = createGateauResourceKey("redstone");
    public static final ResourceKey<Gateau> GLOWSTONE = createGateauResourceKey("glowstone");
    public static final ResourceKey<Gateau> NETHER_QUARTZ = createGateauResourceKey("nether_quartz");
    public static final ResourceKey<Gateau> PRISMARINE = createGateauResourceKey("prismarine");
    public static final ResourceKey<Gateau> LAPIS_LAZULI = createGateauResourceKey("lapis_lazuli");
    public static final ResourceKey<Gateau> DIAMOND = createGateauResourceKey("diamond");
    public static final ResourceKey<Gateau> EMERALD = createGateauResourceKey("emerald");

    private static ResourceKey<Gateau> createGateauResourceKey(String key) {
        return ResourceKey.create(GATEAU_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, key));
    }

    public static final Codec<Gateau> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(Gateau::getId),
                    ResourceKey.codec(GateauPowers.GATEAU_POWER_REGISTRY_KEY).listOf().fieldOf("powers").forGetter(Gateau::getPowers)
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
}
