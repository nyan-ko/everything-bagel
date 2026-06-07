package com.example.examplemod.model.client;

import com.example.examplemod.ExampleMod;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;

public class DynamicGeometryLoader implements IGeometryLoader<DynamicGeometry> {
    public static final DynamicGeometryLoader INSTANCE = new DynamicGeometryLoader();
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "dynamic_geometry");

    @Override
    public DynamicGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        return null;
    }
}
