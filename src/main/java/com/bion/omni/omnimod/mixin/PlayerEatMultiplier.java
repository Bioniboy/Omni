package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.element.Life;
import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
    private void omnimod$multiplyHunger(World world, ItemStack stack, FoodComponent foodComponent, CallbackInfoReturnable<ItemStack> cir) {
        if (((Apprentice) this).omni$getElement() instanceof Life) {
            Power power = ((Apprentice) this).omni$getPowerById("transformPig");

            if (power != null && ((Apprentice) this).omni$getConfigValue("transformPig") == 1) {
                this.getHungerManager().eat(stack.get(DataComponentTypes.FOOD));
                if (stack.getItem() == Items.PORKCHOP || stack.getItem() == Items.COOKED_PORKCHOP) {
                    addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 2));
                }
            }
        }
    }
}
