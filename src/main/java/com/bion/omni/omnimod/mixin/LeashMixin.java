package com.bion.omni.omnimod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.Leashable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Leashable.class)
public interface LeashMixin {
    @Inject(method="applyLeashElasticity(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;F)V", at=@At("TAIL"))
    private static <E extends Entity> void leashElasticityMixin(E entity, Entity leashHolder, float distance, CallbackInfo ci) {
        double d = (entity.getX() - leashHolder.getX()) / (double)distance;
        double e = (entity.getY() - leashHolder.getY()) / (double)distance;
        double f = (entity.getZ() - leashHolder.getZ()) / (double)distance;
        leashHolder.addVelocity(Math.copySign(d * d * 0.4, d), Math.copySign(e * e * 0.4, e), Math.copySign(f * f * 0.4, f));
        leashHolder.velocityModified = true;
    }
}
