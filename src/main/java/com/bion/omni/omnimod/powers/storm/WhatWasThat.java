package com.bion.omni.omnimod.powers.storm;

import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.util.ConfigSymbol;

import java.util.ArrayList;

public class WhatWasThat extends Power {
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
    public Integer getInfluenceCost() {
        return 30;
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
