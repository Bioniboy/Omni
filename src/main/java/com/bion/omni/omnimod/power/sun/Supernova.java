package com.bion.omni.omnimod.power.sun;

import com.bion.omni.omnimod.power.ImpulsePower;

public class Supernova extends ImpulsePower {
    public Supernova(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "Supernova";//normal writing
    }

    @Override
    public String getId() {
        return "supernova";//camel case
    }

    @Override
    public String getAdvancementId() {
        return "supernova";//all lowercase with _
    }

    @Override
    public Integer getInfluenceCost() {
        return 10;
    }
    @Override
    public String getPreRequisiteId() {
        return "pulse_nova";//all lowercase with _
    }

    @Override
    public double getManaCost() {
        return 2;
    }
}
