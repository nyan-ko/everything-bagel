package com.nyan.everybagel.datagen.gateau;

import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.gateau.Gateau;
import com.nyan.everybagel.gateau.Gateaux;
import com.nyan.everybagel.gateau.GateauDefaults;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class GateauProvider extends DatapackBuiltinEntriesProvider {
    public GateauProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, getRegistrySetBuilder(), Set.of(EverythingBagel.MOD_ID));
    }

    private static RegistrySetBuilder getRegistrySetBuilder() {
        return new RegistrySetBuilder()
                .add(Gateaux.GATEAU_REGISTRY_KEY, bootstrap -> {
                    for (GateauDefaults gateau : GateauDefaults.values()) {
                        bootstrap.register(gateau.getGateauKey().key(), new Gateau(gateau.name(), gateau.getLook(), gateau.getPowers()));
                    }
                });
    }

    @Override
    public String getName() {
        return EverythingBagel.MOD_ID + ": Gateaux";
    }
}
