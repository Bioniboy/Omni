package com.bion.omni.omnimod.power.earth;

import com.bion.omni.omnimod.power.ImpulsePower;

public class SummonClayGolem extends ImpulsePower {
    public SummonClayGolem(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "Summon Clay Golem";
    }

    @Override
    public String getId() {
        return "summonClayGolem";
    }

    @Override
    public String getAdvancementId() {
        return "summon_clay_golem";
    }

    @Override
    public Integer getInfluenceCost() {
        return 10;
    }
    @Override
    public String getPreRequisiteId() {
        return "metalDetector";
    }

    @Override
    public double getManaCost() {
        return 2;
    }
}
