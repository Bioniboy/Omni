package com.bion.omni.omnimod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.bion.omni.omnimod.util.Apprentice;
import com.bion.omni.omnimod.util.EntityDataInterface;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;
import java.util.Iterator;

import static com.bion.omni.omnimod.powers.Mana.manaInitialize;
import static com.bion.omni.omnimod.powers.Mana.manaShow;

public class ManaCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess s, CommandManager.RegistrationEnvironment p) {
        dispatcher.register(CommandManager.literal("mana")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("set")
                        .then(CommandManager.argument("value", IntegerArgumentType.integer())
                                .executes(context -> {return setPlayerMana(context.getSource().getPlayer(), IntegerArgumentType.getInteger(context, "value"));})
                                .then(CommandManager.argument("targets", EntityArgumentType.players())
                                        .executes(context -> {return set(EntityArgumentType.getPlayers(context, "targets"), IntegerArgumentType.getInteger(context, "value"));})
                                )
                        )
                )
                .then(CommandManager.literal("add")
                        .then(CommandManager.argument("value", IntegerArgumentType.integer())
                                .executes(context -> {return addPlayerMana(context.getSource().getPlayer(), IntegerArgumentType.getInteger(context, "value"));})
                                .then(CommandManager.argument("targets", EntityArgumentType.players())
                                        .executes(context -> {return add(EntityArgumentType.getPlayers(context, "targets"), IntegerArgumentType.getInteger(context, "value"));})
                                )
                        )
                )
                .then(CommandManager.literal("remove")
                        .then(CommandManager.argument("value", IntegerArgumentType.integer())
                                .executes(context -> {return addPlayerMana(context.getSource().getPlayer(), -IntegerArgumentType.getInteger(context, "value"));})
                                .then(CommandManager.argument("targets", EntityArgumentType.players())
                                        .executes(context -> {return add(EntityArgumentType.getPlayers(context, "targets"), -IntegerArgumentType.getInteger(context, "value"));})
                                )
                        )
                )
                .then(CommandManager.literal("initialize")
                        .executes(context -> {manaInitialize((Apprentice)context.getSource().getPlayer()); return 1;})
                        .then(CommandManager.argument("targets", EntityArgumentType.players())
                                .executes(context -> {return initialize(EntityArgumentType.getPlayers(context, "targets"));})
                        )
                )
                .then(CommandManager.literal("upgrade")
                        .then(CommandManager.literal("regen")
                                .executes(ManaCommand::upgradeRegen)
                        )
                        .then(CommandManager.literal("max")
                                .executes(ManaCommand::upgradeMax)
                        )
                )
        );
    }

    private static int set(Collection<ServerPlayerEntity> players, int value) throws CommandSyntaxException {
        for (ServerPlayerEntity player : players) {
            setPlayerMana(player, value);
        }
        return 1;
    }
    private static int setPlayerMana(ServerPlayerEntity player, int value) {
        ((EntityDataInterface)player).getPersistentData().putInt("mana", value);
        manaShow(player);
        return 1;
    }
    private static int add(Collection<ServerPlayerEntity> players, int value) throws CommandSyntaxException {
        for (ServerPlayerEntity player : players) {
            addPlayerMana(player, value);
        }
        return 1;
    }
    private static int addPlayerMana(ServerPlayerEntity player, int value) {
        setPlayerMana(player, value + ((EntityDataInterface)player).getPersistentData().getInt("mana"));
        return 1;
    }
    private static int initialize(Collection<ServerPlayerEntity> players) throws CommandSyntaxException {
        for (Iterator pIt = players.iterator(); pIt.hasNext(); ) {
            ServerPlayerEntity player = (ServerPlayerEntity)pIt.next();
            manaInitialize((Apprentice)player);
        }
        return 1;
    }
    private static int upgradeRegen(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        NbtCompound omniData = ((EntityDataInterface)player).getPersistentData();
        int influenceRequirement = switch (omniData.getInt("manaRegenLevel")) {
            case 0:
                yield 60;
            case 1:
                yield 100;
            case 2:
                yield 160;
            case 3:
                yield 240;
            case 4:
                yield 340;
            default:
                yield 9999;
        };
        Scoreboard scoreboard = player.getScoreboard();
        ScoreboardPlayerScore score = scoreboard.getPlayerScore(player.getName().getString(), scoreboard.getObjective("Influence"));
        int influence = score.getScore();
        if (influence >= influenceRequirement) {
            omniData.putInt("manaRegenLevel", omniData.getInt("manaRegenLevel") + 1);
            score.incrementScore(-influenceRequirement);
            context.getSource().sendFeedback(Text.literal("Your mana regen has been upgraded to level " + omniData.getInt("manaRegenLevel")), true);
        } else {
            if (omniData.getInt("manaMaxLevel") >= 5) {
                context.getSource().sendError(Text.literal("You've already maxed out this upgrade"));
            } else {
                context.getSource().sendError(Text.literal("You don't have enough Influence. Required: " + influenceRequirement));
            }
        }
        return 1;
    }
    private static int upgradeMax(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        NbtCompound omniData = ((EntityDataInterface)player).getPersistentData();
        int influenceRequirement = switch (omniData.getInt("manaMaxLevel")) {
            case 0:
                yield 30;
            case 1:
                yield 60;
            case 2:
                yield 100;
            case 3:
                yield 150;
            case 4:
                yield 210;
            default:
                yield 9999;
        };
        Scoreboard scoreboard = player.getScoreboard();
        ScoreboardPlayerScore score = scoreboard.getPlayerScore(player.getName().getString(), scoreboard.getObjective("Influence"));
        int influence = score.getScore();
        if (influence >= influenceRequirement) {
            omniData.putInt("manaMaxLevel", omniData.getInt("manaMaxLevel") + 1);
            score.incrementScore(-influenceRequirement);
            context.getSource().sendFeedback(Text.literal("Your mana regen has been upgraded to level " + omniData.getInt("manaRegenLevel")), true);
        } else {
            if (omniData.getInt("manaMaxLevel") >= 5) {
                context.getSource().sendError(Text.literal("You've already maxed out this upgrade"));
            } else {
                context.getSource().sendError(Text.literal("You don't have enough Influence. Required: " + influenceRequirement));
            }
        }
        return 1;
    }
}
