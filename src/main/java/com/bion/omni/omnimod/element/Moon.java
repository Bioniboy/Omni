package com.bion.omni.omnimod.element;

import com.bion.omni.omnimod.power.moon.*;
import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.power.storm.MakeItRain;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;


public class Moon extends Element {
    public Moon() {
        powers = new ArrayList<>(Arrays.asList(
                new Invisibility(1),
                new NightVision(1),
                new BlindnessWave(1),
                new DarkAura(1),
                new ShadowPortal(1),
                new MakeItNight(1),
                new ShadowSpeed(1),
                new Backstab(1)
        ));
    }

    @Override
    public String getName() {
        return "Moon";
    }

    @Override
    public Formatting getColor() {
        return Formatting.DARK_BLUE;
    }

    @Override
    public boolean isInDomain(ServerPlayerEntity player) {
        return testPredicate(player, "in_dark");
    }
}
