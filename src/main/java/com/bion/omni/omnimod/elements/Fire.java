package com.bion.omni.omnimod.elements;

import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.powers.air.*;
import com.bion.omni.omnimod.powers.fire.*;
import net.minecraft.util.Formatting;


public class Fire extends Element {
    @Override
    public Power getPower(String id, Integer level) {
        return switch (id) {
            case "ignite":
                yield new Ignite();
            case "fireResistance":
                yield new FireResistance();
            case "flameImmune":
                yield new FlameImmunity();
            case "lavaStorage":
                yield new LavaStorage(level);
            case "hotFeet":
                yield new HotFeet();
            default:
                yield null;
        };
    }
    @Override
    public String[] getPowerIds() {
        return new String[] {
                "ignite",
                "fireResistance",
                "flameImmune",
                "lavaStorage",
                "hotFeet"
        };
    }
    @Override
    public Power getPower(String id) {
        return getPower(id, 1);
    }
    @Override
    public String getName() {
        return "Fire";
    }

    @Override
    public Formatting getColor() {
        return Formatting.GOLD;
    }


}
