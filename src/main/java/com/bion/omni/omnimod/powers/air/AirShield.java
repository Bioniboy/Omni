package com.bion.omni.omnimod.powers.air;

import com.bion.omni.omnimod.powers.ContinuousPower;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class AirShield extends ContinuousPower {
    private Vec3d playerPos = null;
    private boolean active = false;
    public AirShield(Integer level) {
        this.setLevel(level);
    }
    @Override
    public String getAdvancementId() {
        return "air_shield" + (getLevel() == 1 ? "" : "_" + getLevel());
    }
    @Override
    public String getName() {
        return "Air Shield " + getRomanNumeral(getLevel());
    }

    @Override
    public String getId() {
        return "airShield";
    }

    @Override
    public String getPreRequisiteId() {
        return "push";
    }

    @Override
    public Integer getInfluenceCost() {
        return switch (getLevel()) {
            case 1:
                yield 50;
            case 2:
                yield 70;
            default:
                yield 0;
        };
    }

    @Override
    public double getManaCost() {
        return 15;
    }

    @Override
    public void start(ServerPlayerEntity user) {
        playerPos = user.getPos();
        active = true;
    }

    @Override
    public void use(ServerPlayerEntity user) {
        user.requestTeleport(playerPos.x, playerPos.y, playerPos.z);
        for (double x = -2; x <= 2; x += 0.2) {
            for (double y = -2; y <= 2; y += 0.2) {
                for (double z = -2; z <= 2; z += 0.2) {
                    double distance = new Vec3d(user.getX() + x, user.getY() + y, user.getZ() + z).distanceTo(user.getPos());
                    if (user.getRandom().nextDouble() > 0.95 && distance > 1.9 && distance <= 2.0) {
                        user.getServerWorld().spawnParticles(ParticleTypes.CLOUD, user.getX() + x, user.getY() + y + 1, user.getZ() + z, 1, 0, 0, 0, 0);
                    }
                }
            }
        }
    }

    @Override
    public void stop(ServerPlayerEntity user) {
        active = false;
    }
    public boolean getActive() {
        return active;
    }
}
