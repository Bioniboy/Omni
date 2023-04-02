package com.bion.omni.omnimod.powers.storm;

import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.util.ConfigSymbol;

import java.util.ArrayList;

public class KineticResistance extends Power {
    public KineticResistance(int level) {
        setLevel(level);
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
