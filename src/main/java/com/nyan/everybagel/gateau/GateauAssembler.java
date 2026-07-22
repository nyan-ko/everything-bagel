package com.nyan.everybagel.gateau;

import com.nyan.everybagel.ModComponents;
import com.nyan.everybagel.PowerSetIterator;
import com.nyan.everybagel.gateau.mixes.GateauMixes;
import com.nyan.everybagel.gateau.powers.GateauPower;
import com.nyan.everybagel.gateau.powers.GateauPowers;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GateauAssembler {
    public static GateauSet sumInputGateaux(IItemHandler inputs) {
        var result = GateauSet.of();
        for (int i = 0; i < inputs.getSlots(); i++) {
            var stack = inputs.getStackInSlot(i);
            Gateau.Key key;
            if (stack.has(ModComponents.GATEAU)) {
                result.addAll(stack.get(ModComponents.GATEAU));
            }
            else if ((key = stack.getItemHolder().getData(Gateaux.GATEAU_BY_ITEM)) != null) {
                result.add(key);
            }
        }
        return result;
    }

    public static GateauSet computeOutput(GateauSet input, Map<GateauSet, GateauSet> mixes) {
        var result = GateauSet.of();
        var iterator = new PowerSetIterator<>(input, GateauSet::of);
        while (iterator.hasNext()) {
            var set = (GateauSet) iterator.next();
            var mix = mixes.getOrDefault(set, GateauSet.EMPTY);
            if (mix != GateauSet.EMPTY) {
                result.addAll(mix);
            }
        }
        return result;
    }
}
