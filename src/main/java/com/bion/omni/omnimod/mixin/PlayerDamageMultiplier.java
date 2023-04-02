package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.elements.Air;
import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.powers.air.Pacifism;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
        if (((Apprentice)this).getElement() instanceof Air) {
            Power power;
            if ((power = ((Apprentice)this).getPowerById("pacifism")) != null) {
                ((Pacifism)power).resetTimer();
            }
        }
    }
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), index = 1, method = "attack")
    private float injected(float f) {
        assert target != null;
        if (((Apprentice)this).getElement() != null && Objects.equals(((Apprentice) this).getElement().getName(), "Moon")) {
            Power backstab = ((Apprentice) this).getPowerById("backstab");
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
        return f;
    }
}
