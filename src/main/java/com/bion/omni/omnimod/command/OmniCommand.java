package com.bion.omni.omnimod.command;

import com.bion.omni.omnimod.elements.*;
import com.bion.omni.omnimod.mixin.accessor.EntityAccessor;
import com.bion.omni.omnimod.util.AfkUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.bion.omni.omnimod.item.ModItems;
import com.bion.omni.omnimod.powers.Mana;
import com.bion.omni.omnimod.util.EntityDataInterface;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;

import java.util.ArrayList;
import java.util.List;


public class OmniCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess s, CommandManager.RegistrationEnvironment p) {
        dispatcher.register(CommandManager.literal("omni")
                .then(CommandManager.literal("glow")
                        .requires(context -> ((EntityDataInterface)context.getPlayer()).getPersistentData().getCompound("powers").contains("glow"))
                        .executes(OmniCommand::run)
                )
                .then(CommandManager.literal("choose")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.literal("moon")
                                .executes(context -> {choose(context.getSource().getPlayer(), "moon"); return 1;})
                        )
                        .then(CommandManager.literal("storm")
                                .executes(context -> {choose(context.getSource().getPlayer(), "storm"); return 1;})
                        )
                        .then(CommandManager.literal("air")
                                .executes(context -> {choose(context.getSource().getPlayer(), "air"); return 1;})
                        )
                        .then(CommandManager.literal("clarity")
                                .executes(context -> {choose(context.getSource().getPlayer(), "clarity"); return 1;})
                        )
                        .then(CommandManager.literal("life")
                                .executes(context -> {choose(context.getSource().getPlayer(), "life"); return 1;})
                        )
                        .then(CommandManager.literal("fire")
                                .executes(context -> {choose(context.getSource().getPlayer(), "fire"); return 1;})
                        )
                )
                .then(CommandManager.literal("playtime")
                        .executes(OmniCommand::playtime)
                )
        );
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        Box boxAroundPlayer = new Box(player.getBlockPos()).expand(10, 10, 10);
        List<Entity> entities = context.getSource().getWorld().getOtherEntities(null, boxAroundPlayer);
        ((Apprentice) player).omni$getCosts().put("glow", 20.0);
        for (Entity entity : entities) {
            List<DataTracker.SerializedEntry<?>> entries = new ArrayList<>();
            entries.add(DataTracker.SerializedEntry.of(EntityAccessor.getFLAGS(), (byte)(entity.getDataTracker().get(EntityAccessor.getFLAGS()) | 1 << 6)));
            var packet = new EntityTrackerUpdateS2CPacket(entity.getId(), entries);

            player.networkHandler.sendPacket(packet);
        }
        return 1;
    }
    private static void choose(PlayerEntity player, String element) {
        ((Apprentice)player).omni$clearPowers();
        NbtCompound omniData = ((EntityDataInterface) player).getPersistentData();
        while (!omniData.isEmpty()) {
            String[] dataList = omniData.toString().split(":", 2);
            omniData.remove(dataList[0].substring(1));
        }
        ((EntityDataInterface)player).getPersistentData().putString("1RightClick", "");
        String starterPowerId = "";
        ((Apprentice)player).omni$setElement(switch (element) {
            case "moon":
                if (!player.getInventory().contains(ModItems.NOCTONOMICON.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.NOCTONOMICON));
                }
                if (!player.getInventory().contains(ModItems.MOON_WAND.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.MOON_WAND));
                }
                starterPowerId = "invisibility";
                yield new Moon();
            case "storm":
                if (!player.getInventory().contains(ModItems.CYCLONOMICON.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.CYCLONOMICON));
                }
                if (!player.getInventory().contains(ModItems.STORM_WAND.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.STORM_WAND));
                }
                starterPowerId = "speed";
                yield new Storm();
            case "air":
                if (!player.getInventory().contains(ModItems.AERONOMICON.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.AERONOMICON));
                }
                if (!player.getInventory().contains(ModItems.STORM_WAND.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.STORM_WAND));
                }
                starterPowerId = "slowFall";
                yield new Air();
            case "clarity":
                if (!player.getInventory().contains(ModItems.LIBRONOMICON.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.LIBRONOMICON));
                }
                if (!player.getInventory().contains(ModItems.CLARITY_WAND.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.CLARITY_WAND));
                }
                starterPowerId = "astralProject";
                yield new Clarity();
            case "life":
                if (!player.getInventory().contains(ModItems.VITANOMICON.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.VITANOMICON));
                }
                if (!player.getInventory().contains(ModItems.LIFE_WAND.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.LIFE_WAND));
                }
                starterPowerId = "photosynthesis";
                yield new Life();
            case "fire":
                if (!player.getInventory().contains(ModItems.PYRONOMICON.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.PYRONOMICON));
                }
                if (!player.getInventory().contains(ModItems.FIRE_WAND.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.FIRE_WAND));
                }
                starterPowerId = "fireResistance";
                yield new Fire();
            default:
                yield null;
        });
        if (!starterPowerId.equals("")) {
            ((Apprentice)player).omni$addPower(starterPowerId);
        }
        Mana.manaInitialize((Apprentice) player);
    }


    private static int playtime(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        int activeTicks = ((AfkUtil)context.getSource().getPlayer()).omni$getActiveTicks();
        int hours = activeTicks / (20 * 60 * 60);
        int minutes = (activeTicks / (20 * 60)) - (hours * 60);
        int seconds = (activeTicks / 20) - (minutes * 60);

        if (activeTicks < 72000) {
            context.getSource().sendFeedback(() -> Text.literal("You have been playing for " + (hours != 0 ? hours + " hours, ": "") + (minutes != 0 ? minutes + " minutes and ": "") + seconds + " seconds today."), false);
            int remainingTicks = 72000 - activeTicks + 20;
            int remainingHours = remainingTicks / (20 * 60 * 60);
            int remainingMinutes = (remainingTicks / (20 * 60)) - (remainingHours * 60);
            int remainingSeconds = (remainingTicks / 20) - (remainingMinutes * 60);
            context.getSource().sendFeedback(() -> Text.literal("You need to play for " + (remainingHours != 0 ? remainingHours + " hours, ": "") + (remainingMinutes != 0 ? remainingMinutes + " minutes and ": "") + remainingSeconds + " seconds to get your Influence reward."), false);
        } else {
            context.getSource().sendFeedback(() -> Text.literal("You got your Influence reward for today!"), false);
        }
        return 1;
    }
}
