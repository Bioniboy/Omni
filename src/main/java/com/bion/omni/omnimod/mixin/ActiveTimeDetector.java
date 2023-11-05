package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.util.AfkUtil;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.Entity;
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

import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ActiveTimeDetector {
    @Shadow
    public ServerPlayerEntity player;

    @Shadow protected abstract boolean isEntityOnAir(Entity entity);

    @Unique
    int afkTicks = 0;
    @Unique
    boolean isAfk = false;
//    @Unique
//    int nextStreakDay = 0;
//    @Unique
//    int nextStreakYear = 0;
//    @Unique
//    int streak = 0;

    @Inject(method="tick", at=@At("HEAD"))
    private void incrementTime(CallbackInfo ci) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT-7"));
        AfkUtil afkUtil = (AfkUtil) player;
        if (now.getDayOfYear() != afkUtil.omni$getPrevActiveDay()) {

            afkUtil.omni$setPrevActiveDay(now.getDayOfYear());
            afkUtil.omni$setActiveTicks(0);
            ((AfkUtil)player).omni$setGotReward(false);
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
                    player.sendMessageToClient(Text.literal("You are now AFK"), false);
                }
            } else if (!((AfkUtil)player).omni$getGotReward()) {
                ((Apprentice) player).omni$changeInfluence(2);
                ((AfkUtil)player).omni$setGotReward(true);
                player.sendMessageToClient(Text.literal("Your Influence has been increased by 2 (Active for one hour)"), false);
                LocalDate streakDate = LocalDate.ofYearDay(afkUtil.omni$getNextStreakYear(), afkUtil.omni$getNextStreakDay());
                if (streakDate.isBefore(now.toLocalDate())) {
                    LocalDate nextStreakDate = now.plusDays(7 - now.getDayOfWeek().getValue()).toLocalDate();
                    player.sendMessageToClient(Text.literal(nextStreakDate.toString()), false);
                    afkUtil.omni$setNextStreakDay(nextStreakDate.getDayOfYear());
                    afkUtil.omni$setNextStreakYear(nextStreakDate.getYear());
                    if (streakDate.plusDays(7).isAfter(now.toLocalDate())) {
                        afkUtil.omni$setStreak(afkUtil.omni$getStreak() + 1);
                        int influenceIncrease = switch (afkUtil.omni$getStreak()) {
                            case 0, 1:
                                yield 0;
                            case 2:
                                yield 3;
                            case 3:
                                yield 4;
                            default:
                                yield 5;
                        };
                        player.sendMessageToClient(Text.literal("Your Influence has been increased by " + influenceIncrease + " (Weekly streak: " + afkUtil.omni$getStreak() + ")"), false);
                        ((Apprentice)player).omni$changeInfluence(influenceIncrease);
                    }
                    else {
                        player.sendMessageToClient(Text.literal("Weekly streak: 1. Play for an hour again within a week of next Sunday for additional Influence"), false);
                        afkUtil.omni$setStreak(1);
                    }
                }

            }
        }
    }

    @Inject(method="onPlayerMove", at=@At("HEAD"))
    private void detectAfk(PlayerMoveC2SPacket packet, CallbackInfo ci) {
        if (packet instanceof PlayerMoveC2SPacket.Full || packet instanceof PlayerMoveC2SPacket.LookAndOnGround) {
            afkTicks = 0;
            if (isAfk) {
                isAfk = false;
                player.sendMessageToClient(Text.literal("You are no longer AFK"), false);
            }

        }
    }
}
