package com.bion.omni.omnimod.elements;

import com.bion.omni.omnimod.powers.moon.*;
import com.bion.omni.omnimod.powers.Power;
import net.minecraft.util.Formatting;


public class Moon extends Element {
    @Override
    public Power getPower(String id, Integer level) {
        return switch (id) {
            case "invisibility":
                yield new Invisibility();
            case "nightVision":
                yield new NightVision();
            case "blindnessWave":
                yield new BlindnessWave(level);
            case "darkAura":
                yield new DarkAura(level);
            case "shadowPortal":
                yield new ShadowPortal(level);
            case "makeItNight":
                yield new MakeItNight();
            case "shadowSpeed":
                yield new ShadowSpeed();
            case "backstab":
                yield new Backstab();
            default:
                yield null;
        };
    }
    @Override
    public String[] getPowerIds() {
        return new String[] {
                "invisibility",
                "nightVision",
                "blindnessWave",
                "darkAura",
                "shadowPortal",
                "makeItNight",
                "shadowSpeed",
                "backstab"
        };
    }
    @Override
    public Power getPower(String id) {
        return getPower(id, 1);
    }
    @Override
    public String getName() {
        return "Moon";
    }

    @Override
    public Formatting getColor() {
        return Formatting.DARK_BLUE;
    }


}
