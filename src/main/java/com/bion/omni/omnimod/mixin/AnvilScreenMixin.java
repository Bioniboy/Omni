package com.bion.omni.omnimod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenMixin {
    @Inject(method="updateResult", at= @At(value = "INVOKE", target = "Lnet/minecraft/inventory/CraftingResultInventory;setStack(ILnet/minecraft/item/ItemStack;)V", ordinal = 4))
    void noExtraRepairCost(CallbackInfo ci, @Local(ordinal=1) ItemStack itemStack2) {
        itemStack2.set(DataComponentTypes.REPAIR_COST, 0);
    }
}
