package com.bion.omni.omnimod.power.storm;

import com.bion.omni.omnimod.power.ImpulsePower;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class GoBoom extends ImpulsePower {

    public GoBoom(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Go Boom";
    }

    @Override
    public String getId() {
        return "goBoom";
    }

    @Override
    public String getPreRequisiteId() {
        return "explosionResistance";
    }

    @Override
    public Integer getInfluenceCost() {
        return 40;
    }

    @Override
    public double getManaCost() {
        return 15;
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        if (super.activate(user)) {
            user.getWorld().createExplosion(user, user.getX(), user.getY(), user.getZ(), 2F, World.ExplosionSourceType.MOB);
            return true;
        } else {
            return false;
        }
    }
}
