package com.nyan.everybagel.gateau;

import com.nyan.everybagel.ModComponents;
import com.nyan.everybagel.PowerSetIterator;
import com.nyan.everybagel.gateau.mixes.GateauMix;
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
    public static GateauSet computeCombinations(GateauSet input) {
        if (GateauMixes.queryCache(input) != GateauSet.EMPTY) {
            return GateauMixes.queryCache(input);
        }
        var result = GateauSet.of();
        var iterator = new PowerSetIterator<>(input, GateauSet::of);
        while (iterator.hasNext()) {
            var set = (GateauSet) iterator.next();
            var mix = GateauMixes.queryMixes(set);
            if (mix != GateauSet.EMPTY) {
                result.addAll(mix);
            }
        }
        GateauMixes.cacheMix(input, result);
        return result;
    }

    public static GateauSet sumInputGateaux(IItemHandler inputs) {
        var result = GateauSet.of();
        for (int i = 0; i < inputs.getSlots(); i++) {
            var stack = inputs.getStackInSlot(i);
            result.addAll(stack.getOrDefault(ModComponents.GATEAU, GateauSet.EMPTY));
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
