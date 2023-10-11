package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.element.Air;
import com.bion.omni.omnimod.element.Moon;
import com.bion.omni.omnimod.element.Water;
import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.power.air.Pacifism;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.TridentItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerDamageMultiplier extends LivingEntity {
    Entity target = null;

    protected PlayerDamageMultiplier(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At(value = "HEAD"), method = "attack", locals = LocalCapture.CAPTURE_FAILHARD)
    private void onHit(Entity target, CallbackInfo ci) {
        this.target = target;
        if (((Apprentice)this).omni$getElement() instanceof Air) {
            Power power;
            if ((power = ((Apprentice)this).omni$getPowerById("pacifism")) != null) {
                ((Pacifism)power).resetTimer();
            }
        }
    }
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), index = 1, method = "attack")
    private float injected(float f) {
        assert target != null;
        if (((Apprentice)this).omni$getElement() instanceof Moon) {
            Power backstab = ((Apprentice) this).omni$getPowerById("backstab");
            if (backstab != null) {
                float diff = Math.abs(this.headYaw - target.getHeadYaw()) % 360;
                if (diff > 180) {
                    diff = 360 - diff;
                }
                if (diff <= 40) {
                    float multiplier = switch (backstab.getLevel()) {
                        case 1:
                            yield 1.3F;
                        case 2:
                            yield 1.5F;
                        case 3:
                            yield 1.7F;
                        default:
                            yield 0;
                    };
                    return f * multiplier;
                }
            }
        }
        if (((Apprentice)this).omni$getElement() instanceof Water) {
            if (getMainHandStack().getItem() instanceof TridentItem && ((Apprentice)this).omni$getConfigValue("tridentDamage") == 1 && ((Apprentice)this).omni$getMana() > ((Apprentice)this).omni$getPowerById("tridentDamage").getManaCost()) {
                ((Apprentice)this).omni$changeMana(-((Apprentice)this).omni$getPowerById("tridentDamage").getManaCost());
                return f * 2;
            }
        }
        return f;
    }
}
