package com.nyan.everybagel.datagen.items;

import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.items.ModItems;
import com.nyan.everybagel.model.client.IngredientColor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.Objects;

public class IngredientModelProvider extends ItemModelProvider {

    public IngredientModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EverythingBagel.MOD_ID, existingFileHelper);
    }

    public ItemModelBuilder variedItem(Item item, String... variations) {
        return variedItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)), variations);
    }

    public ItemModelBuilder variedItem(ResourceLocation item, String... variations) {
        var builder = getBuilder("item/" + item.getPath())
                .customLoader(IngredientModelLoader::new).end();
        Arrays.stream(variations).forEach((name) -> builder.texture(name, "item/" + item.getPath() + "/" + name));
        return builder;
    }

    public static void registerTints(RegisterColorHandlersEvent.Item event) {
        event.register(IngredientColor::getTint, ModItems.FLOUR.value());
        event.register(IngredientColor::getTint, ModItems.DOUGH.value());
    }

    @Override
    protected void registerModels() {
        variedItem(ModItems.FLOUR.get(), "base", "coarse");
        variedItem(ModItems.DOUGH.get(), "base");
    }

    @Override
    public String getName() {
        return "Ingredient Item Models: " + modid;
    }
}
