package com.bion.omni.omnimod.element;

import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.power.air.*;
import com.bion.omni.omnimod.power.earth.Accretion;
import com.bion.omni.omnimod.power.earth.Haste;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;


public class Earth extends Element {
    public Earth() {
        powers = new ArrayList<>(Arrays.asList(
                new Haste(1),
                new Accretion(1)
        ));
    }

    @Override
    public String getName() {
        return "Earth";
    }

    @Override
    public Formatting getColor() {
        return Formatting.GRAY;
    }

    @Override
    public boolean isInDomain(ServerPlayerEntity player) {
        return testPredicate(player, "is_below_40");
    }


}
