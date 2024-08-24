package com.bion.omni.omnimod.power.sun;


import com.bion.omni.omnimod.power.ContinuousPower;

public class BeaconBoost extends ContinuousPower {
    public BeaconBoost(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "Beacon Boost";//normal writing
    }

    @Override
    public String getId() {
        return "beaconBoost";//camel case
    }

    @Override
    public String getAdvancementId() {
        return "beacon_boost";//all lowercase with _
    }

    @Override
    public Integer getInfluenceCost() {
        return 10;
    }
    @Override
    public String getPreRequisiteId() {
        return "beamMeUp";
    }

    @Override
    public double getManaCost() {
        return 2;
    }
}
