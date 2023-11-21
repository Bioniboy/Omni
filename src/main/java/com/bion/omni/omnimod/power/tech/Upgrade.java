package com.bion.omni.omnimod.power.tech;

import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.List;

public class Upgrade {
    public final Character CHARACTER;
    private int level = 0;
    public final int MAX_LEVEL;
    public final List<Inventory> ITEM_COSTS;
    public final List<Integer> INFLUENCE_COSTS;
    public final String NEGATIVE_SPACE;
    public Upgrade(Character character, String negativeSpace, List<Inventory> items, List<Integer> influence, int maxLevel){
        this.CHARACTER = character;
        this.NEGATIVE_SPACE = negativeSpace;
        this.ITEM_COSTS = items;
        this.INFLUENCE_COSTS = influence;
        this.MAX_LEVEL = maxLevel;


    }
    public Upgrade(Character character, String negativeSpace, List<Inventory> items, List<Integer> influence, int level, int maxLevel){
        this.CHARACTER = character;
        this.NEGATIVE_SPACE = negativeSpace;
        this.ITEM_COSTS = items;
        this.INFLUENCE_COSTS = influence;
        this.level = level;
        this.MAX_LEVEL = maxLevel;
    }
    public int getLevel(){
        return level;
    }
    public void setLevel(int level){
        this.level = level;
    }
    public void incrementLevel(){
        this.level++;
    }
}
