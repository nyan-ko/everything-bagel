package com.nyan.everybagel.datagen.gateau;

import com.nyan.everybagel.gateau.Gateaux;
import com.nyan.everybagel.gateau.GateauDefaults;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class GateauByItemProvider extends DataMapProvider {

    public GateauByItemProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        var map = builder(Gateaux.GATEAU_BY_ITEM);
        for (GateauDefaults wg : GateauDefaults.values()) {
            map.add(wg.getTag(), wg.getGateau(), false);
        }
    }
}
