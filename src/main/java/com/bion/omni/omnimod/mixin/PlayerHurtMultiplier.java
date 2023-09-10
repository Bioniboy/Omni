package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.elements.Air;
import com.bion.omni.omnimod.elements.Fire;
import com.bion.omni.omnimod.elements.Storm;
import com.bion.omni.omnimod.powers.air.AirShield;
import com.bion.omni.omnimod.powers.air.AirWalk;
import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

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
            if (((Apprentice) this).omni$getElement() instanceof Storm) {
                Power power = ((Apprentice)this).omni$getPowerById("explosionResistance");
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
            if (((Apprentice) this).omni$getElement() instanceof Storm || ((Apprentice) this).omni$getElement() instanceof Air) {
                Power power = ((Apprentice)this).omni$getPowerById("kineticResistance");
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
            if (((Apprentice) this).omni$getElement() instanceof Storm) {
                Power power = ((Apprentice)this).omni$getPowerById("fallDamageResistance");
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
        } else if (source.isOf(DamageTypes.HOT_FLOOR)) {
            if (((Apprentice)this).omni$getElement() instanceof Fire) {
                if (((Apprentice)this).omni$getConfigValue("flameImmune") == 1) {
                    return 0F;
                }
            }
        }
        if (((Apprentice)this).omni$getElement() instanceof Air) {
            Power airWalk;
            if ((airWalk = ((Apprentice) this).omni$getPowerById("airWalk")) != null) {
                ((AirWalk) airWalk).setTickCooldown(60);
            }
            Power airShield;
            if ((airShield = ((Apprentice) this).omni$getPowerById("airShield")) != null && ((AirShield) airShield).getActive()) {
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
