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

public class InfluenceCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess s, CommandManager.RegistrationEnvironment p) {
        dispatcher.register(CommandManager.literal("influence")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("set")
                        .then(CommandManager.argument("value", IntegerArgumentType.integer())
                                .executes(context -> setPlayerInfluence(context.getSource().getPlayer(), IntegerArgumentType.getInteger(context, "value")))
                                .then(CommandManager.argument("targets", EntityArgumentType.players())
                                        .executes(context -> set(EntityArgumentType.getPlayers(context, "targets"), IntegerArgumentType.getInteger(context, "value")))
                                )
                        )
                )
                .then(CommandManager.literal("add")
                        .then(CommandManager.argument("value", IntegerArgumentType.integer())
                                .executes(context -> {
                                    return addPlayerInfluence(context.getSource().getPlayer(), IntegerArgumentType.getInteger(context, "value"));
                                })
                                .then(CommandManager.argument("targets", EntityArgumentType.players())
                                        .executes(context -> {
                                            return add(EntityArgumentType.getPlayers(context, "targets"), IntegerArgumentType.getInteger(context, "value"));
                                        })
                                )
                        )
                )
                .then(CommandManager.literal("remove")
                        .then(CommandManager.argument("value", IntegerArgumentType.integer())
                                .executes(context -> {
                                    return addPlayerInfluence(context.getSource().getPlayer(), -IntegerArgumentType.getInteger(context, "value"));
                                })
                                .then(CommandManager.argument("targets", EntityArgumentType.players())
                                        .executes(context -> {
                                            return add(EntityArgumentType.getPlayers(context, "targets"), -IntegerArgumentType.getInteger(context, "value"));
                                        })
                                )
                        )
                )
        );
    }

    private static int set(Collection<ServerPlayerEntity> players, int value) throws CommandSyntaxException {
        for (ServerPlayerEntity player : players) {
            setPlayerInfluence(player, value);
        }
        return 1;
    }

    private static int setPlayerInfluence(ServerPlayerEntity player, int value) {
        ((Apprentice)player).setInfluence(value);
        return 1;
    }

    private static int add(Collection<ServerPlayerEntity> players, int value) throws CommandSyntaxException {
        for (ServerPlayerEntity player : players) {
            addPlayerInfluence(player, value);
        }
        return 1;
    }

    private static int addPlayerInfluence(ServerPlayerEntity player, int value) {
        ((Apprentice)player).changeInfluence(value);
        return 1;
    }
}