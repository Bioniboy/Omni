package com.bion.omni.omnimod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;

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
        ((Apprentice)player).omni$setInfluence(value);
        return 1;
    }

    private static int add(Collection<ServerPlayerEntity> players, int value) throws CommandSyntaxException {
        for (ServerPlayerEntity player : players) {
            addPlayerInfluence(player, value);
        }
        return 1;
    }

    private static int addPlayerInfluence(ServerPlayerEntity player, int value) {
        ((Apprentice)player).omni$changeInfluence(value);
        return 1;
    }
}