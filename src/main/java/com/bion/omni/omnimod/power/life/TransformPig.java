package com.bion.omni.omnimod.power.life;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

public class TransformPig extends TransformPower {
    public TransformPig(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Transform Pig";
    }

    @Override
    public String getId() {
        return "transformPig";
    }

    @Override
    public String getAdvancementId() {
        return "pig";
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
        return EntityType.PIG;
    }
}
