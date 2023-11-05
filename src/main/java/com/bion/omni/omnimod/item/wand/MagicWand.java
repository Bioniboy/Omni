package com.bion.omni.omnimod.item.wand;

import com.bion.omni.omnimod.element.Air;
import com.bion.omni.omnimod.element.Element;
import com.bion.omni.omnimod.element.Magic;
import net.minecraft.item.Item;

public class MagicWand extends Wand {
    private final Element element = new Magic();

    public MagicWand(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public int getItemNumber() {
        return 850011;
    }
}
