package com.bion.omni.omnimod.power.tech;

import com.bion.omni.omnimod.item.ModItems;

public class Wrench extends Augmentation{
    public Wrench(int level) {
        super(level, ModItems.WRENCH);
    }

    @Override
    public String getName() {
        return "Wrench";
    }

    @Override
    public String getId() {
        return "Wrench";
    }
}
