package com.bion.omni.omnimod.item.wand;

import com.bion.omni.omnimod.element.Element;
import com.bion.omni.omnimod.element.Moon;
import com.bion.omni.omnimod.element.Sun;
import net.minecraft.item.Item;

public class SunWand extends Wand {
    private final Element element = new Sun();

    public SunWand(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public int getItemNumber() {
        return 850008;
    }
}
