package com.bion.omni.omnimod.power.life;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class TransformChicken extends TransformPower {
    public TransformChicken(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Transform Chicken";
    }

    @Override
    public String getId() {
        return "transformChicken";
    }

    @Override
    public String getAdvancementId() {
        return "chicken";
    }

    @Override
    public Integer getInfluenceCost() {
        return 30;
    }

    @Override
    public double getManaCost() {
        return 1;
    }

    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 10, 1, true, false));

    }

    @Override
    public EntityType<? extends LivingEntity> getDisguiseType() {
        return EntityType.CHICKEN;
    }

}
