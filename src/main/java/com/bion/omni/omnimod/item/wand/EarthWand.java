package com.bion.omni.omnimod.item.wand;

import com.bion.omni.omnimod.element.Earth;
import com.bion.omni.omnimod.element.Element;
import com.bion.omni.omnimod.element.Moon;
import net.minecraft.item.Item;

public class EarthWand extends Wand {
    private final Element element = new Earth();

    public EarthWand(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public int getItemNumber() {
        return 850005;
    }
}
