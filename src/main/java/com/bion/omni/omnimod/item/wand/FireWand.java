package com.bion.omni.omnimod.item.wand;

import com.bion.omni.omnimod.elements.Element;
import com.bion.omni.omnimod.elements.Fire;
import com.bion.omni.omnimod.elements.Storm;
import net.minecraft.item.Item;

public class FireWand extends Wand {
    private final Element element = new Fire();

    public FireWand(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public int getItemNumber() {
        return 850001;
    }
}
