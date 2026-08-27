package com.nyan.everybagel.gateau;

import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.gateau.powers.GateauPower;
import com.nyan.everybagel.gateau.powers.GateauPowers;
import com.nyan.everybagel.gateau.powers.PowerSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;

public enum GateauDefaults {
    WOOD("wood", Gateau.Visual.of(140, 70, 0, "base"), PowerSet.of(new GateauPower.Resource(GateauPowers.PLACEHOLDER))),
    STONE("stone", Gateau.Visual.of(114, 114, 114, "coarse"), PowerSet.of(new GateauPower.Resource(GateauPowers.PLACEHOLDER))),
    GRAVEL("gravel", Gateau.Visual.of(104, 104, 104, "coarse"), PowerSet.of(new GateauPower.Resource(GateauPowers.PLACEHOLDER))),
    SAND("sand"),
    DIRT("dirt"),

    FLINT("flint"),
    BONE("bone"),
    SCULK("sculk"),

    COPPER("copper"),
    IRON("iron"),
    GOLD("gold"),
    NETHERITE("netherite"),
    
    COAL("coal"),
    AMETHYST("amethyst"),
    REDSTONE("redstone"),
    GLOWSTONE("glowstone"),
    NETHER_QUARTZ("nether_quartz"),
    PRISMARINE("prismarine"),
    LAPIS_LAZULI("lapis_lazuli"),
    DIAMOND("diamond"),
    EMERALD("emerald");

    private final TagKey<Item> tag;
    private final ResourceKey<Gateau> key;
    private final Gateau.Visual look;
    private final PowerSet powers;

    GateauDefaults(String id) {
        this(id, Gateau.Visual.PLACEHOLDER, PowerSet.EMPTY);
    }

    GateauDefaults(String id, Gateau.Visual look, PowerSet powers) {
        this.tag = createTagKey(id);
        this.key = createResourceKey(id);
        this.look = look;
        this.powers = powers;
    }

    public TagKey<Item> getTag() { return tag; }
    public Gateau.Resource getGateauKey() { return new Gateau.Resource(key); }
    public GateauSet getGateauSet(int quality, int quantity) { return GateauSet.of(Map.of(new Gateau.Resource(key), new GateauSet.QualityQuantityPair(quality, quantity))); }
    public GateauSet getGateauSet() { return getGateauSet(1, 1); }

    public Gateau.Visual getLook() { return look; }
    public PowerSet getPowers() { return powers; }

    private static ResourceKey<Gateau> createResourceKey(String id) {
        return ResourceKey.create(Gateaux.GATEAU_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "gateau/" + id));
    }

    private static TagKey<Item> createTagKey(String id) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "gateau/" + id));
    }

    private static int color(int r, int g, int b) {
        return FastColor.ARGB32.color(r, g, b);
    }
}
