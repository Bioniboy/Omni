package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.elements.Air;
import com.bion.omni.omnimod.elements.Life;
import com.bion.omni.omnimod.elements.Storm;
import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.powers.air.AirShield;
import com.bion.omni.omnimod.powers.air.AirWalk;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEatMultiplier extends LivingEntity {
    protected PlayerEatMultiplier(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public HungerManager getHungerManager() {
        throw new AssertionError();
    }

    //    @Inject(method = "damage", at = @At("RETURN"))
//    private void injected(float amount, CallbackInfoReturnable ci) {
//
//    }
    @Inject(at = @At(value = "HEAD"), method = "eatFood")
    private void omnimod$multiplyHunger(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (((Apprentice) this).getElement() instanceof Life) {
            Power power = ((Apprentice) this).getPowerById("transformPig");

            if (power != null && ((Apprentice) this).getConfigValue("transformPig") == 1) {
                this.getHungerManager().eat(stack.getItem(), stack);
                if (stack.getItem() == Items.PORKCHOP || stack.getItem() == Items.COOKED_PORKCHOP) {
                    addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 2));
                }
            }
        }
    }
}
