package com.bion.omni.omnimod.powers.storm;

import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.util.ConfigSymbol;

import java.util.ArrayList;

public class ExplosionResistance extends Power {
    public ExplosionResistance(int level) {
        setLevel(level);
    }
    @Override
    public String getName() {
        return getLevel() < 3 ? "Resist Explosions " + getRomanNumeral(getLevel()) : "Explosion Immunity";
    }

    @Override
    public String getId() {
        return "explosionResistance";
    }

    @Override
    public Boolean hasConfig() {
        return false;
    }

    @Override
    public Integer getMaxLevel() {
        return 3;
    }

    @Override
    public Integer getInfluenceCost() {
        return switch (getLevel()) {
            case 1:
                yield 50;
            case 2:
                yield 70;
            case 3:
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
