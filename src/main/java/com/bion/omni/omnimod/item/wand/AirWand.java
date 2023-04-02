package com.bion.omni.omnimod.item.wand;

import com.bion.omni.omnimod.elements.Air;
import com.bion.omni.omnimod.elements.Element;
import net.minecraft.item.Item;

public class AirWand extends Wand {
    private final Element element = new Air();

    public AirWand(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public int getItemNumber() {
        return 850006;
    }
}
