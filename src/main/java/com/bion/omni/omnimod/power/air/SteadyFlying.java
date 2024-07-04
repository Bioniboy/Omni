package com.bion.omni.omnimod.power.air;

import com.bion.omni.omnimod.power.ContinuousPower;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class SteadyFlying extends ContinuousPower {
    public SteadyFlying(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Steady Flying " + getRomanNumeral(getLevel());
    }

    @Override
    public String getId() {
        return "steadyFlying";
    }

    @Override
    public double getManaCost() {
        return getLevel() == 1 ? 1 : 0.5;
    }

    @Override
    public Integer getInfluenceCost() {
        return 40;
    }
    @Override
    public String getAdvancementId() {
        return "steady_flying" + (getLevel() == 1 ? "" : "_" + getLevel());
    }

    @Override
    public Integer getMaxLevel() {
        return 2;
    }

    @Override
    public void use(ServerPlayerEntity user) {
        if (user.isFallFlying()) {
            super.use(user);
            Vec3d vec3d = user.getRotationVector();
            Vec3d vec3d2 = user.getVelocity();
            user.addVelocity(vec3d.x * 0.01 + (vec3d.x * 1.5 - vec3d2.x) * 0.1, vec3d.y * 0.01 + (vec3d.y * 1.5 - vec3d2.y) * 0.1, vec3d.z * 0.01 + (vec3d.z * 1.5 - vec3d2.z) * 0.1);
            user.velocityModified = true;
        }
    }
}
