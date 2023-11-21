package com.bion.omni.omnimod.power.tech;

import com.bion.omni.omnimod.power.ContinuousPower;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;

import java.util.*;

public abstract class Augmentation extends ContinuousPower {
    private ArrayList<Upgrade> upgrades = new ArrayList<>(List.of());
    public final Item ITEM;
    private int powerLevel = 0;
    public Upgrade getUpgrade(int index){
        return upgrades.get(index);
    }

    public void addUpgrade(Character character,String negativeSpace, List<Inventory> items, List<Integer> influence, int maxLevel){
        upgrades.add(new Upgrade(character, negativeSpace, items, influence, maxLevel));
    }
    public int getUpgradeCount(){
        return upgrades.size();
    }

    public Augmentation(int level, Item item) {
        super(level);
        ITEM = item;

    }
    public int getPowerLevel(){
        return this.powerLevel;
    }
    public void setPowerLevel(int level){
        this.powerLevel = level;
    }
}
