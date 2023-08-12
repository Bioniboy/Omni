package com.bion.omni.omnimod.command;

import com.bion.omni.omnimod.elements.Air;
import com.bion.omni.omnimod.elements.Clarity;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.bion.omni.omnimod.elements.Moon;
import com.bion.omni.omnimod.elements.Storm;
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
import net.minecraft.util.math.Box;

import java.util.ArrayList;
import java.util.List;


public class OmniCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess s, CommandManager.RegistrationEnvironment p) {
        dispatcher.register(CommandManager.literal("omni")
                .then(CommandManager.literal("glow")
                        .requires(context -> {return ((EntityDataInterface)context.getPlayer()).getPersistentData().getCompound("powers").contains("glow");})
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
                )
        );
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        Box boxAroundPlayer = new Box(player.getBlockPos()).expand(10, 10, 10);
        List<Entity> entities = context.getSource().getWorld().getOtherEntities(null, boxAroundPlayer);
        ((Apprentice) player).getCosts().put("glow", 20.0);
        for (Entity entity : entities) {
            EntityDataInterface dataEntity = (EntityDataInterface) entity;
            TrackedData<Byte> FLAGS = dataEntity.getFlags();
            List<DataTracker.SerializedEntry<?>> entries = new ArrayList<>();
            entries.add(DataTracker.SerializedEntry.of(FLAGS, (byte)(entity.getDataTracker().get(FLAGS) | 1 << 6)));
            var packet = new EntityTrackerUpdateS2CPacket(entity.getId(), entries);

            player.networkHandler.sendPacket(packet);
        }
        return 1;
    }
    private static void choose(PlayerEntity player, String element) {
        ((Apprentice)player).clearPowers();
        NbtCompound omniData = ((EntityDataInterface) player).getPersistentData();
        while (!omniData.isEmpty()) {
            String[] dataList = omniData.toString().split(":", 2);
            omniData.remove(dataList[0].substring(1));
        }
        ((EntityDataInterface)player).getPersistentData().putString("1RightClick", "");
        String starterPowerId = "";
        ((Apprentice)player).setElement(switch (element) {
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
                if (!player.getInventory().contains(ModItems.STORM_WAND.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.STORM_WAND));
                }
                starterPowerId = "astralProject";
                yield new Clarity();
            default:
                yield null;
        });
        if (!starterPowerId.equals("")) {
            ((Apprentice)player).addPower(starterPowerId);
        }
        Mana.manaInitialize((Apprentice) player);
    }
}
//((PropertyDelegate)lectern).set(0, 2);