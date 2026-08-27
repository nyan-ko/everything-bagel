package com.nyan.everybagel.gateau.powers;

import com.mojang.serialization.MapCodec;
import com.nyan.everybagel.EverythingBagel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;

public class PlaceholderPower extends GateauPower {

    @Override
    public ResourceLocation getBase() {
        return ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "placeholder");
    }
}
