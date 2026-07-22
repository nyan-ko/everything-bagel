package com.nyan.everybagel.datagen.gateau;

import com.nyan.everybagel.gateau.Gateaux;
import com.nyan.everybagel.gateau.GateauDefaults;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class GateauByItemProvider extends DataMapProvider {

    public GateauByItemProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        var map = builder(Gateaux.GATEAU_BY_ITEM)
                .add(Tags.Items.INGOTS_COPPER, GateauDefaults.COPPER.getGateauKey(), false)
                .add(Tags.Items.INGOTS_IRON, GateauDefaults.IRON.getGateauKey(), false)
                .add(Tags.Items.INGOTS_GOLD, GateauDefaults.GOLD.getGateauKey(), false)
                .add(Tags.Items.INGOTS_NETHERITE, GateauDefaults.NETHERITE.getGateauKey(), false)

                .add(Tags.Items.RAW_MATERIALS_COPPER, GateauDefaults.COPPER.getGateauKey(0, 1), false)
                .add(Tags.Items.RAW_MATERIALS_IRON, GateauDefaults.IRON.getGateauKey(0, 1), false)
                .add(Tags.Items.RAW_MATERIALS_GOLD, GateauDefaults.GOLD.getGateauKey(0, 1), false)
                .add(Tags.Items.ORES_NETHERITE_SCRAP, GateauDefaults.NETHERITE.getGateauKey(0, 1), false)

                .add(Tags.Items.STONES, GateauDefaults.STONE.getGateauKey(),  false)
                .add(Tags.Items.GRAVELS, GateauDefaults.GRAVEL.getGateauKey(),  false);
//        var map = builder(Gateaux.GATEAU_BY_ITEM);
        for (GateauDefaults wg : GateauDefaults.values()) {
            map.add(wg.getTag(), wg.getGateauKey(), false);
        }
    }
}
