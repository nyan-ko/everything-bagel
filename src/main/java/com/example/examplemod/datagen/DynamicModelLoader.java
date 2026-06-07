package com.example.examplemod.datagen;

import com.example.examplemod.model.client.DynamicGeometryLoader;
import com.google.gson.JsonObject;
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class DynamicModelLoader extends CustomLoaderBuilder<ItemModelBuilder> {

    protected DynamicModelLoader(ItemModelBuilder parent, ExistingFileHelper existingFileHelper) {
        super(DynamicGeometryLoader.ID, parent, existingFileHelper, false);
    }

    @Override
    public JsonObject toJson(JsonObject json) {
        return super.toJson(json);
    }
}
