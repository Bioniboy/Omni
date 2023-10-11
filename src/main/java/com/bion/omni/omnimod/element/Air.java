package com.bion.omni.omnimod.element;

import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.power.air.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;


public class Air extends Element {
    public Air() {
        powers = new ArrayList<>(Arrays.asList(
                new KineticResistance(1),
                new SlowFall(1),
                new SolidAir(1),
                new SteadyFlying(1),
                new AirWalk(1),
                new WhirlwindBurst(1),
                new Push(1),
                new AirShield(1),
                new Pacifism(1)
        ));
    }

    @Override
    public String getName() {
        return "Air";
    }

    @Override
    public Formatting getColor() {
        return Formatting.AQUA;
    }

    @Override
    public boolean isInDomain(ServerPlayerEntity player) {
        return testPredicate(player, "is_above_100");
    }


}
