package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.power.clarity.AstralProject;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class CancelSpectatorTeleport {
    @Shadow
    public ServerPlayerEntity player;
    @Inject(method = "onSpectatorTeleport", at = @At("HEAD"), cancellable = true)
    void cancelTeleport(CallbackInfo ci) {
        AstralProject power = (AstralProject) ((Apprentice) player).omni$getPowerById("astralProject");
        if (power != null && power.getBody() != null && !power.getBody().isRemoved()) {
            ci.cancel();
        }
    }
}
