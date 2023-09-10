package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.util.AfkUtil;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ActiveTimeDetector {
    @Shadow
    public ServerPlayerEntity player;
    @Unique
    int afkTicks = 0;
    @Unique
    boolean isAfk = false;
    @Unique
    boolean gotReward = false;

    @Inject(method="tick", at=@At("HEAD"))
    private void incrementTime(CallbackInfo ci) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT-7"));
        AfkUtil afkUtil = (AfkUtil) player;
        if (now.getDayOfYear() != afkUtil.omni$getPrevActiveDay()) {

            afkUtil.omni$setPrevActiveDay(now.getDayOfYear());
            afkUtil.omni$setActiveTicks(0);
            gotReward = false;
        }
        if (!isAfk) {
            if (afkUtil.omni$getActiveTicks() < 72000) {
                if (afkTicks < 6000) {
                    afkUtil.omni$changeActiveTicks(1);
                    afkTicks++;
                } else {
                    afkUtil.omni$changeActiveTicks(afkTicks);
                    if (afkUtil.omni$getActiveTicks() < 0)
                        afkUtil.omni$setActiveTicks(0);
                    afkTicks = 0;
                    isAfk = true;
                }
            } else if (!gotReward) {
                ((Apprentice) player).omni$changeInfluence(2);
                gotReward = true;
                player.sendMessageToClient(Text.literal("Your Influence has been increased"), false);
            }
        }
    }

    @Inject(method="onPlayerMove", at=@At("HEAD"))
    private void detectAfk(PlayerMoveC2SPacket packet, CallbackInfo ci) {
        if (packet instanceof PlayerMoveC2SPacket.Full || packet instanceof PlayerMoveC2SPacket.LookAndOnGround) {
            afkTicks = 0;
            isAfk = false;
        }
    }
}
