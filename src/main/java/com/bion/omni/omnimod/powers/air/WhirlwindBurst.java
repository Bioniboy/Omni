package com.bion.omni.omnimod.powers.air;

import com.bion.omni.omnimod.powers.ImpulsePower;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;

public class WhirlwindBurst extends ImpulsePower {

    @Override
    public String getName() {
        return "Whirlwind Burst";
    }

    @Override
    public String getId() {
        return "whirlwindBurst";
    }

    @Override
    public Integer getInfluenceCost() {
        return 30;
    }

    @Override
    public double getManaCost() {
        return 20;
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        if (super.activate(user)) {
            BlockHitResult result = user.getWorld().raycast(new RaycastContext(user.getEyePos(), user.getEyePos().add(user.getRotationVector().multiply(5)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, user));
            BlockPos pos;
            //OmniMod.LOGGER.info(result.getType().name());
            if (result.getType().equals(HitResult.Type.BLOCK)) {
                pos = result.getBlockPos().add(result.getSide().getOffsetX(), result.getSide().getOffsetY(), result.getSide().getOffsetZ());
            } else {
                pos = BlockPos.ofFloored(user.getEyePos().add(0, -0.5, 0).add(user.getRotationVector().multiply(2)));
            }
            AreaEffectCloudEntity cloud = EntityType.AREA_EFFECT_CLOUD.create(user.getWorld(), null, null, pos, SpawnReason.NATURAL, false, false);
            cloud.addEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 2));
            cloud.setRadius(4);
            cloud.setDuration(600);
            cloud.setParticleType(ParticleTypes.CLOUD);
            user.getWorld().spawnEntity(cloud);
            return true;
        } else {
            return false;
        }
    }
}
