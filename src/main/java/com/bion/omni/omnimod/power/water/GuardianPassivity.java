package com.bion.omni.omnimod.power.water;

import com.bion.omni.omnimod.power.ContinuousPower;

public class GuardianPassivity extends ContinuousPower {
    public GuardianPassivity(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Guardian Passivity";
    }

    @Override
    public String getId() {
        return "guardianPassivity";
    }

    @Override
    public String getAdvancementId() {
        return "guardian_passivity";
    }

    @Override
    public Integer getInfluenceCost() {
        return 80;
    }
}
