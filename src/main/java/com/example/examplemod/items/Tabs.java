package com.example.examplemod.items;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class Tabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleMod.MODID);

    public static final Supplier<CreativeModeTab> INGREDIENTS_TAB = TABS.register("ingredients_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.DOUGH.get()))
            .title(Component.translatable("creativetab.examplemod.ingredients"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModItems.DOUGH.get());
                output.accept(ModBlocks.DOUGH_BLOCK);
            })
            .build());

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
