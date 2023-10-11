package com.bion.omni.omnimod.power.water;

import com.bion.omni.omnimod.power.ContinuousPower;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class DolphinsGrace extends ContinuousPower {
    public DolphinsGrace(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Dolphin's Grace";
    }

    @Override
    public String getId() {
        return "dolphinsGrace";
    }

    @Override
    public String getAdvancementId() {
        return "dolphins_grace";
    }

    @Override
    public boolean isActive(ServerPlayerEntity user) {
        return super.isActive(user) && user.isSwimming();
    }

    @Override
    public Integer getInfluenceCost() {
        return 80;
    }

    @Override
    public String getPreRequisiteId() {
        return "conduitPowerPlus";
    }

    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 10, 0));
    }
}
