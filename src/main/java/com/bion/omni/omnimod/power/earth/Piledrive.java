package com.bion.omni.omnimod.power.earth;

import com.bion.omni.omnimod.power.ImpulsePower;

public class Piledrive extends ImpulsePower {
    public Piledrive(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "Piledrive";
    }

    @Override
    public String getId() {
        return "piledrive";
    }

    @Override
    public String getAdvancementId() {
        return "piledrive";
    }

    @Override
    public Integer getInfluenceCost() {
        return 10;
    }
    @Override
    public String getPreRequisiteId() {
        return "burrow";
    }

    @Override
    public double getManaCost() {
        return 2;
    }
}
