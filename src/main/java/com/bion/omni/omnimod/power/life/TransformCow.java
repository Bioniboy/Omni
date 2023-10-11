package com.bion.omni.omnimod.power.life;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class TransformCow extends TransformPower {
    public TransformCow(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Transform Cow";
    }

    @Override
    public String getId() {
        return "transformCow";
    }

    @Override
    public String getAdvancementId() {
        return "cow";
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
    public EntityType<? extends LivingEntity> getDisguiseType() {
        return EntityType.COW;
    }

    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        user.clearStatusEffects();
    }
}
