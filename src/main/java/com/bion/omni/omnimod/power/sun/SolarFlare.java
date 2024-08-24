package com.bion.omni.omnimod.power.sun;

import com.bion.omni.omnimod.power.ImpulsePower;

public class SolarFlare extends ImpulsePower {
    public SolarFlare(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "Solar Flare";//normal writing
    }

    @Override
    public String getId() {
        return "solarFlare";//camel case
    }

    @Override
    public String getAdvancementId() {
        return "solar_flare";//all lowercase with _
    }

    @Override
    public Integer getInfluenceCost() {
        return 10;
    }
    @Override
    public String getPreRequisiteId() {
        return "strength";
    }

    @Override
    public double getManaCost() {
        return 2;
    }
}
