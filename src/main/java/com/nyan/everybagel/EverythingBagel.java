package com.nyan.everybagel;

import com.nyan.everybagel.blocks.ModBlocks;
import com.nyan.everybagel.blocks.entities.ModBlockEntities;
import com.nyan.everybagel.gateau.Gateau;
import com.nyan.everybagel.gateau.GateauSet;
import com.nyan.everybagel.gateau.Gateaux;
import com.nyan.everybagel.gateau.mixes.GateauMixLoader;
import com.nyan.everybagel.items.ModItems;
import com.nyan.everybagel.items.Tabs;
import com.nyan.everybagel.recipes.ModRecipes;
import com.nyan.everybagel.recipes.components.ComponentTransformers;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(EverythingBagel.MOD_ID)
public class EverythingBagel {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "everybagel";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public EverythingBagel(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        Tabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModComponents.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModRecipes.register(modEventBus);
        Effects.register(modEventBus);
        ComponentTransformers.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::registerRegistries);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            var provider = event.getParameters().holders();
            var registry = provider.lookupOrThrow(Gateaux.GATEAU_REGISTRY_KEY);
            registry.listElements().forEach(ref -> {
                var gateau = ref.value();
                var stack = new ItemStack(ModItems.FLOUR.get());
                var gateauSet = GateauSet.of(new Gateau.Resource(ref.key()));
//                gateauSet.resolve(provider);
                stack.set(ModComponents.GATEAU, gateauSet);
                stack.set(ModComponents.INGREDIENT, gateau.getLook().variation());
                stack.set(ModComponents.INGREDIENT_TINT, gateau.getLook().color());
                event.accept(stack);
            });
            var stack = new ItemStack(ModItems.BREAD.get());
            stack.set(ModComponents.INGREDIENT, "base");
            stack.set(ModComponents.INGREDIENT_TINT, -1);
            event.accept(stack);
//            var provider = event.getParameters().holders();
//            var registry = provider.lookupOrThrow(Gateaux.GATEAU_REGISTRY_KEY);
//            registry.listElements().forEach(ref -> {
//                var gateau = ref.value();
//                var stack = ModItems.FLOUR.toStack();
//                var gateauSet = GateauSet.of(new Gateau.Resource(ref.key()));
//                gateauSet.resolve();
//                stack.set(ModComponents.GATEAU, );
//                stack.set(ModComponents.INGREDIENT, gateau.getLook().variation());
//                stack.set(ModComponents.INGREDIENT_TINT, gateau.getLook().color());
//                event.accept(stack);
//            });
        }

//        else if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
//            event.accept(ModBlocks.DOUGH_BLOCK);
//        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event) {
//
//    }

    @SubscribeEvent
    public void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(GateauMixLoader.INSTANCE);
    }

    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.MIXING_BOWL_BE.get(),
                (be, side) -> be.getFluidTank()
        );
    }

    public void registerRegistries(NewRegistryEvent event) {
        event.register(ComponentTransformers.COMPONENT_TRANSFORMER_TYPES);
    }
}
