package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.entity.custom.Pet;
import com.bion.omni.omnimod.mixin.accessor.MobEntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MobEntity.class)
public abstract class PetAmbientSounds extends Entity {
    public PetAmbientSounds(EntityType<?> type, World world) {
        super(type, world);
    }
    @ModifyVariable(method="playAmbientSound", at=@At("STORE"), ordinal = 0)
    public SoundEvent modifyAmbientSounds(SoundEvent soundEvent) {
        if ((Object)this instanceof Pet pet && pet.getCustomType() != null) {
            return ((MobEntityAccessor)pet.getCustomType().create(getWorld())).invokeGetAmbientSound();
        }
        return soundEvent;
    }
}
