package com.bion.omni.omnimod.power.storm;

import com.bion.omni.omnimod.power.ImpulsePower;
import com.bion.omni.omnimod.power.Power;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;

import java.util.Objects;
import java.util.UUID;

public class Mark extends ImpulsePower {
    private String targetId = "";

    public Mark(int level) {
        super(level);
    }

    @Override
    public NbtCompound toNbt() {
        NbtCompound nbt = super.toNbt();
        nbt.putString("targetId", targetId);
        return nbt;
    }

    @Override
    public Integer getInfluenceCost() {
        return 15;
    }

    @Override
    public double getManaCost() {
        return 10;
    }

    @Override
    public Power setNbt(NbtCompound nbt) {
        super.setNbt(nbt);
        targetId = nbt.getString("targetId");
        return this;
    }

    @Override
    public String getName() {
        return "Mark";
    }

    @Override
    public String getId() {
        return "mark";
    }

    @Override
    public String getAdvancementId() {
        return "mark";
    }

    public LivingEntity getTarget(ServerWorld world) {
        if (!Objects.equals(targetId, "")) {
            return (LivingEntity) world.getEntity(UUID.fromString(targetId));
        } else {
            return null;
        }
    }
    public void setTarget(LivingEntity target) {
        this.targetId = target.getUuidAsString();
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {

        EntityHitResult hitResult = ProjectileUtil.raycast(user.getCameraEntity(), user.getEyePos(), user.getEyePos().add(user.getRotationVector().multiply(4)), new Box(user.getBlockPos()).expand(5), EntityPredicates.VALID_LIVING_ENTITY, 16);
        if (hitResult != null && hitResult.getEntity() instanceof LivingEntity entity) {
            if (super.activate(user)) {
                user.sendMessage(Text.literal("Target marked").formatted(Formatting.DARK_GRAY));
                targetId = entity.getUuidAsString();
                return true;
            }
        }
        return false;
    }
}
