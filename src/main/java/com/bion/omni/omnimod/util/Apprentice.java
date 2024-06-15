package com.bion.omni.omnimod.util;

import com.bion.omni.omnimod.element.Element;
import com.bion.omni.omnimod.entity.custom.Pet;
import com.bion.omni.omnimod.power.ContinuousPower;
import com.bion.omni.omnimod.power.ImpulsePower;
import com.bion.omni.omnimod.power.Power;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public interface Apprentice {
    void omni$setInMansion(boolean inMansion);
    boolean omni$getInMansion();
    void omni$setInOpMode(boolean inOpMode);
    boolean omni$inOpMode();
    void omni$setOpModeOtherPos(Vec3d pos, World world);
    Vec3d omni$getOpModeOtherPos();
    World omni$getOpModeOtherWorld();
    void omni$addSavedInventory(String id, PlayerInventory inventory);
    PlayerInventory omni$removeSavedInventory(String id);
    PlayerInventory omni$getSavedInventory(String id);
    Set<String> omni$getInventoryKeys();
    void omni$setHome(Vec3d pos, World world);
    Vec3d omni$getHomePos();
    World omni$getHomeWorld();
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
    void omni$setPet(Pet entity);
    Pet omni$getPet();
    void omni$setPetCooldown(int cooldown);
    int omni$getPetCooldown();
    CustomChar omni$getChar(CustomCharDict.CharName p_name);



}
