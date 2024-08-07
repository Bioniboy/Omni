package com.bion.omni.omnimod.power.moon;

import com.bion.omni.omnimod.power.ContinuousPower;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class ShadowSpeed extends ContinuousPower {
    public ShadowSpeed(int level) {
        super(level);
    }

    @Override
    public String getAdvancementId() {
        return "shadow_speed" + (getLevel() == 1 ? "" : "_" + getLevel());
    }
    @Override
    public Integer getMaxLevel() {
        return 2;
    }
    @Override
    public Integer getInfluenceCost() {
        return switch (this.getLevel()) {
            case 1:
                yield 40;
            case 2:
                yield 30;
            default:
                yield 0;
        };
    }


    @Override
    public String getName() {
        return "Shadow Speed " + getRomanNumeral(getLevel());
    }

    @Override
    public String getId() {
        return "shadowSpeed";
    }

    @Override
    public String getPreRequisiteId() {
        return "darkAura";
    }

    @Override
    public boolean isActive(ServerPlayerEntity user) {
        return super.isActive(user) && user.getWorld().getLightLevel(user.getBlockPos()) <= 4;
    }

    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        if (user.getWorld().getLightLevel(user.getBlockPos()) > 0) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 0, true, false));
        } else {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 1, true, false));
        }
    }
}
