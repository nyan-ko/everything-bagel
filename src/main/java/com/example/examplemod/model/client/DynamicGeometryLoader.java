package com.example.examplemod.model.client;

import com.example.examplemod.ExampleMod;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;

import java.util.HashMap;
import java.util.Map;

public class DynamicGeometryLoader implements IGeometryLoader<DynamicGeometry> {
    public static final DynamicGeometryLoader INSTANCE = new DynamicGeometryLoader();
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "dynamic_geometry");

    @Override
    public DynamicGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        // hack for now
        var variations = jsonObject.getAsJsonObject("variations");
        Map<String, String> variationsMap = new HashMap<>();
        for (Map.Entry<String, JsonElement> variation : variations.entrySet()) {
            variationsMap.put(variation.getKey(), variation.getValue().getAsString());
        }
        return new DynamicGeometry(variationsMap);
    }
}
