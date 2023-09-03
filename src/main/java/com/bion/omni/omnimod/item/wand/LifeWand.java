package com.bion.omni.omnimod.item.wand;

import com.bion.omni.omnimod.elements.Air;
import com.bion.omni.omnimod.elements.Element;
import com.bion.omni.omnimod.elements.Life;
import net.minecraft.item.Item;

public class LifeWand extends Wand {
    private final Element element = new Life();

    public LifeWand(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public int getItemNumber() {
        return 850009;
    }
}
