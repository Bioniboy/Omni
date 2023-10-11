package com.bion.omni.omnimod.power.clarity;

import com.bion.omni.omnimod.power.ContinuousPower;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class BlindnessImmunity extends ContinuousPower {
    public BlindnessImmunity(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Blindness Immunity";
    }

    @Override
    public String getId() {
        return "blindnessImmunity";
    }

    @Override
    public Integer getInfluenceCost() {
        return 40;
    }

    @Override
    public String getAdvancementId() {
        return "blind_immune";
    }

    @Override
    public void use(ServerPlayerEntity user) {
        user.removeStatusEffect(StatusEffects.BLINDNESS);
    }
}
