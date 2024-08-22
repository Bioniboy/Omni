package com.bion.omni.omnimod.power.sun;


import com.bion.omni.omnimod.power.ContinuousPower;

public class TasteTheRainbow extends ContinuousPower {
    public TasteTheRainbow(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "Taste The Rainbow";//normal writing
    }

    @Override
    public String getId() {
        return "tasteTheRainbow";//camel case
    }

    @Override
    public String getAdvancementId() {
        return "taste_the_rainbow";//all lowercase with _
    }

    @Override
    public Integer getInfluenceCost() {
        return 10;
    }
    @Override
    public String getPreRequisiteId() {
        return "beam_me_up";//all lowercase with _
    }

    @Override
    public double getManaCost() {
        return 2;
    }
}
