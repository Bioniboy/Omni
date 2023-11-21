package com.bion.omni.omnimod.element;

import com.bion.omni.omnimod.item.tech.ItemCannon;
import com.bion.omni.omnimod.power.clarity.AstralProject;
import com.bion.omni.omnimod.power.clarity.BlindnessImmunity;
import com.bion.omni.omnimod.power.clarity.BlockHistory;
import com.bion.omni.omnimod.power.tech.DivineRepo;
import com.bion.omni.omnimod.power.tech.ItemCannonAugmentation;
import com.bion.omni.omnimod.power.tech.SpeedAugmentation;
import com.bion.omni.omnimod.power.tech.Wrench;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;

public class Tech extends Element {
    public Tech() {
        powers = new ArrayList<>(Arrays.asList(
                new DivineRepo(1),
                new SpeedAugmentation(1),
                new ItemCannonAugmentation(1),
                new Wrench(1)
        ));
    }

    @Override
    public Formatting getColor() {
        return Formatting.RED;
    }

    @Override
    public String getName() {
        return "Tech";
    }

    @Override
    public boolean isInDomain(ServerPlayerEntity player) {
        return false;
    }
}
