package com.bion.omni.omnimod.power.earth;

import com.bion.omni.omnimod.power.ContinuousPower;

public class Unbreakable extends ContinuousPower {
    public Unbreakable(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "Unbreakable";
    }

    @Override
    public String getId() {
        return "unbreakable";
    }

    @Override
    public String getAdvancementId() {
        return "unbreakable";
    }

    @Override
    public Integer getInfluenceCost() {
        return 10;
    }
    @Override
    public String getPreRequisiteId() {
        return "summonIronGolem";
    }

    @Override
    public double getManaCost() {
        return 2;
    }
}
