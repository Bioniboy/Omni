package com.bion.omni.omnimod.command;

import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.powers.storm.Mark;
import com.bion.omni.omnimod.util.Apprentice;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;

public class TrollSoundCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess s, CommandManager.RegistrationEnvironment p) {
        dispatcher.register(CommandManager.literal("troll")
                .then(CommandManager.literal("playsound")
                        .requires(serverCommandSource -> {return ((Apprentice)serverCommandSource.getPlayer()).omni$getPowerById("whatWasThat") != null;})
                        .then(CommandManager.argument("sound", IdentifierArgumentType.identifier()).suggests(SuggestionProviders.AVAILABLE_SOUNDS)
                                .executes(TrollSoundCommand::run)
                        )
                )
                .then(CommandManager.literal("mark")
                        .requires(serverCommandSource -> {return ((Apprentice)serverCommandSource.getPlayer()).omni$getPowerById("mark") != null;})
                        .executes(TrollSoundCommand::mark)
                )
        );
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
//        EntityDataInterface player = (EntityDataInterface) context.getSource().getPlayer();
//        BlockPos playerPos = context.getSource().getPlayer().getBlockPos();
//
//        player.getPersistentData().putIntArray("homepos",
//                new int[] {playerPos.getX(), playerPos.getY(), playerPos.getZ()});
//
//        context.getSource().sendFeedback(Text.literal("Set home"), false);
//        context.getSource().getServer().getCommandManager().sendCommandTree(context.getSource().getPlayer());

        ServerPlayerEntity player = context.getSource().getPlayer();
        Apprentice apprentice = (Apprentice) player;
        Power markPower = apprentice.omni$getPowerById("mark");
        if (markPower != null) {
            Entity target = ((Mark)markPower).getTarget(context.getSource().getWorld());
            if (target instanceof ServerPlayerEntity targetPlayer) {
                if (apprentice.omni$getMana() >= 20) {
                    apprentice.omni$changeMana(-20);
                    RegistryEntry<SoundEvent> registryEntry = RegistryEntry.of(SoundEvent.of(IdentifierArgumentType.getIdentifier(context, "sound")));
                    targetPlayer.networkHandler.sendPacket(new PlaySoundS2CPacket(registryEntry, SoundCategory.MASTER, targetPlayer.getX(), targetPlayer.getY(), targetPlayer.getZ(), 1.0f, 1.0f, (long) 1.0));
                    context.getSource().getPlayer().networkHandler.sendPacket(new PlaySoundS2CPacket(registryEntry, SoundCategory.MASTER, player.getX(), player.getY(), player.getZ(), 1.0f, 1.0f, (long) 1.0));
                    return 1;
                } else {
                    context.getSource().sendError(Text.literal("Not enough mana"));
                    return 0;
                }
            }
        }
        context.getSource().sendError(Text.literal("No valid mark"));
        return 0;
    }

    public static int mark(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Apprentice apprentice = (Apprentice) context.getSource().getPlayer();
        Power markPower = apprentice.omni$getPowerById("mark");
        if (markPower != null) {
            ((Mark) markPower).setTarget(context.getSource().getPlayer());
        }
        return 1;
    }
}
