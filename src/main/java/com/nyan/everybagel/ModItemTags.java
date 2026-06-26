package com.nyan.everybagel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> MOD_DEBUG = create("debug");

    public static final TagKey<Item> MIXING_BOWL_INPUT = create("mixing_bowl_input");

    private static TagKey<Item> create(String id) {
        System.out.println("Creating " + id);
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, id));
    }
}
