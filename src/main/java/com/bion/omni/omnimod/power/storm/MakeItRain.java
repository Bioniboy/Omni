package com.bion.omni.omnimod.power.storm;

import com.bion.omni.omnimod.power.ImpulsePower;
import net.minecraft.server.network.ServerPlayerEntity;

public class MakeItRain extends ImpulsePower {
    public MakeItRain(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Make it Rain";
    }

    @Override
    public String getId() {
        return "makeItRain";
    }

    @Override
    public String getAdvancementId() {
        return "make_rain";
    }

    @Override
    public Integer getInfluenceCost() {
        return 30;
    }

    @Override
    public double getManaCost() {
        return 20;
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        if (super.activate(user)) {
            user.getServerWorld().setWeather(0, 0, true, user.getRandom().nextDouble() < 0.33);
            return true;
        } else {
            return false;
        }
    }
}
