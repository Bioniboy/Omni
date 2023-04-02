package com.bion.omni.omnimod.powers.storm;

import com.bion.omni.omnimod.powers.ImpulsePower;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class ManaRocket extends ImpulsePower {
    @Override
    public String getName() {
        return "Mana Rocket";
    }

    @Override
    public String getId() {
        return "manaRocket";
    }

    @Override
    public String getPreRequisiteId() {
        return "makeItRain";
    }

    @Override
    public Integer getInfluenceCost() {
        return 45;
    }

    @Override
    public double getManaCost() {
        return 3;
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        if (user.isFallFlying() && (user.getWorld().isRaining() || super.activate(user))) {
            Vec3d vec3d = user.getRotationVector();
            Vec3d vec3d2 = user.getVelocity();
            user.addVelocity(vec3d.x * 1 + (vec3d.x * 1.5 - vec3d2.x) * 0.5, vec3d.y * 1 + (vec3d.y * 1.5 - vec3d2.y) * 0.5, vec3d.z * 1 + (vec3d.z * 1.5 - vec3d2.z) * 0.5);
            user.velocityModified = true;
            return true;
        } else {
            return false;
        }
    }
}
