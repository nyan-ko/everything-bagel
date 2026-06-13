package com.example.examplemod.datagen.items;

import com.example.examplemod.model.client.DynamicGeometryLoader;
import com.google.gson.JsonObject;
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class IngredientModelLoader extends CustomLoaderBuilder<ItemModelBuilder> {

    protected IngredientModelLoader(ItemModelBuilder parent, ExistingFileHelper existingFileHelper) {
        super(DynamicGeometryLoader.ID, parent, existingFileHelper, false);
    }

    @Override
    public JsonObject toJson(JsonObject json) {
        var obj = super.toJson(json);
        obj.add("variations", json.get("textures"));
        obj.remove("textures");
        return obj;
    }
}
