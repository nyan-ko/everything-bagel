package com.nyan.everybagel.datagen.gateau;

import com.nyan.everybagel.gateau.Gateaux;
import com.nyan.everybagel.gateau.GateauDefaults;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
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
                .add(ItemTags.OAK_LOGS, GateauDefaults.WOOD.getGateauKey(), false)
                .add(ItemTags.SPRUCE_LOGS, GateauDefaults.WOOD.getGateauKey(), false)
                .add(ItemTags.BIRCH_LOGS, GateauDefaults.WOOD.getGateauKey(), false)
                .add(ItemTags.JUNGLE_LOGS, GateauDefaults.WOOD.getGateauKey(), false)
                .add(ItemTags.ACACIA_LOGS, GateauDefaults.WOOD.getGateauKey(), false)
                .add(ItemTags.DARK_OAK_LOGS, GateauDefaults.WOOD.getGateauKey(), false)
                .add(ItemTags.MANGROVE_LOGS, GateauDefaults.WOOD.getGateauKey(), false)
                .add(ItemTags.JUNGLE_LOGS, GateauDefaults.WOOD.getGateauKey(), false)

                .add(ItemTags.DIRT, GateauDefaults.DIRT.getGateauKey(), false)
                .add(Tags.Items.STONES, GateauDefaults.STONE.getGateauKey(),  false)
                .add(Tags.Items.GRAVELS, GateauDefaults.GRAVEL.getGateauKey(),  false)
                .add(Tags.Items.SANDS_COLORLESS, GateauDefaults.SAND.getGateauKey(), false)
                .add(Tags.Items.SANDS_RED,  GateauDefaults.SAND.getGateauKey(), false)

                .add(Items.FLINT.builtInRegistryHolder(), GateauDefaults.FLINT.getGateauKey(), false)

                .add(Items.BONE.builtInRegistryHolder(), GateauDefaults.BONE.getGateauKey(), false)
                .add(Items.BONE_MEAL.builtInRegistryHolder(), GateauDefaults.BONE.getGateauKey(),  false)
                .add(Items.BONE_BLOCK.builtInRegistryHolder(), GateauDefaults.BONE.getGateauKey(), false)

                .add(Items.SCULK.builtInRegistryHolder(), GateauDefaults.SCULK.getGateauKey(), false)
                .add(Items.SCULK_VEIN.builtInRegistryHolder(), GateauDefaults.SCULK.getGateauKey(), false)
                .add(Items.SCULK_CATALYST.builtInRegistryHolder(), GateauDefaults.SCULK.getGateauKey(), false)
                .add(Items.SCULK_SENSOR.builtInRegistryHolder(), GateauDefaults.SCULK.getGateauKey(), false)
                .add(Items.SCULK_SHRIEKER.builtInRegistryHolder(), GateauDefaults.SCULK.getGateauKey(), false)

                .add(Tags.Items.INGOTS_COPPER, GateauDefaults.COPPER.getGateauKey(), false)
                .add(Tags.Items.INGOTS_IRON, GateauDefaults.IRON.getGateauKey(), false)
                .add(Tags.Items.INGOTS_GOLD, GateauDefaults.GOLD.getGateauKey(), false)
                .add(Tags.Items.INGOTS_NETHERITE, GateauDefaults.NETHERITE.getGateauKey(), false)

                .add(Tags.Items.RAW_MATERIALS_COPPER, GateauDefaults.COPPER.getGateauKey(0, 1), false)
                .add(Tags.Items.RAW_MATERIALS_IRON, GateauDefaults.IRON.getGateauKey(0, 1), false)
                .add(Tags.Items.RAW_MATERIALS_GOLD, GateauDefaults.GOLD.getGateauKey(0, 1), false)
                .add(Tags.Items.ORES_NETHERITE_SCRAP, GateauDefaults.NETHERITE.getGateauKey(0, 1), false)

                .add(ItemTags.COALS, GateauDefaults.COAL.getGateauKey(), false)

                .add(Tags.Items.GEMS_AMETHYST, GateauDefaults.AMETHYST.getGateauKey(), false)

                .add(Tags.Items.DUSTS_REDSTONE, GateauDefaults.REDSTONE.getGateauKey(), false)

                .add(Tags.Items.DUSTS_GLOWSTONE, GateauDefaults.GLOWSTONE.getGateauKey(), false)

                .add(Tags.Items.GEMS_QUARTZ, GateauDefaults.NETHER_QUARTZ.getGateauKey(), false)

                .add(Tags.Items.GEMS_PRISMARINE, GateauDefaults.PRISMARINE.getGateauKey(), false)

                .add(Tags.Items.GEMS_LAPIS, GateauDefaults.LAPIS_LAZULI.getGateauKey(), false)

                .add(Tags.Items.GEMS_DIAMOND, GateauDefaults.DIAMOND.getGateauKey(), false)

                .add(Tags.Items.GEMS_EMERALD, GateauDefaults.EMERALD.getGateauKey(), false);

        for (GateauDefaults wg : GateauDefaults.values()) {
            map.add(wg.getTag(), wg.getGateauKey(), false);
        }
    }
}
