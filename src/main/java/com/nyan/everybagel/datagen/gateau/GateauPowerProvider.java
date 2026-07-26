package com.nyan.everybagel.datagen.gateau;

import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.gateau.powers.GateauPowers;
import com.nyan.everybagel.gateau.powers.JaggedPower;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class GateauPowerProvider extends DatapackBuiltinEntriesProvider {
    public GateauPowerProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, getRegistrySetBuilder(), Set.of(EverythingBagel.MOD_ID));
    }

    private static RegistrySetBuilder getRegistrySetBuilder() {
        return new RegistrySetBuilder()
                .add(GateauPowers.GATEAU_POWER_REGISTRY_KEY,bootstrap -> {
                    bootstrap.register(GateauPowers.JAGGED, new JaggedPower(0, 10, 20));
                });
    }

    @Override
    public String getName() {
        return EverythingBagel.MOD_ID + ": Gateau Powers";
    }
}
