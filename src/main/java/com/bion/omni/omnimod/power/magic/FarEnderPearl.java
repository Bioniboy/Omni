package com.bion.omni.omnimod.power.magic;

import com.bion.omni.omnimod.power.ContinuousPower;
import net.minecraft.server.network.ServerPlayerEntity;

public class FarEnderPearl extends ContinuousPower {
    public FarEnderPearl(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Far Ender Pearl";
    }

    @Override
    public String getId() {
        return "farEnderPearl";
    }

    @Override
    public String getAdvancementId() {
        return "ep_yeet";
    }

    @Override
    public int getDefaultConfig() {
        return 1;
    }

    @Override
    public Integer getInfluenceCost() {
        return 40;
    }

    @Override
    public boolean isActive(ServerPlayerEntity user) {
        return super.isActive(user) && user.isSprinting();
    }

    @Override
    public String getPreRequisiteId() {
        return "enderPearlAffinity";
    }
}
