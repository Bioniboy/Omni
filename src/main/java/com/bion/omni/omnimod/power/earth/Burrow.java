package com.bion.omni.omnimod.power.earth;

import com.bion.omni.omnimod.power.ImpulsePower;

public class Burrow extends ImpulsePower {
    public Burrow(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "Burrow";
    }

    @Override
    public String getId() {
        return "burrow";
    }

    @Override
    public String getAdvancementId() {
        return "burrow";
    }

    @Override
    public Integer getInfluenceCost() {
        return 10;
    }
    @Override
    public String getPreRequisiteId() {
        return "haste";
    }

    @Override
    public double getManaCost() {
        return 2;
    }
}
