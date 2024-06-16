package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.entity.custom.Pet;
import com.bion.omni.omnimod.mixin.accessor.LivingEntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class PetHurtSounds extends Entity {
    protected PetHurtSounds(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

//    @ModifyVariable(method="playHurtSound", at=@At("STORE"), ordinal = 0)
//    public SoundEvent modifyHurtSounds(SoundEvent soundEvent, DamageSource source) {
//        if ((Object)this instanceof Pet pet && pet.getCustomType() != null) {
//            return ((LivingEntityAccessor)pet.getCustomType().create(getWorld())).invokeGetHurtSound(source);
//        }
//        return soundEvent;
//    }
//
//    @ModifyVariable(method="damage", at=@At("STORE"), ordinal = 0)
//    public SoundEvent modifyDeathSoundsDamage(SoundEvent soundEvent) {
//        if ((Object)this instanceof Pet pet && pet.getCustomType() != null) {
//            return ((LivingEntityAccessor)pet.getCustomType().create(getWorld())).invokeGetDeathSound();
//        }
//        return soundEvent;
//    }
//
//    @ModifyVariable(method="handleStatus", at=@At("STORE"), ordinal = 0)
//    public SoundEvent modifyDeathSoundsStatus(SoundEvent soundEvent) {
//        if ((Object)this instanceof Pet pet && pet.getCustomType() != null) {
//            return ((LivingEntityAccessor)pet.getCustomType().create(getWorld())).invokeGetDeathSound();
//        }
//        return soundEvent;
//    }
}
