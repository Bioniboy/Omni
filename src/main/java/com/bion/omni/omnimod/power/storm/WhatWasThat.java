package com.bion.omni.omnimod.power.storm;

import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.util.ConfigSymbol;

import java.util.ArrayList;

public class WhatWasThat extends Power {
    public WhatWasThat(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "What Was That!?";
    }

    @Override
    public String getId() {
        return "whatWasThat";
    }

    @Override
    public String getPreRequisiteId() {
        return "mark";
    }

    @Override
    public String getAdvancementId() {
        return "play_sound";
    }

    @Override
    public Integer getInfluenceCost() {
        return 20;
    }

    @Override
    public Boolean hasConfig() {
        return false;
    }

    @Override
    public ArrayList<ConfigSymbol> getConfigSymbols() {
        return null;
    }
}
