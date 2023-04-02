package com.bion.omni.omnimod.powers.moon;

import com.bion.omni.omnimod.powers.ContinuousPower;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class NightVision extends ContinuousPower {

    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 210, 0, true, false));
    }
    public void stop(ServerPlayerEntity user) {
        user.removeStatusEffect(StatusEffects.NIGHT_VISION);
    }
    @Override
    public String getPreRequisiteId() {
        return "invisibility";
    }
    @Override
    public String getName() {
        return "Night Vision";
    }
    @Override
    public String getId() {
        return "nightVision";
    }
    @Override
    public String getAdvancementId() {
        return "night_vision";
    }
    @Override
    public Boolean hasConfig() { return true; }
    @Override
    public Integer getInfluenceCost() {
        return 15;
    }
}
