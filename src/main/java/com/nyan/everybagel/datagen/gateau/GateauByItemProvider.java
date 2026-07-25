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
                .add(ItemTags.OAK_LOGS, GateauDefaults.WOOD.getGateauSet(), false)
                .add(ItemTags.SPRUCE_LOGS, GateauDefaults.WOOD.getGateauSet(), false)
                .add(ItemTags.BIRCH_LOGS, GateauDefaults.WOOD.getGateauSet(), false)
                .add(ItemTags.JUNGLE_LOGS, GateauDefaults.WOOD.getGateauSet(), false)
                .add(ItemTags.ACACIA_LOGS, GateauDefaults.WOOD.getGateauSet(), false)
                .add(ItemTags.DARK_OAK_LOGS, GateauDefaults.WOOD.getGateauSet(), false)
                .add(ItemTags.MANGROVE_LOGS, GateauDefaults.WOOD.getGateauSet(), false)
                .add(ItemTags.JUNGLE_LOGS, GateauDefaults.WOOD.getGateauSet(), false)

                .add(ItemTags.DIRT, GateauDefaults.DIRT.getGateauSet(), false)
                .add(Tags.Items.STONES, GateauDefaults.STONE.getGateauSet(),  false)
                .add(Tags.Items.GRAVELS, GateauDefaults.GRAVEL.getGateauSet(),  false)
                .add(Tags.Items.SANDS_COLORLESS, GateauDefaults.SAND.getGateauSet(), false)
                .add(Tags.Items.SANDS_RED,  GateauDefaults.SAND.getGateauSet(), false)

                .add(Items.FLINT.builtInRegistryHolder(), GateauDefaults.FLINT.getGateauSet(), false)

                .add(Items.BONE.builtInRegistryHolder(), GateauDefaults.BONE.getGateauSet(), false)
                .add(Items.BONE_MEAL.builtInRegistryHolder(), GateauDefaults.BONE.getGateauSet(),  false)
                .add(Items.BONE_BLOCK.builtInRegistryHolder(), GateauDefaults.BONE.getGateauSet(), false)

                .add(Items.SCULK.builtInRegistryHolder(), GateauDefaults.SCULK.getGateauSet(), false)
                .add(Items.SCULK_VEIN.builtInRegistryHolder(), GateauDefaults.SCULK.getGateauSet(), false)
                .add(Items.SCULK_CATALYST.builtInRegistryHolder(), GateauDefaults.SCULK.getGateauSet(), false)
                .add(Items.SCULK_SENSOR.builtInRegistryHolder(), GateauDefaults.SCULK.getGateauSet(), false)
                .add(Items.SCULK_SHRIEKER.builtInRegistryHolder(), GateauDefaults.SCULK.getGateauSet(), false)

                .add(Tags.Items.INGOTS_COPPER, GateauDefaults.COPPER.getGateauSet(), false)
                .add(Tags.Items.INGOTS_IRON, GateauDefaults.IRON.getGateauSet(), false)
                .add(Tags.Items.INGOTS_GOLD, GateauDefaults.GOLD.getGateauSet(), false)
                .add(Tags.Items.INGOTS_NETHERITE, GateauDefaults.NETHERITE.getGateauSet(), false)

                .add(Tags.Items.RAW_MATERIALS_COPPER, GateauDefaults.COPPER.getGateauSet(), false)
                .add(Tags.Items.RAW_MATERIALS_IRON, GateauDefaults.IRON.getGateauSet(), false)
                .add(Tags.Items.RAW_MATERIALS_GOLD, GateauDefaults.GOLD.getGateauSet(), false)
                .add(Tags.Items.ORES_NETHERITE_SCRAP, GateauDefaults.NETHERITE.getGateauSet(), false)

                .add(ItemTags.COALS, GateauDefaults.COAL.getGateauSet(), false)

                .add(Tags.Items.GEMS_AMETHYST, GateauDefaults.AMETHYST.getGateauSet(), false)

                .add(Tags.Items.DUSTS_REDSTONE, GateauDefaults.REDSTONE.getGateauSet(), false)

                .add(Tags.Items.DUSTS_GLOWSTONE, GateauDefaults.GLOWSTONE.getGateauSet(), false)

                .add(Tags.Items.GEMS_QUARTZ, GateauDefaults.NETHER_QUARTZ.getGateauSet(), false)

                .add(Tags.Items.GEMS_PRISMARINE, GateauDefaults.PRISMARINE.getGateauSet(), false)

                .add(Tags.Items.GEMS_LAPIS, GateauDefaults.LAPIS_LAZULI.getGateauSet(), false)

                .add(Tags.Items.GEMS_DIAMOND, GateauDefaults.DIAMOND.getGateauSet(), false)

                .add(Tags.Items.GEMS_EMERALD, GateauDefaults.EMERALD.getGateauSet(), false);

        for (GateauDefaults wg : GateauDefaults.values()) {
            map.add(wg.getTag(), wg.getGateauSet(), false);
        }
    }
}
