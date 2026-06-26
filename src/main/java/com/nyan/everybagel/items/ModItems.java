package com.nyan.everybagel.items;

import com.nyan.everybagel.EverythingBagel;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EverythingBagel.MOD_ID);

    public static final DeferredItem<Item> DOUGH = ITEMS.register("dough", () -> new DoughItem(new Item.Properties()));

    public static final DeferredItem<FlourItem> FLOUR = ITEMS.register("flour", () -> new FlourItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
