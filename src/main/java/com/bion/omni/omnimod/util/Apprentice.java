package com.bion.omni.omnimod.util;

import com.bion.omni.omnimod.elements.Element;
import com.bion.omni.omnimod.powers.ContinuousPower;
import com.bion.omni.omnimod.powers.ImpulsePower;
import com.bion.omni.omnimod.powers.Power;

import java.util.ArrayList;
import java.util.Hashtable;

public interface Apprentice {
    Hashtable<String, Double> getCosts();
    Element getElement();
    void setElement(Element element);
    void setElement(String elementId);
    ArrayList<ContinuousPower> getContinuousPowers();
    ArrayList<ImpulsePower> getImpulsePowers();
    ArrayList<Power> getOtherPowers();
    ArrayList<Power> getAllPowers();
    Power getPowerById(String id);
    double getMana();
    void setMana(double value);
    void changeMana(double value);
    Integer getManaMax();
    Integer getManaMaxLevel();
    void upgradeManaMax();
    void setManaMaxLevel(Integer value);
    double getManaRegen();
    Integer getManaRegenLevel();
    void upgradeManaRegen();
    void setManaRegenLevel(Integer value);
    Power addPower(Power power);
    Power addPower(String id);
    void buyPower(String id, Integer level);
    void clearPowers();
    Integer getInfluence();
    void changeInfluence(Integer amount);
    void setInfluence(Integer amount);
    void addConfig(String id, Integer value);
    void setConfig(String id, Integer value);
    void reorderConfig(String id, Integer value);
    Integer getConfigValue(String id);
    ArrayList<PowerConfig> getConfig();
    void interpretWandCommand(String[] components);
    void interpretWandCommand(String command);
    Integer getWandPage();
    void changeWandPage();
}
