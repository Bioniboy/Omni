package com.bion.omni.omnimod.power.storm;

import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.util.ConfigSymbol;

import java.util.ArrayList;

public class FallDamageResistance extends Power {

    public FallDamageResistance(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Resist Falling " + getRomanNumeral(getLevel());
    }

    @Override
    public String getId() {
        return "fallDamageResistance";
    }

    @Override
    public String getPreRequisiteId() {
        return "kineticResistance";
    }

    @Override
    public String getAdvancementId() {
        return getLevel() == 1 ? "fall_res" : "fall_res_2";
    }

    @Override
    public Boolean hasConfig() {
        return false;
    }

    @Override
    public Integer getMaxLevel() {
        return 2;
    }

    @Override
    public Integer getInfluenceCost() {
        return switch (getLevel()) {
            case 1:
                yield 50;
            case 2:
                yield 90;
            default:
                yield 0;
        };
    }

    @Override
    public ArrayList<ConfigSymbol> getConfigSymbols() {
        return null;
    }
}
