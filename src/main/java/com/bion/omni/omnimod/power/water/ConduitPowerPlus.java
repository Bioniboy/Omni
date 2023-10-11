package com.bion.omni.omnimod.power.water;

import com.bion.omni.omnimod.power.ContinuousPower;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class ConduitPowerPlus extends ContinuousPower {
    public ConduitPowerPlus(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Conduit Power " + ("+".repeat(getLevel()));
    }

    @Override
    public Integer getMaxLevel() {
        return 2;
    }

    @Override
    public String getId() {
        return "conduitPowerPlus";
    }

    @Override
    public Integer getInfluenceCost() {
        return switch (getLevel()) {
            case 1:
                yield 50;
            case 2:
                yield 80;
            default:
                yield 0;
        };
    }

    @Override
    public boolean isActive(ServerPlayerEntity user) {
        return true;
    }

    @Override
    public Boolean hasConfig() {
        return false;
    }

    @Override
    public String getAdvancementId() {
        return "better_conduit" + (getLevel() != 1 ? "_" + getLevel() : "");
    }

    @Override
    public void use(ServerPlayerEntity user) {
        if (user.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {
            ((Apprentice)user).omni$getCosts().put(getId(), -1.0);
            if (getLevel() >= 2)
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 10, 0));
        }
    }
}
