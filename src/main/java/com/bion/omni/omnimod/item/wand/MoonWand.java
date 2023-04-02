package com.bion.omni.omnimod.item.wand;

import com.bion.omni.omnimod.elements.Element;
import com.bion.omni.omnimod.elements.Moon;
import net.minecraft.item.Item;

public class MoonWand extends Wand {
    private final Element element = new Moon();

    public MoonWand(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public int getItemNumber() {
        return 850007;
    }
}
