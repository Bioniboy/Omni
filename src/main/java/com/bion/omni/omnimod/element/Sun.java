package com.bion.omni.omnimod.element;

import com.bion.omni.omnimod.power.sun.Strength;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import java.util.ArrayList;
import java.util.Arrays;


public class Sun extends Element {
    public Sun() {
        powers = new ArrayList<>(Arrays.asList(
                new Strength(1)
        ));
    }

    @Override
    public String getName() {
        return "Sun";
    }

    @Override
    public Formatting getColor() {
        return Formatting.BLACK;
    }

    @Override
    public boolean isInDomain(ServerPlayerEntity player) {
        return testPredicate(player, "in_light");
    }


}
