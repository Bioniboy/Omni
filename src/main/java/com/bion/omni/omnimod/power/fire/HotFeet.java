package com.bion.omni.omnimod.power.fire;

import com.bion.omni.omnimod.power.ContinuousPower;

public class HotFeet extends ContinuousPower {
    public HotFeet(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Hot Feet";
    }

    @Override
    public String getId() {
        return "hotFeet";
    }

    @Override
    public String getAdvancementId() {
        return "hot_feet";
    }

    @Override
    public String getPreRequisiteId() {
        return "ignite";
    }

    @Override
    public Integer getInfluenceCost() {
        return 40;
    }
}
