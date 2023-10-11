package com.bion.omni.omnimod.element;

import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.power.clarity.AstralProject;
import com.bion.omni.omnimod.power.clarity.BlindnessImmunity;
import com.bion.omni.omnimod.power.clarity.BlockHistory;
import com.bion.omni.omnimod.power.fire.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;


public class Fire extends Element {
    public Fire() {
        powers = new ArrayList<>(Arrays.asList(
                new Ignite(1),
                new FireResistance(1),
                new FlameImmunity(1),
                new LavaStorage(1),
                new HotFeet(1)
        ));
    }

    @Override
    public String getName() {
        return "Fire";
    }

    @Override
    public Formatting getColor() {
        return Formatting.GOLD;
    }

    @Override
    public boolean isInDomain(ServerPlayerEntity player) {
        return testPredicate(player, "in_nether");
    }
}
