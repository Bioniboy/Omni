package com.bion.omni.omnimod.element;

import com.bion.omni.omnimod.power.sun.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import java.util.ArrayList;
import java.util.Arrays;


public class Sun extends Element {
    public Sun() {
        powers = new ArrayList<>(Arrays.asList(
                new Strength(1),
                new AHealthySmite(1),
                new BeaconBoost(1),
                new BeamMeUp(1),
                new PulseNova(1),
                new Purify(1),
                new SolarFlare(1),
                new SunRay(1),
                new Supernova(1),
                new TasteTheRainbow(1)
        ));
    }

    @Override
    public String getName() {
        return "Sun";
    }

    @Override
    public Formatting getColor() {
        return Formatting.BLACK;
    }//currently black cause yellow is hard to see on the white paper

    @Override
    public boolean isInDomain(ServerPlayerEntity player) {
        return testPredicate(player, "in_light");
    }


}
