package com.bion.omni.omnimod.power.sun;

import com.bion.omni.omnimod.power.ImpulsePower;

public class PulseNova extends ImpulsePower {
    public PulseNova(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "Pulse Nova";//normal writing
    }

    @Override
    public String getId() {
        return "pulseNova";//camel case
    }

    @Override
    public String getAdvancementId() {
        return "pulse_nova";//all lowercase with _
    }

    @Override
    public Integer getInfluenceCost() {
        return 10;
    }
    @Override
    public String getPreRequisiteId() {
        return "strength";
    }

    @Override
    public double getManaCost() {
        return 2;
    }
}
