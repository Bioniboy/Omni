package com.bion.omni.omnimod.item.wand;

import com.bion.omni.omnimod.element.Air;
import com.bion.omni.omnimod.element.Element;
import com.bion.omni.omnimod.element.Water;
import net.minecraft.item.Item;

public class WaterWand extends Wand {
    private final Element element = new Water();

    public WaterWand(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public int getItemNumber() {
        return 850002;
    }
}
