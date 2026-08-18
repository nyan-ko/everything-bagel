package com.nyan.everybagel;

import com.nyan.everybagel.gateau.powers.GateauPower;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.Map;

public class EffectDataMap {
    private final Map<DeferredHolder<MobEffect, ? extends GateauPower.Effect<?>>, GateauPower.Data> backing;

    public EffectDataMap() {
        this.backing = new HashMap<>();
    }

    public <D extends GateauPower.Data> void put(DeferredHolder<MobEffect, ? extends GateauPower.Effect<D>> effect, D data) {
        backing.put(effect, data);
    }

    @SuppressWarnings("unchecked")
    public <D extends GateauPower.Data> D get(DeferredHolder<MobEffect, ? extends GateauPower.Effect<D>> effect) {
        return (D) backing.get(effect);
    }

    public boolean empty() {
        return backing.isEmpty();
    }
}
