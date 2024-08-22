package com.bion.omni.omnimod.power.sun;

import com.bion.omni.omnimod.power.ImpulsePower;

public class SunRay extends ImpulsePower {
    public SunRay(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "Sun Ray";//normal writing
    }

    @Override
    public String getId() {
        return "sunRay";//camel case
    }

    @Override
    public String getAdvancementId() {
        return "sun_ray";//all lowercase with _
    }

    @Override
    public Integer getInfluenceCost() {
        return 10;
    }
    @Override
    public String getPreRequisiteId() {
        return "solar_flare";//all lowercase with _
    }

    @Override
    public double getManaCost() {
        return 2;
    }
}
