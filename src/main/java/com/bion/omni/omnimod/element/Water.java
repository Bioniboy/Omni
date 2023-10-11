package com.bion.omni.omnimod.element;

import com.bion.omni.omnimod.power.air.*;
import com.bion.omni.omnimod.power.air.WaterMovement;
import com.bion.omni.omnimod.power.water.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;


public class Water extends Element {
    public Water() {
        powers = new ArrayList<>(Arrays.asList(
                new WaterBreathing(1),
                new WaterStorage(1),
                new TridentDamage(1),
                new ConduitPowerPlus(1),
                new GuardianPassivity(1),
                new Extinguish(1),
                new DolphinsGrace(1),
                new WaterMovement(1)
        ));
    }

    @Override
    public String getName() {
        return "Water";
    }

    @Override
    public Formatting getColor() {
        return Formatting.DARK_AQUA;
    }

    @Override
    public boolean isInDomain(ServerPlayerEntity player) {
        return testPredicate(player, "in_ocean") && player.isSubmergedInWater();
    }


}
