package com.bion.omni.omnimod.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModCommands {
    public static void registerCommands() {
        //CommandRegistrationCallback.EVENT.register(SetHomeCommand::register);
        //CommandRegistrationCallback.EVENT.register(ReturnHomeCommand::register);
        //CommandRegistrationCallback.EVENT.register(DeleteHomeCommand::register);
        CommandRegistrationCallback.EVENT.register(OmniCommand::register);
        CommandRegistrationCallback.EVENT.register(ManaCommand::register);
        //CommandRegistrationCallback.EVENT.register(OmniDataCommand::register);
        //CommandRegistrationCallback.EVENT.register(PowersCommand::register);
        CommandRegistrationCallback.EVENT.register(InfluenceCommand::register);
        CommandRegistrationCallback.EVENT.register(TrollSoundCommand::register);
        CommandRegistrationCallback.EVENT.register(OpenBackpackCommand::register);
    }
}
