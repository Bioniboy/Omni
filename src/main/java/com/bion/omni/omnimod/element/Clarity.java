package com.bion.omni.omnimod.element;

import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.power.clarity.AstralProject;
import com.bion.omni.omnimod.power.clarity.BlindnessImmunity;
import com.bion.omni.omnimod.power.clarity.BlockHistory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;

public class Clarity extends Element {
    public Clarity() {
        powers = new ArrayList<>(Arrays.asList(
                new AstralProject(1),
                new BlockHistory(1),
                new BlindnessImmunity(1)
        ));
    }

    @Override
    public Formatting getColor() {
        return Formatting.BLUE;
    }

    @Override
    public String getName() {
        return "Clarity";
    }

    @Override
    public boolean isInDomain(ServerPlayerEntity player) {
        return testPredicate(player, "has_sky_access") && testPredicate(player, "in_overworld") && (testPredicate(player, "holding_book_main") || testPredicate(player, "holding_book_off"));
    }
}
