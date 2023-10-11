package com.bion.omni.omnimod.item.wand;

import com.bion.omni.omnimod.element.Element;
import com.bion.omni.omnimod.element.Storm;
import net.minecraft.item.Item;

public class StormWand extends Wand {
    private final Element element = new Storm();

    public StormWand(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public int getItemNumber() {
        return 850003;
    }
}
