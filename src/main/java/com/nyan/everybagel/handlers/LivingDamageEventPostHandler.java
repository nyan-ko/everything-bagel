package com.nyan.everybagel.handlers;

import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public interface LivingDamageEventPostHandler {
    void onLivingDamage(LivingDamageEvent.Post event);
}
