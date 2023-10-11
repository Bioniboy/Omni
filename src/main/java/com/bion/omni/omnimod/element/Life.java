package com.bion.omni.omnimod.element;

import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.power.clarity.AstralProject;
import com.bion.omni.omnimod.power.clarity.BlindnessImmunity;
import com.bion.omni.omnimod.power.clarity.BlockHistory;
import com.bion.omni.omnimod.power.life.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;


public class Life extends Element {
    public Life() {
        powers = new ArrayList<>(Arrays.asList(
                new Photosynthesis(1),
                new TransformOcelot(1),
                new TransformChicken(1),
                new TransformSheep(1),
                new TransformCow(1),
                new TransformPig(1),
                new TransformFish(1)
        ));
    }

    @Override
    public String getName() {
        return "Life";
    }

    @Override
    public Formatting getColor() {
        return Formatting.DARK_GREEN;
    }

    @Override
    public boolean isInDomain(ServerPlayerEntity player) {
        return testPredicate(player, "in_forest");
    }
}
