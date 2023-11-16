package com.bion.omni.omnimod.power.tech;

import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.util.ConfigSymbol;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.ArrayList;

public class DivineRepo extends Power {
    SimpleInventory divRepo = new SimpleInventory(999);
    public DivineRepo(int level) {
        super(level);
    }
    public SimpleInventory get(){
        return divRepo;
    }

    @Override
    public String getName() {
        return "Divine Repository";
    }

    @Override
    public String getId() {
        return "divineRepo";
    }

    @Override
    public ArrayList<ConfigSymbol> getConfigSymbols() {
        return null;
    }

    @Override
    public NbtCompound toNbt() {
        NbtCompound nbt = super.toNbt();
        nbt.put("DivineRepo", divRepo.toNbtList());
        return nbt;
    }

    @Override
    public Power setNbt(NbtCompound nbt) {
        divRepo.readNbtList(nbt.getList("DivineRepo", NbtElement.COMPOUND_TYPE));
        return this;
    }
}
