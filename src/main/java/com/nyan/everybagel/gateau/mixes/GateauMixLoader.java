package com.nyan.everybagel.gateau.mixes;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.nyan.everybagel.gateau.GateauSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class GateauMixLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final String folder = "everybagel/gateau_mixes";
    private static final Logger LOGGER = LogManager.getLogger();

    public static final GateauMixLoader INSTANCE = new GateauMixLoader();

    private Map<GateauSet, GateauSet> mixes;

    private GateauMixLoader() {
        super(GSON, folder);
        this.mixes = Map.of();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        HashMap<GateauSet, GateauSet> map = new HashMap<>();
        for (Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            ResourceLocation loc = entry.getKey();
            JsonElement json = entry.getValue();
            GateauMixes.CODEC.parse(JsonOps.INSTANCE, json)
                    .resultOrPartial(onError -> LOGGER.warn("Could not parse GateauMixes with json id {}, err {}", loc, onError))
                    .ifPresent(map::putAll);
        }
        this.mixes = map;
    }

    public Map<GateauSet, GateauSet> getMixes() { return mixes; }
}
