package com.bion.omni.omnimod.power.storm;

import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.util.ConfigSymbol;

import java.util.ArrayList;

public class ExplosionResistance extends Power {

    public ExplosionResistance(int level) {
        super(level);
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

    @Override
    public String getAdvancementId() {
        return switch(getLevel()) {
            case 1:
                yield "explosion_res";
            case 2:
                yield "explosion_res_2";
            case 3:
                yield "explosion_immunity";
            default:
                yield "";
        };
    }
}
