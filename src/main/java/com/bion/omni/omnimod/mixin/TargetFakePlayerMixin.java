package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.entity.custom.PlayerBody;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ActiveTargetGoal.class)
public abstract class TargetFakePlayerMixin<T extends LivingEntity> extends TrackTargetGoal {
    @Final
    @Shadow
    protected Class<T> targetClass;

    @Shadow
    protected Box getSearchBox(double distance) {
        throw new AssertionError();
    };

    @Shadow
    protected TargetPredicate targetPredicate;
    public TargetFakePlayerMixin(MobEntity mob, boolean checkVisibility) {
        super(mob, checkVisibility);
    }
    @Redirect(method = "findClosestTarget", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/ai/goal/ActiveTargetGoal;targetEntity:Lnet/minecraft/entity/LivingEntity;", opcode = Opcodes.PUTFIELD))
    private void targetPlayerBody(ActiveTargetGoal<T> goal, LivingEntity entity) {
        if (targetClass == PlayerEntity.class || targetClass == ServerPlayerEntity.class) {
            PlayerBody closestFakePlayer = this.mob.getWorld().getClosestEntity(this.mob.getWorld().getEntitiesByClass(PlayerBody.class, this.getSearchBox(this.getFollowRange()), livingEntity -> true), this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            if (closestFakePlayer == null) {
                goal.setTargetEntity(entity);
                return;
            }
            PlayerEntity closestPlayer = this.mob.getWorld().getClosestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            if (closestPlayer == null) {
                goal.setTargetEntity(closestFakePlayer);
                return;
            }
            goal.setTargetEntity(this.mob.getPos().squaredDistanceTo(closestFakePlayer.getPos()) < this.mob.getPos().squaredDistanceTo(closestPlayer.getPos()) ? closestFakePlayer : closestPlayer);

        }
    }
}
