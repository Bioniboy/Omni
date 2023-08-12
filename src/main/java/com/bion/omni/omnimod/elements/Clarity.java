package com.bion.omni.omnimod.elements;

import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.powers.clarity.AstralProject;
import net.minecraft.util.Formatting;

public class Clarity extends Element {
    @Override
    public Formatting getColor() {
        return Formatting.BLUE;
    }

    @Override
    public String getName() {
        return "Clarity";
    }

    @Override
    public Power getPower(String id, Integer level) {
        return switch (id) {
            case "astralProject":
                yield new AstralProject();
            default:
                yield null;
        };
    }

    @Override
    public Power getPower(String id) {
        return getPower(id, 1);
    }

    @Override
    public String[] getPowerIds() {
        return new String[] {
            "astralProject"
        };
    }
}
