package com.bion.omni.omnimod.powers.life;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class TransformFish extends TransformPower {
    @Override
    public String getName() {
        return "Transform Fish";
    }

    @Override
    public String getId() {
        return "transformFish";
    }

    @Override
    public String getAdvancementId() {
        return "fish";
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
        if (!user.isSubmergedInWater())
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 0, true, false));
        else
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 10, 0, true, false));
    }

    @Override
    public EntityType<? extends LivingEntity> getDisguiseType() {
        return EntityType.COD;
    }
}
