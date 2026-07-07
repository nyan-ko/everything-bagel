package com.nyan.everybagel.datagen;

import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.gateau.Gateaux;
import com.nyan.everybagel.gateau.powers.GateauPower;
import com.nyan.everybagel.gateau.powers.GateauPowers;
import com.nyan.everybagel.gateau.powers.JaggedPower;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class GateauPowerProvider extends DatapackBuiltinEntriesProvider {
    public GateauPowerProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, getRegistrySetBuilder(), Set.of(EverythingBagel.MOD_ID));
    }

    private static RegistrySetBuilder getRegistrySetBuilder() {
        return new RegistrySetBuilder()
                .add(GateauPowers.GATEAU_POWER_REGISTRY_KEY,bootstrap -> {
                    bootstrap.register(GateauPowers.JAGGED_WEAK, new JaggedPower(1.0f, 10.0f, 2.0f));
                    bootstrap.register(GateauPowers.JAGGED_MEDIUM, new JaggedPower(2.0f, 15.0f,  4.0f));
                    bootstrap.register(GateauPowers.JAGGED_STRONG, new JaggedPower(3.0f, 20.0f,  6.0f));

                });
    }

    @Override
    public String getName() {
        return "Gateau Powers: " + EverythingBagel.MOD_ID;
    }
}
