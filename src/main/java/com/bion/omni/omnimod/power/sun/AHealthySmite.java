package com.bion.omni.omnimod.power.sun;

import com.bion.omni.omnimod.power.ContinuousPower;

public class AHealthySmite extends ContinuousPower {
    public AHealthySmite(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "A Healthy Smite";//normal writing
    }

    @Override
    public String getId() {
        return "aHealthySmite";//camel case
    }

    @Override
    public String getAdvancementId() {
        return "a_healthy_smite";//all lowercase with _
    }

    @Override
    public Integer getInfluenceCost() {
        return 10;
    }
    @Override
    public String getPreRequisiteId() {
        return "purify";
    }//all lowercase with _

    @Override
    public double getManaCost() {
        return 2;
    }
}
