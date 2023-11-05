package com.bion.omni.omnimod.element;

import com.bion.omni.omnimod.entity.custom.ManaBulletEntity;
import com.bion.omni.omnimod.power.magic.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;

public class Magic extends Element {
    public Magic() {
        powers = new ArrayList<>(Arrays.asList(
                new RemoteEnderChest(1),
                new EnderPearlAffinity(1),
                new PotionCrafting(1),
                new ShulkerBullet(1),
                new FarEnderPearl(1)
        ));
    }
    @Override
    public Formatting getColor() {
        return Formatting.DARK_PURPLE;
    }

    @Override
    public boolean isInDomain(ServerPlayerEntity player) {
        return testPredicate(player, "in_magic_biome");
    }

    @Override
    public String getName() {
        return "Magic";
    }
}
