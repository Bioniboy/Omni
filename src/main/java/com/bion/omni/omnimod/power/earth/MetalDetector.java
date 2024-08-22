package com.bion.omni.omnimod.power.earth;

import com.bion.omni.omnimod.power.ImpulsePower;

public class MetalDetector extends ImpulsePower {
    public MetalDetector(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "Metal Detector";
    }

    @Override
    public String getId() {
        return "metalDetector";
    }

    @Override
    public String getAdvancementId() {
        return "metal_detector";
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
