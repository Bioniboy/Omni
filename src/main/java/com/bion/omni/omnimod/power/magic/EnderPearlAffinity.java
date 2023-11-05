package com.bion.omni.omnimod.power.magic;

import com.bion.omni.omnimod.power.ContinuousPower;
import net.minecraft.server.network.ServerPlayerEntity;

public class EnderPearlAffinity extends ContinuousPower {
    public EnderPearlAffinity(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Pearl Affinity " + getRomanNumeral(getLevel());
    }

    @Override
    public Integer getMaxLevel() {
        return 2;
    }

    @Override
    public Integer getInfluenceCost() {
        return 30;
    }

    @Override
    public int getDefaultConfig() {
        return 1;
    }

    @Override
    public String getId() {
        return "enderPearlAffinity";
    }

    @Override
    public Boolean hasConfig() {
        return getLevel() >= 2;
    }

    @Override
    public double getManaCost() {
        return 5;
    }

    @Override
    public void use(ServerPlayerEntity user) {
    }

    @Override
    public String getAdvancementId() {
        return "ep_affinity" + (getLevel() == 2 ? "_2" : "");
    }
}
