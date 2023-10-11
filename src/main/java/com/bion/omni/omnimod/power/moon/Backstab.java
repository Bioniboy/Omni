package com.bion.omni.omnimod.power.moon;

import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.util.ConfigSymbol;

import java.util.ArrayList;

public class Backstab extends Power {

    public Backstab(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Backstab " + getRomanNumeral(getLevel());
    }

    @Override
    public String getId() {
        return "backstab";
    }

    @Override
    public String getAdvancementId() {
        return "backstab" + (getLevel() == 1 ? "" : "_" + getLevel());
    }

    @Override
    public String getPreRequisiteId() {
        return "shadowSpeed";
    }

    @Override
    public Integer getMaxLevel() {
        return 3;
    }

    @Override
    public Boolean hasConfig() {
        return false;
    }

    @Override
    public Integer getInfluenceCost() {
        return switch (this.getLevel()) {
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
