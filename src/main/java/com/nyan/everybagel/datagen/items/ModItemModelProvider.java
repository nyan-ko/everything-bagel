package com.nyan.everybagel.datagen.items;

import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.items.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EverythingBagel.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
//        basicItem(ModItems.DOUGH.get());

//        var loader = getBuilder("").customLoader(DynamicModelLoader::new);

    }
}
