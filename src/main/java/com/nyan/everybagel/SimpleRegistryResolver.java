package com.nyan.everybagel;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.util.thread.EffectiveSide;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class SimpleRegistryResolver implements RegistryResolver {

    public static final SimpleRegistryResolver INSTANCE = new SimpleRegistryResolver();

    private <T> Optional<Registry<T>> getRegistryAccess(ResourceKey<Registry<T>> registryKey) {
        var side = EffectiveSide.get();
        if (side == LogicalSide.CLIENT) {
            var instance = Minecraft.getInstance();
            if (instance == null) {
                return Optional.empty();
            }
            var connection = instance.getConnection();
            return connection != null ? connection.registryAccess().registry(registryKey) : Optional.empty();
        }
        else {
            var server = ServerLifecycleHooks.getCurrentServer();
            return server != null ? server.registryAccess().registry(registryKey) : Optional.empty();
        }
    }

    @Override
    public <T> Optional<T> resolve(ResourceKey<T> key, ResourceKey<Registry<T>> registryKey) {
        var match = getRegistryAccess(registryKey);
        return match.map(ts -> ts.get(key));
    }
}
