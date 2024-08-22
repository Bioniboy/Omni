package com.bion.omni.omnimod.power.earth;

import com.bion.omni.omnimod.power.ImpulsePower;

public class SummonIronGolem extends ImpulsePower {
    public SummonIronGolem(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "Summon Iron Golem";
    }

    @Override
    public String getId() {
        return "summonIronGolem";
    }

    @Override
    public String getAdvancementId() {
        return "summon_iron_golem";
    }

    @Override
    public Integer getInfluenceCost() {
        return 10;
    }
    @Override
    public String getPreRequisiteId() {
        return "metal_detector";
    }

    @Override
    public double getManaCost() {
        return 2;
    }
}
