package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.item.BackpackItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class ArmorSlotMixin {

    @Shadow
    public int id;

    @Shadow public abstract ItemStack getStack();

    @Inject(method="canTakeItems", at=@At("HEAD"), cancellable = true)
    public void lockBackpack(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> cir) {
        if (id == 6 && !getStack().getComponents().getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).stream().toList().isEmpty()) {
            cir.setReturnValue(false);
        }
    }
}
