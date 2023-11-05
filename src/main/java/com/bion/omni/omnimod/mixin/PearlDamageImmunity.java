package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnderPearlEntity.class)
public abstract class PearlDamageImmunity extends ThrownItemEntity {
    public PearlDamageImmunity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    @Redirect(method="onCollision", at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private boolean cancelDamage(Entity owner, DamageSource source, float amount) {
        Power power;
        if (!(((Apprentice)owner).omni$getConfigValue("enderPearlAffinity") == 1 && (power = ((Apprentice)owner).omni$getPowerById("enderPearlAffinity")) != null && ((Apprentice)owner).omni$getMana() >= power.getManaCost() && power.getLevel() >= 2)) {
            owner.damage(source, amount);
        } else {
            ((Apprentice) owner).omni$changeMana(-power.getManaCost());
        }
        return true;
    }
}
