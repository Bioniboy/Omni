package com.bion.omni.omnimod.util;

import com.bion.omni.omnimod.elements.Element;
import com.bion.omni.omnimod.powers.ContinuousPower;
import com.bion.omni.omnimod.powers.ImpulsePower;
import com.bion.omni.omnimod.powers.Power;

import java.util.ArrayList;
import java.util.Hashtable;

public interface Apprentice {
    Hashtable<String, Double> omni$getCosts();
    Element omni$getElement();
    void omni$setElement(Element element);
    void omni$setElement(String elementId);
    ArrayList<ContinuousPower> omni$getContinuousPowers();
    ArrayList<ImpulsePower> omni$getImpulsePowers();
    ArrayList<Power> omni$getOtherPowers();
    ArrayList<Power> omni$getAllPowers();
    Power omni$getPowerById(String id);
    double omni$getMana();
    void omni$setMana(double value);
    void omni$changeMana(double value);
    Integer omni$getManaMax();
    Integer omni$getManaMaxLevel();
    void omni$upgradeManaMax();
    void omni$setManaMaxLevel(Integer value);
    double omni$getManaRegen();
    Integer omni$getManaRegenLevel();
    void omni$upgradeManaRegen();
    void omni$setManaRegenLevel(Integer value);
    Power omni$addPower(Power power);
    Power omni$addPower(String id);
    void omni$buyPower(String id, Integer level);
    void omni$clearPowers();
    Integer omni$getInfluence();
    void omni$changeInfluence(Integer amount);
    void omni$setInfluence(Integer amount);
    void omni$addConfig(String id, Integer value);
    void omni$setConfig(String id, Integer value);
    void omni$reorderConfig(String id, Integer value);
    Integer omni$getConfigValue(String id);
    ArrayList<PowerConfig> omni$getConfig();
    void omni$interpretWandCommand(String[] components);
    void omni$interpretWandCommand(String command);
    Integer omni$getWandPage();
    void omni$changeWandPage();
}
