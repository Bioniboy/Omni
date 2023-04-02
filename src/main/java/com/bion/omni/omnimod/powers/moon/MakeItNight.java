package com.bion.omni.omnimod.powers.moon;

import com.bion.omni.omnimod.powers.ImpulsePower;
import net.minecraft.server.network.ServerPlayerEntity;

public class MakeItNight extends ImpulsePower {
    @Override
    public String getName() {
        return "Make it Night";
    }

    @Override
    public String getId() {
        return "makeItNight";
    }
    @Override
    public String getAdvancementId() {
        return "make_night";
    }

    @Override
    public Boolean hasConfig() {
        return true;
    }

    @Override
    public String getPreRequisiteId() {
        return "nightVision";
    }

    @Override
    public double getManaCost() {
        return 30;
    }

    @Override
    public Integer getInfluenceCost() {
        return 30;
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        if (super.activate(user)) {
            user.getWorld().setTimeOfDay(13700);
            return true;
        }
        return false;
    }
}
