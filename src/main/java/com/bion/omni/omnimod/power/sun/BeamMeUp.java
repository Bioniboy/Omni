package com.bion.omni.omnimod.power.sun;


import com.bion.omni.omnimod.power.ImpulsePower;

public class BeamMeUp extends ImpulsePower {
    public BeamMeUp(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "Beam Me Up";//normal writing
    }

    @Override
    public String getId() {
        return "beamMeUp";//camel case
    }

    @Override
    public String getAdvancementId() {
        return "beam_me_up";//all lowercase with _
    }

    @Override
    public Integer getInfluenceCost() {
        return 10;
    }
    @Override
    public String getPreRequisiteId() {
        return "strength";//all lowercase with _
    }

    @Override
    public double getManaCost() {
        return 2;
    }
}
