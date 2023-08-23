package com.bion.omni.omnimod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.bion.omni.omnimod.util.EntityDataInterface;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ReturnHomeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess s, CommandManager.RegistrationEnvironment p) {
        dispatcher.register(CommandManager.literal("home")
                .then(CommandManager.literal("return")
                        .requires(ReturnHomeCommand::condition)
                        .executes(ReturnHomeCommand::run)));
    }

    private static boolean condition(ServerCommandSource context) {
        EntityDataInterface player = (EntityDataInterface) context.getPlayer();

        return player.getPersistentData().getIntArray("homepos").length > 0;

    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        EntityDataInterface player = (EntityDataInterface) context.getSource().getPlayer();
        int[] homepos = player.getPersistentData().getIntArray("homepos");

        if (homepos.length != 0) {
            context.getSource().getPlayer().requestTeleport(homepos[0], homepos[1], homepos[2]);
            context.getSource().sendFeedback(() -> Text.literal("Teleported home"), false);

        } else {
            context.getSource().sendError(Text.literal("No home set"));
        }
        return 1;
    }

}
