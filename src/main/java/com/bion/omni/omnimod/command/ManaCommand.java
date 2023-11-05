package com.bion.omni.omnimod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;

import static com.bion.omni.omnimod.power.Mana.manaInitialize;
import static com.bion.omni.omnimod.power.Mana.manaShow;

public class ManaCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess s, CommandManager.RegistrationEnvironment p) {
        dispatcher.register(CommandManager.literal("mana")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("set")
                        .then(CommandManager.argument("value", IntegerArgumentType.integer())
                                .executes(context -> setPlayerMana(context.getSource().getPlayer(), IntegerArgumentType.getInteger(context, "value")))
                                .then(CommandManager.argument("targets", EntityArgumentType.players())
                                        .executes(context -> set(EntityArgumentType.getPlayers(context, "targets"), IntegerArgumentType.getInteger(context, "value")))
                                )
                        )
                )
                .then(CommandManager.literal("add")
                        .then(CommandManager.argument("value", IntegerArgumentType.integer())
                                .executes(context -> addPlayerMana(context.getSource().getPlayer(), IntegerArgumentType.getInteger(context, "value")))
                                .then(CommandManager.argument("targets", EntityArgumentType.players())
                                        .executes(context -> add(EntityArgumentType.getPlayers(context, "targets"), IntegerArgumentType.getInteger(context, "value")))
                                )
                        )
                )
                .then(CommandManager.literal("remove")
                        .then(CommandManager.argument("value", IntegerArgumentType.integer())
                                .executes(context -> addPlayerMana(context.getSource().getPlayer(), -IntegerArgumentType.getInteger(context, "value")))
                                .then(CommandManager.argument("targets", EntityArgumentType.players())
                                        .executes(context -> add(EntityArgumentType.getPlayers(context, "targets"), -IntegerArgumentType.getInteger(context, "value")))
                                )
                        )
                )
                .then(CommandManager.literal("initialize")
                        .executes(context -> {manaInitialize((Apprentice)context.getSource().getPlayer()); return 1;})
                        .then(CommandManager.argument("targets", EntityArgumentType.players())
                                .executes(context -> initialize(EntityArgumentType.getPlayers(context, "targets")))
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
                .then(CommandManager.literal("refill")
                        .executes(context -> setPlayerMana(context.getSource().getPlayer(), ((Apprentice)context.getSource().getPlayer()).omni$getManaMax()))
                )
        );
    }

    private static int set(Collection<ServerPlayerEntity> players, int value) throws CommandSyntaxException {
        for (ServerPlayerEntity player : players) {
            setPlayerMana(player, value);
        }
        return 1;
    }
    private static int setPlayerMana(ServerPlayerEntity player, double value) {
        ((Apprentice)player).omni$setMana(value);
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
        setPlayerMana(player, value + ((Apprentice)player).omni$getMana());
        return 1;
    }
    private static int initialize(Collection<ServerPlayerEntity> players) throws CommandSyntaxException {
        for (ServerPlayerEntity player : players) {
            manaInitialize((Apprentice) player);
        }
        return 1;
    }
    private static int upgradeRegen(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        ((Apprentice)player).omni$upgradeManaRegen();
        return 1;
    }
    private static int upgradeMax(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        ((Apprentice)player).omni$upgradeManaMax();
        return 1;
    }
}
