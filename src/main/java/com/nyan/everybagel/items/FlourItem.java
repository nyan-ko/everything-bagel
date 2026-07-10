package com.nyan.everybagel.items;

import com.nyan.everybagel.ModComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FlourItem extends Item {

    public FlourItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        var gateau = stack.get(ModComponents.GATEAU);
        return Component.literal(gateau.location().toString() + " flour");
    }
}
