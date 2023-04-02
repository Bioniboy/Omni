package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.elements.Air;
import com.bion.omni.omnimod.elements.Storm;
import com.bion.omni.omnimod.powers.air.AirShield;
import com.bion.omni.omnimod.powers.air.AirWalk;
import com.mojang.authlib.GameProfile;
import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerHurtMultiplier extends LivingEntity {
    protected PlayerHurtMultiplier(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    //    @Inject(method = "damage", at = @At("RETURN"))
//    private void injected(float amount, CallbackInfoReturnable ci) {
//
//    }
    @ModifyVariable(at = @At(value = "HEAD"), ordinal = 0, method = "damage")
    private float omnimod$multiplyDamage(float amount, DamageSource source) {
        if (source.isOf(DamageTypes.EXPLOSION)) {
            if (((Apprentice) this).getElement() instanceof Storm) {
                Power power = ((Apprentice)this).getPowerById("explosionResistance");
                if (power != null) {
                    float multiplier = switch (power.getLevel()) {
                        case 1:
                            yield .66F;
                        case 2:
                            yield .33F;
                        case 3:
                            yield 0;
                        default:
                            yield 1;
                    };
                    return amount * multiplier;
                }
            }
        } else if (source.isOf(DamageTypes.FLY_INTO_WALL)) {
            if (((Apprentice) this).getElement() instanceof Storm || ((Apprentice) this).getElement() instanceof Air) {
                Power power = ((Apprentice)this).getPowerById("kineticResistance");
                if (power != null) {
                    float multiplier = switch (power.getLevel()) {
                        case 1:
                            yield .5F;
                        case 2:
                            yield 0;
                        default:
                            yield 1F;
                    };
                    return amount * multiplier;
                }
            }
        } else if (source.isOf(DamageTypes.FALL)) {
            if (((Apprentice) this).getElement() instanceof Storm) {
                Power power = ((Apprentice)this).getPowerById("fallDamageResistance");
                if (power != null) {
                    float multiplier = switch (power.getLevel()) {
                        case 1:
                            yield 0.66F;
                        case 2:
                            yield 0.33F;
                        default:
                            yield 1F;
                    };
                    return amount * multiplier;
                }
            }
        }
        if (((Apprentice)this).getElement() instanceof Air) {
            Power airWalk;
            if ((airWalk = ((Apprentice) this).getPowerById("airWalk")) != null) {
                ((AirWalk) airWalk).setTickCooldown(60);
            }
            Power airShield;
            if ((airShield = ((Apprentice) this).getPowerById("airShield")) != null && ((AirShield) airShield).getActive()) {
                float multiplier = switch (airShield.getLevel()) {
                    case 1:
                        yield .40F;
                    case 2:
                        yield .25F;
                    default:
                        yield 1F;
                };
                return amount * multiplier;
            }
        }
        return amount;
    }
}
