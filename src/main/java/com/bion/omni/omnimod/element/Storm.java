package com.bion.omni.omnimod.element;

import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.power.storm.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Storm extends Element {
    public Storm() {
        powers = new ArrayList<>(Arrays.asList(
                new Speed(1),
                new ExplosionResistance(1),
                new CreeperDisguise(1),
                new GoBoom(1),
                new FallDamageResistance(1),
                new SelfDestruct(1),
                new MakeItRain(1),
                new ManaRocket(1),
                new Yeet(1),
                new SpinMe(1),
                new WhatWasThat(1),
                new HammerTime(1)
        ));
    }

    @Override
    public String getName() {
        return "Storm";
    }

    @Override
    public Formatting getColor() {
        return Formatting.DARK_GRAY;
    }

    @Override
    public boolean isInDomain(ServerPlayerEntity player) {
        return testPredicate(player, "is_above_150") || testPredicate(player, "is_raining");
    }
}
