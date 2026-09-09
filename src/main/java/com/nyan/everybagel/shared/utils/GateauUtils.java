package com.nyan.everybagel.shared.utils;

import com.nyan.everybagel.ModComponents;
import com.nyan.everybagel.gateau.GateauSet;
import com.nyan.everybagel.gateau.Gateaux;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GateauUtils {
    public static GateauSet getGateau(ItemStack input) {
        return getGateau(input, false);
    }

    public static GateauSet getGateau(ItemStack input, boolean stack) {
        if (input == null || input.isEmpty()) {
            return GateauSet.EMPTY;
        }

        if (input.has(ModComponents.GATEAU)) {
            var result = input.get(ModComponents.GATEAU);
            result.multiplyQuantity(input.getCount());
            return result;
        }

        var result = input.getItemHolder().getData(Gateaux.GATEAU_BY_ITEM);
        result.multiplyQuantity(input.getCount());
        return result;
    }
}
