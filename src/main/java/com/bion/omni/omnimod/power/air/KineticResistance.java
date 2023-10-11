package com.bion.omni.omnimod.power.air;

import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.util.ConfigSymbol;

import java.util.ArrayList;

public class KineticResistance extends Power {

    public KineticResistance(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return getLevel() == 1 ? "Kinetic Resistance" : "Kinetic Immunity";
    }

    @Override
    public String getId() {
        return "kineticResistance";
    }
    @Override
    public String getAdvancementId() {
        return getLevel() == 1 ? "kinetic_res" : "kinetic_immunity";
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
                yield 40;
            case 2:
                yield 80;
            default:
                yield 0;
        };
    }

    @Override
    public ArrayList<ConfigSymbol> getConfigSymbols() {
        return null;
    }
}
