package com.bion.omni.omnimod.elements;

import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.powers.air.*;
import com.bion.omni.omnimod.powers.moon.*;
import net.minecraft.util.Formatting;


public class Air extends Element {
    @Override
    public Power getPower(String id, Integer level) {
        return switch (id) {
            case "kineticResistance":
                yield new KineticResistance(level);
            case "slowFall":
                yield new SlowFall();
            case "solidAir":
                yield new SolidAir(level);
            case "steadyFlying":
                yield new SteadyFlying(level);
            case "airWalk":
                yield new AirWalk();
            case "whirlwindBurst":
                yield new WhirlwindBurst();
            case "push":
                yield new Push(level);
            case "airShield":
                yield new AirShield(level);
            case "pacifism":
                yield new Pacifism(level);
            default:
                yield null;
        };
    }
    @Override
    public String[] getPowerIds() {
        return new String[] {
                "kineticResistance",
                "slowFall",
                "solidAir",
                "steadyFlying",
                "airWalk",
                "whirlwindBurst",
                "push",
                "airShield",
                "pacifism"
        };
    }
    @Override
    public Power getPower(String id) {
        return getPower(id, 1);
    }
    @Override
    public String getName() {
        return "Air";
    }

    @Override
    public Formatting getColor() {
        return Formatting.AQUA;
    }


}
