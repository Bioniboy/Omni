package com.bion.omni.omnimod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.bion.omni.omnimod.util.EntityDataInterface;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class DeleteHomeCommand {
//    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess s, CommandManager.RegistrationEnvironment p) {
//        dispatcher.register(CommandManager.literal("home")
//                .then(CommandManager.literal("delete")
//                        .requires(DeleteHomeCommand::condition)
//                        .executes(DeleteHomeCommand::run)));
//
//    }
//
//    private static boolean condition(ServerCommandSource context) {
//        EntityDataInterface player = (EntityDataInterface) context.getPlayer();
//        return player.getPersistentData().getIntArray("homepos").length > 0;
//    }
//
//    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
//        EntityDataInterface player = (EntityDataInterface) context.getSource().getPlayer();
//        if (player.getPersistentData().contains("homepos")) {
//            player.getPersistentData().remove("homepos");
//            context.getSource().sendFeedback(() -> Text.literal("Deleted home"), false);
//        } else {
//            context.getSource().sendError(Text.literal("No home set"));
//        }
//        context.getSource().getServer().getCommandManager().sendCommandTree(context.getSource().getPlayer());
//        return 1;
//    }

}
