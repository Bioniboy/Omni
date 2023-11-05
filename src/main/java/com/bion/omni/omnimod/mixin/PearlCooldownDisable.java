package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.power.ContinuousPower;
import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.util.Apprentice;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderPearlItem.class)
public abstract class PearlCooldownDisable extends Item {
    public PearlCooldownDisable(Settings settings) {
        super(settings);
    }

    @Inject(method="use", at=@At(value="TAIL"))
    private void disableCooldown(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (((Apprentice)user).omni$getPowerById("enderPearlAffinity") != null) {
            user.getItemCooldownManager().set(this, 0);
        }
    }
    @ModifyArg(method="use", at=@At(value="INVOKE", target="Lnet/minecraft/entity/projectile/thrown/EnderPearlEntity;setVelocity(Lnet/minecraft/entity/Entity;FFFFF)V"), index = 4)
    private float farPearl(float speed, @Local(ordinal=0) PlayerEntity user) {
        ContinuousPower farPearl;
        if ((farPearl = (ContinuousPower)((Apprentice)user).omni$getPowerById("farEnderPearl")) != null && farPearl.isActive((ServerPlayerEntity) user))
            return speed * 1.75f;
        return speed;
    }
}
