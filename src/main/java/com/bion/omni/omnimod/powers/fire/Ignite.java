package com.bion.omni.omnimod.powers.fire;

import com.bion.omni.omnimod.mixin.accessor.EntityAccessor;
import com.bion.omni.omnimod.powers.ContinuousPower;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class Ignite extends ContinuousPower {
    private int tickCounter = 0;
    @Override
    public String getName() {
        return "Ignite";
    }

    @Override
    public String getId() {
        return "ignite";
    }

    @Override
    public Integer getInfluenceCost() {
        return 25;
    }

    @Override
    public double getManaCost() {
        return 2;
    }

    @Override
    public String getAdvancementId() {
        return "ignite";
    }

    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 10, 1));
        if (((Apprentice)user).omni$getConfigValue("hotFeet") == 1)
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 1));
        ((EntityAccessor)user).setHasVisualFire(true);
        if (tickCounter % 40 == 0) {
            user.damage(user.getDamageSources().magic(), 1.0f);
        }
        tickCounter++;
        if (user.isWet()) {
            ((Apprentice)user).omni$setConfig(getId(), 0);
        }
    }

    @Override
    public void stop(ServerPlayerEntity user) {
        super.stop(user);
        ((EntityAccessor)user).setHasVisualFire(false);
    }
}
