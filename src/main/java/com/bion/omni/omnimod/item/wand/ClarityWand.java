package com.bion.omni.omnimod.item.wand;

import com.bion.omni.omnimod.elements.Clarity;
import com.bion.omni.omnimod.elements.Element;
import net.minecraft.item.Item;

public class ClarityWand extends Wand {
    private final Element element = new Clarity();

    public ClarityWand(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public int getItemNumber() {
        return 850004;
    }
}
