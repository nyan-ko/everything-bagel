package com.nyan.everybagel.gateau.powers;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyan.everybagel.Effects;
import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.handlers.LivingDamageEventPostHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.extensions.IEntityExtension;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;

public class JaggedPower extends GateauPower implements LivingDamageEventPostHandler {
    private static final MapCodec<JaggedPower.Data> DATA_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.FLOAT.fieldOf("min_damage").forGetter(JaggedPower.Data::minDamage),
            Codec.FLOAT.fieldOf("max_damage").forGetter(JaggedPower.Data::maxDamage),
            Codec.FLOAT.fieldOf("max_threshold").forGetter(JaggedPower.Data::maxThreshold)
    ).apply(instance, JaggedPower.Data::new));

    public static final MapCodec<JaggedPower> CODEC = DATA_CODEC.xmap(JaggedPower::new, JaggedPower::getData);
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "jagged_power");
    public static final double HURT_RADIUS = 1.5;

    private final Data data;

    public JaggedPower(JaggedPower.Data data) {
        this.data = data;
    }

    public JaggedPower(float minDamage, float maxDamage, float maxThreshold) {
        this(new Data(minDamage, maxDamage, maxThreshold));
    }

    @Override
    public void onLivingDamage(LivingDamageEvent.Post event) {
        var victim = event.getEntity();
        var source = event.getSource();
        var curHealth = victim.getHealth();
        var maxHealth = victim.getMaxHealth();
        var percent = curHealth / maxHealth;
        if (!victim.hasEffect(Effects.JAGGED)) {
            return;
        }
        var map = victim.getData(Effects.GATEAU_POWER_EXTRA_DATA);
        if (map.empty()) {
            return;
        }
        var data = map.get(Effects.JAGGED);

        var missingPercent = 1 - Mth.clamp(percent, data.maxThreshold, 1);
        var damage = Mth.lerp(missingPercent, data.minDamage, data.maxDamage);
        try (var level = victim.level()) {
            List<LivingEntity> nearby = level.getEntitiesOfClass(
                    LivingEntity.class,
                    victim.getBoundingBox().inflate(HURT_RADIUS),
                    entity -> entity != victim && entity.isAlive() && entity.distanceToSqr(victim) <= HURT_RADIUS * HURT_RADIUS
            );

            for (LivingEntity entity : nearby) {
                entity.hurt(victim.damageSources().source(DamageTypes.CACTUS), damage);
            }
        }
        catch (Exception e) {
            LogUtils.getLogger().error("Jagged power error", e);
        }
    }

    public JaggedPower.Data getData() {
        return data;
    }

    @Override
    public ResourceLocation getBase() {
        return ID;
    }

    public static class Effect extends GateauPower.Effect<Data> {
        public Effect() {
            super(MobEffectCategory.BENEFICIAL, 0);
        }
    }

    public record Data(float minDamage, float maxDamage, float maxThreshold) implements GateauPower.Data {}
}