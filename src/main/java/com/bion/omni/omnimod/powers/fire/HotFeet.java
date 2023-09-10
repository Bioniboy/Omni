package com.bion.omni.omnimod.powers.fire;

import com.bion.omni.omnimod.powers.ContinuousPower;

public class HotFeet extends ContinuousPower {
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
