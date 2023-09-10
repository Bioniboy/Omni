package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(at = @At("HEAD"), method = "remove")
    private void onDisconnect(ServerPlayerEntity player, CallbackInfo ci) {
        for (var power : ((Apprentice)player).omni$getAllPowers()) {
            power.onDisconnect(player);
        }
    }
}
