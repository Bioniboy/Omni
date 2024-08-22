package com.bion.omni.omnimod.power.sun;

import com.bion.omni.omnimod.power.ImpulsePower;

public class Purify extends ImpulsePower {
    public Purify(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "Purify";
    }

    @Override
    public String getId() {
        return "purify";
    }

    @Override
    public String getAdvancementId() {
        return "purify";
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
