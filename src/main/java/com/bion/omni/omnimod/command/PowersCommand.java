package com.bion.omni.omnimod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.bion.omni.omnimod.util.EntityDataInterface;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PowersCommand {
//    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess s, CommandManager.RegistrationEnvironment p) {
//        dispatcher.register(CommandManager.literal("power")
//                .then(CommandManager.literal("buy")
//                        .then(CommandManager.literal("glow")
//                                .requires(context -> {
//                                    NbtCompound powers = ((EntityDataInterface)context.getPlayer()).getPersistentData().getCompound("powers");
//                                    return !powers.contains("glow");
//                                })
//                                .executes(context -> {return grantPower(context, "glow", 20);})
//                        )
//                )
//        );
//    }
//    private static int grantPower(CommandContext<ServerCommandSource> context, String powerId, Integer cost) {
//        ServerPlayerEntity player = context.getSource().getPlayer();
//        Scoreboard scoreboard = player.getScoreboard();
//        ScoreboardPlayerScore score = scoreboard.getPlayerScore(player.getName().getString(), scoreboard.getObjective("Influence"));
//        int influence = score.getScore();
//        if (influence >= cost) {
//            score.incrementScore(-cost);
//            ((EntityDataInterface) player).getPersistentData().getCompound("powers").putByte(powerId, (byte) 0);
//            player.getServer().getCommandManager().sendCommandTree(player);
//            context.getSource().sendFeedback(() -> Text.literal("You feel new power within you"), true);
//        } else {
//            context.getSource().sendError(Text.literal("You don't have enough Influence. Required: " + cost));
//        }
//
//        return 1;
//    }
}
