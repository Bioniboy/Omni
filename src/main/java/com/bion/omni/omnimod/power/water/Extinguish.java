package com.bion.omni.omnimod.power.water;

import com.bion.omni.omnimod.power.ImpulsePower;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.server.network.ServerPlayerEntity;

public class Extinguish extends ImpulsePower {
    public Extinguish(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Extinguish";
    }

    @Override
    public String getId() {
        return "extinguish";
    }

    @Override
    public String getAdvancementId() {
        return "extinguish";
    }

    @Override
    public double getManaCost() {
        return 3;
    }

    @Override
    public Integer getInfluenceCost() {
        return 60;
    }

    @Override
    public String getPreRequisiteId() {
        return "waterStorage";
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        WaterStorage waterStorage = ((WaterStorage)((Apprentice)user).omni$getPowerById("waterStorage"));
        if (waterStorage.getWaterStored() >= 1 && user.isOnFire() && super.activate(user)) {
            user.extinguishWithSound();
            waterStorage.setWaterStored(waterStorage.getWaterStored() - 1);
            return true;
        }
        return false;
    }
}
