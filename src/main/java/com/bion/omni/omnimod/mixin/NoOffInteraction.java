package com.bion.omni.omnimod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class NoOffInteraction {
    @Shadow
    public ServerPlayerEntity player;
    @Unique
    boolean mainHandSuccessful = false;
    @Inject(method="onPlayerInteractItem", at=@At(value="INVOKE", target="Lnet/minecraft/server/network/ServerPlayerInteractionManager;interactItem(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;", shift=At.Shift.BEFORE), cancellable = true)
    void cancelOffhand(PlayerInteractItemC2SPacket packet, CallbackInfo ci) {
        if (packet.getHand() == Hand.OFF_HAND && mainHandSuccessful) {
            ci.cancel();
        }
    }

    @Inject(method="onPlayerInteractBlock", at=@At(value="INVOKE", target="Lnet/minecraft/server/network/ServerPlayerInteractionManager;interactBlock(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;", shift=At.Shift.BEFORE), cancellable = true)
    void cancelOffhand(PlayerInteractBlockC2SPacket packet, CallbackInfo ci) {
        if (packet.getHand() == Hand.OFF_HAND && mainHandSuccessful) {
            ci.cancel();
        }
    }

    @Inject(method="onPlayerInteractItem", at=@At(value="INVOKE", target="Lnet/minecraft/util/ActionResult;shouldSwingHand()Z"))
    void setMainHandSuccessful(PlayerInteractItemC2SPacket packet, CallbackInfo ci, @Local ActionResult actionResult) {
        if (packet.getHand() == Hand.MAIN_HAND && actionResult.isAccepted()) {
            mainHandSuccessful = true;
        }
    }
    @Inject(method="onPlayerInteractBlock", at=@At(value="INVOKE", target="Lnet/minecraft/util/ActionResult;shouldSwingHand()Z"))
    void setMainHandSuccessful(PlayerInteractBlockC2SPacket packet, CallbackInfo ci, @Local ActionResult actionResult) {
        if (packet.getHand() == Hand.MAIN_HAND && actionResult.isAccepted()) {
            mainHandSuccessful = true;
        }
    }

    @Inject(method="tick", at=@At("HEAD"))
    void resetMainHandSuccessful(CallbackInfo ci) {
        mainHandSuccessful = false;
    }
}
