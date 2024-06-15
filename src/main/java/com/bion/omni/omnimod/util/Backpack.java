package com.bion.omni.omnimod.util;

import com.bion.omni.omnimod.power.Power;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.ArrayList;

public class Backpack {
    SimpleInventory backpackInventory = new SimpleInventory(999);
    public Backpack(int p_level) {
        level = p_level;
    }

    private Integer level = 1;
    public Integer getLevel() { return level; }
    public SimpleInventory get(){
        return backpackInventory;
    }

}

