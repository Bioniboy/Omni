package com.bion.omni.omnimod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.bion.omni.omnimod.util.EntityDataInterface;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class SetHomeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess s, CommandManager.RegistrationEnvironment p) {
        dispatcher.register(CommandManager.literal("home")
                .then(CommandManager.literal("set").executes(SetHomeCommand::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        EntityDataInterface player = (EntityDataInterface) context.getSource().getPlayer();
        BlockPos playerPos = context.getSource().getPlayer().getBlockPos();

        player.getPersistentData().putIntArray("homepos",
                new int[] {playerPos.getX(), playerPos.getY(), playerPos.getZ()});

        context.getSource().sendFeedback(() -> Text.literal("Set home"), false);
        context.getSource().getServer().getCommandManager().sendCommandTree(context.getSource().getPlayer());
        return 1;
    }

}
