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
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GateauMixLoader extends SimplePreparableReloadListener<Map<GateauSet, GateauSet>> {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final String FOLDER = "everybagel/gateau_mixes";
    private static final Logger LOGGER = LogManager.getLogger();

    public static final GateauMixLoader INSTANCE = new GateauMixLoader();

    private Map<GateauSet, GateauSet> mixes;

    private GateauMixLoader() {
        super();
        this.mixes = Map.of();
    }

    private static boolean validPath(ResourceLocation path) {
        return path.getPath().endsWith(".json");
    }

    @Override
    protected Map<GateauSet, GateauSet> prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        var map = new HashMap<GateauSet, GateauSet>();
        for (var entry : resourceManager.listResources(FOLDER, GateauMixLoader::validPath).entrySet()) {
            try (var reader = entry.getValue().openAsReader()) {
                var element = GSON.fromJson(reader, JsonElement.class);
                GateauMixes.CODEC.parse(JsonOps.INSTANCE, element)
                        .resultOrPartial(onError -> LOGGER.warn("Could not parse GateauMixes with json id {}, err {}", entry.getKey(), onError))
                        .ifPresent(map::putAll);
            }
            catch (IOException e) {
                LOGGER.warn("Could not parse GateauMixes with json id {}", entry.getKey(), e);
            }
        }
        return ImmutableMap.copyOf(map);
    }

    @Override
    protected void apply(Map<GateauSet, GateauSet> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        this.mixes = map;
    }

    public Map<GateauSet, GateauSet> getMixes() { return mixes; }
}
