package com.nyan.everybagel;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Optional;

public interface RegistryResolver {
    <T> Optional<T> resolve(ResourceKey<T> key, ResourceKey<Registry<T>> registryKey);
}
