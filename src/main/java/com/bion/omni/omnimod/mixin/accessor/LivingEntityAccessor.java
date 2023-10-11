package com.bion.omni.omnimod.mixin.accessor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Invoker
    SoundEvent invokeGetHurtSound(DamageSource source);
    @Invoker
    SoundEvent invokeGetDeathSound();
}
