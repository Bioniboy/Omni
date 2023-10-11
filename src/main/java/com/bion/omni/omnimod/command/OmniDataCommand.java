package com.bion.omni.omnimod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.bion.omni.omnimod.util.EntityDataInterface;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class OmniDataCommand {
//    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess s, CommandManager.RegistrationEnvironment p) {
//        dispatcher.register(CommandManager.literal("omnidata")
//                .then(CommandManager.literal("get")
//                        .executes(OmniDataCommand::get)
//                )
//                .then(CommandManager.literal("set")
//                        .then(CommandManager.argument("key", StringArgumentType.string())
//                                .then(CommandManager.argument("value", IntegerArgumentType.integer())
//                                        .then(CommandManager.literal("int")
//                                                .executes(context -> {return setInt(context.getSource().getPlayer(), StringArgumentType.getString(context, "key"), IntegerArgumentType.getInteger(context, "value"));})
//                                        )
//
//                                )
//                                .then(CommandManager.argument("value", StringArgumentType.string())
//                                        .then(CommandManager.literal("string")
//                                                .executes(context -> {return setString(context.getSource().getPlayer(), StringArgumentType.getString(context, "key"), StringArgumentType.getString(context, "value"));})
//                                        )
//
//                                )
//                        )
//                )
//
//        );
//    }
//    private static int get(CommandContext<ServerCommandSource> context) {
//        ServerPlayerEntity player = context.getSource().getPlayer();
//        EntityDataInterface dataPlayer = (EntityDataInterface) player;
//        player.sendMessage(Text.literal(dataPlayer.getPersistentData().toString()));
//        return 1;
//    }
//    private static int setInt(ServerPlayerEntity player, String key, int value) {
//        ((EntityDataInterface) player).getPersistentData().putInt(key, value);
//        return 1;
//    }
//
//    private static int setString(ServerPlayerEntity player, String key, String value) {
//        ((EntityDataInterface) player).getPersistentData().putString(key, value);
//        return 1;
//    }
}

