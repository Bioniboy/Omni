package com.bion.omni.omnimod.power.air;

import com.bion.omni.omnimod.power.ImpulsePower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class Push extends ImpulsePower {
    public Push(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Push " + getRomanNumeral(getLevel());
    }

    @Override
    public String getId() {
        return "push";
    }

    @Override
    public Integer getMaxLevel() {
        return 2;
    }

    @Override
    public Integer getInfluenceCost() {
        return 40;
    }

    @Override
    public double getManaCost() {
        return 30;
    }

    @Override
    public String getPreRequisiteId() {
        return "whirlwindBurst";
    }
    @Override
    public String getAdvancementId() {
        return "push" + (getLevel() == 1 ? "" : "_" + getLevel());
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        int range = switch (getLevel()) {
            case 1:
                yield 8;
            case 2:
                yield 14;
            default:
                yield 0;
        };
        if (super.activate(user)) {
            Box box = new Box(user.getBlockPos()).expand(range);
            for (var entity : user.getWorld().getOtherEntities(user, box)) {
                double distance = entity.getPos().distanceTo(user.getPos());
                if (distance <= range && entity instanceof LivingEntity mob) {
                    Vec3d velocity = user.getPos().subtract(entity.getPos()).multiply(1/distance);
                    mob.takeKnockback((range - distance) / 3, velocity.x, velocity.z);
                    entity.addVelocity(0, 0.7, 0);
                    entity.velocityModified = true;
                    user.getServerWorld().spawnParticles(ParticleTypes.CLOUD, entity.getX(), entity.getY(), entity.getZ(), 10, 0.3, 0.3, 0.3, 0.1);
                }
            }
            return true;
        }
        return false;
    }
}
