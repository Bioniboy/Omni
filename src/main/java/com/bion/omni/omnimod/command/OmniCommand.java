package com.bion.omni.omnimod.command;

import com.bion.omni.omnimod.element.*;
import com.bion.omni.omnimod.entity.ModEntities;
import com.bion.omni.omnimod.entity.custom.Pet;
import com.bion.omni.omnimod.item.InfluenceToken;
import com.bion.omni.omnimod.mixin.accessor.EntityAccessor;
import com.bion.omni.omnimod.util.AfkUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.bion.omni.omnimod.item.ModItems;
import com.bion.omni.omnimod.power.Mana;
import com.bion.omni.omnimod.util.EntityDataInterface;
import com.bion.omni.omnimod.util.Apprentice;
import eu.pb4.sgui.api.gui.SimpleGuiBuilder;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.RegistryEntryArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;


public class OmniCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess s, CommandManager.RegistrationEnvironment p) {
        dispatcher.register(CommandManager.literal("omni")
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
                        .then(CommandManager.literal("water")
                                .executes(context -> {choose(context.getSource().getPlayer(), "water"); return 1;})
                        )
                        .then(CommandManager.literal("magic")
                                .executes(context -> {choose(context.getSource().getPlayer(), "magic"); return 1;})
                        )
                        .then(CommandManager.literal("tech")
                                .executes(context -> {choose(context.getSource().getPlayer(), "tech"); return 1;})
                        )
                )
                .then(CommandManager.literal("playtime")
                        .executes(OmniCommand::playtime)
                        .then(CommandManager.literal("set")
                                .requires(source -> source.hasPermissionLevel(2))
                                .then(CommandManager.argument("time", IntegerArgumentType.integer(0))
                                        .executes(context -> {setPlaytime(context, IntegerArgumentType.getInteger(context, "time")); return 1;})
                                )

                        )
                        .then(CommandManager.literal("reset")
                                .requires(source -> source.hasPermissionLevel(2))
                                .executes(OmniCommand::resetPlaytime)
                        )
                )
                .then(CommandManager.literal("pet")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.literal("create")
                                .then(CommandManager.argument("owner", EntityArgumentType.player())
                                        .then(CommandManager.argument("name", StringArgumentType.word())
                                                .executes(context -> {
                                                    createPet(context, EntityArgumentType.getPlayer(context, "owner"), StringArgumentType.getString(context, "name")); return 1;})
                                                .then(CommandManager.argument("type", RegistryEntryArgumentType.registryEntry(s, RegistryKeys.ENTITY_TYPE)).suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                        .executes(context -> {
                                                            createPet(context, EntityArgumentType.getPlayer(context, "owner"), StringArgumentType.getString(context, "name"), RegistryEntryArgumentType.getSummonableEntityType(context, "type").value()); return 1;})
                                                )
                                        )
                                )
                        )
                        .then(CommandManager.literal("summon")
                                .requires(context -> ((((Apprentice)context.getPlayer()).omni$getPet() != null)))
                                .executes(OmniCommand::summonPet)
                        )
                )
                .then(CommandManager.literal("opmode")
                        .requires(source -> source.hasPermissionLevel(2))
                        .executes(OmniCommand::toggleOpMode)
                )
                .then(CommandManager.literal("mansion")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.literal("start")
                                .executes(OmniCommand::mansionStart)
                        )
                        .then(CommandManager.literal("stop")
                                .executes(context -> {mansionStop(context.getSource().getPlayer()); return 1;})
                        )
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
                if (!player.getInventory().contains(ModItems.AIR_WAND.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.AIR_WAND));
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
            case "water":
                if (!player.getInventory().contains(ModItems.HYDRONOMICON.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.HYDRONOMICON));
                }
                if (!player.getInventory().contains(ModItems.WATER_WAND.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.WATER_WAND));
                }
                starterPowerId = "waterBreathing";
                yield new Water();
            case "magic":
                if (!player.getInventory().contains(ModItems.THAUMONOMICON.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.THAUMONOMICON));
                }
                if (!player.getInventory().contains(ModItems.MAGIC_WAND.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.MAGIC_WAND));
                }
                starterPowerId = "potionCrafting";
                yield new Magic();
            case "tech":
                if (!player.getInventory().contains(ModItems.WRENCH.getDefaultStack())) {
                    player.giveItemStack(new ItemStack(ModItems.WRENCH));
                }
                starterPowerId = "divineRepo";
                ((Apprentice)player).omni$addPower("wrench");
                yield new Tech();
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
    private static void setPlaytime(CommandContext<ServerCommandSource> context, int time) {
        ((AfkUtil)context.getSource().getPlayer()).omni$setActiveTicks(time);
    }

    private static int resetPlaytime(CommandContext<ServerCommandSource> context) {
        AfkUtil player = ((AfkUtil)context.getSource().getPlayer());
        player.omni$setGotReward(false);
        player.omni$setActiveTicks(0);
//        player.omni$setNextStreakDay(1);
//        player.omni$setNextStreakYear(0);
        return 1;
    }

    private static void createPet(CommandContext<ServerCommandSource> context, ServerPlayerEntity player, String name, EntityType<?> type) {
        Pet pet = ModEntities.PET.create(player.getServerWorld());
        pet.setOwner(player);
        pet.setCustomName(Text.literal(name));
        if (type.create(context.getSource().getWorld()) instanceof LivingEntity) {
            pet.setCustomType((EntityType<? extends LivingEntity>) type);
        }
        ((Apprentice)player).omni$setPet(pet);
        pet.setPosition(player.getPos());
        player.getServerWorld().spawnEntity(pet);
        if (player.networkHandler != null) {
            player.server.getCommandManager().sendCommandTree(player);
        }
    }

    private static void createPet(CommandContext<ServerCommandSource> context, ServerPlayerEntity player, String name) {
        createPet(context, player, name, null);
    }

    private static int summonPet(CommandContext<ServerCommandSource> context) {
        Pet pet = ((Apprentice)context.getSource().getPlayer()).omni$getPet();
        if (!pet.isAlive() && ((Apprentice) context.getSource().getPlayer()).omni$getPetCooldown() == 0) {
            pet.unRemove();
            pet.setHealth(pet.defaultMaxHealth);
            pet.setPosition(context.getSource().getPosition());
            context.getSource().getWorld().spawnEntity(((Apprentice) context.getSource().getPlayer()).omni$getPet());
        } else if (((Apprentice) context.getSource().getPlayer()).omni$getPetCooldown() > 0) {
            int remainingTicks = ((Apprentice) context.getSource().getPlayer()).omni$getPetCooldown() + 20;
            int remainingMinutes = (remainingTicks / (20 * 60));
            int remainingSeconds = (remainingTicks / 20) - (remainingMinutes * 60);
            context.getSource().sendError(pet.getName().copyContentOnly()
                    .append(Text.literal(" is still resting. They can be summoned again in " + (remainingMinutes != 0 ? remainingMinutes + " minutes and ": "") + remainingSeconds + " seconds")));
        }
        return 1;
    }

    private static int toggleOpMode(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        Apprentice apprentice = (Apprentice) player;
        if (player == null) return 0;
        if (apprentice.omni$getOpModeOtherPos() != null) {
            World otherWorld = apprentice.omni$getOpModeOtherWorld();
            Vec3d otherPos = apprentice.omni$getOpModeOtherPos();
            apprentice.omni$setOpModeOtherPos(player.getPos(), player.getServerWorld());
            player.teleport((ServerWorld) otherWorld, otherPos.x, otherPos.y, otherPos.z, player.getYaw(), player.getPitch());
            player.refreshPositionAfterTeleport(player.getX(), player.getY(), player.getZ());

        } else {
            apprentice.omni$setOpModeOtherPos(player.getPos(), player.getServerWorld());
        }
        if (apprentice.omni$getSavedInventory("opMode") == null)
            apprentice.omni$addSavedInventory("opMode", new PlayerInventory(player));
        NbtList savedInv = player.getInventory().writeNbt(new NbtList());
        player.getInventory().readNbt(apprentice.omni$getSavedInventory("opMode").writeNbt(new NbtList()));
        apprentice.omni$getSavedInventory("opMode").readNbt(savedInv);

        if (!apprentice.omni$inOpMode()) {
            player.changeGameMode(GameMode.CREATIVE);
        } else {
            player.changeGameMode(GameMode.SURVIVAL);
        }

        apprentice.omni$setInOpMode(!apprentice.omni$inOpMode());
        return 1;
    }
    private static int mansionStart(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        Apprentice apprentice = (Apprentice) player;
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, -1, 0, true, false));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, -1, 3, true, false));
        apprentice.omni$addSavedInventory("mansion", new PlayerInventory(player));
        apprentice.omni$getSavedInventory("mansion").readNbt(player.getInventory().writeNbt(new NbtList()));
        player.getInventory().clear();
        player.networkHandler.sendPacket(new TitleS2CPacket(Text.literal("You have entered").formatted(Formatting.DARK_RED).formatted(Formatting.BOLD)));
        player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.PLAYERS, 1.0f, 0.5f);
        apprentice.omni$setInMansion(true);
        player.changeGameMode(GameMode.ADVENTURE);
        player.getServer().getCommandManager().executeWithPrefix(player.getServer().getCommandSource(), "data merge entity 115b67a6-f426-4093-952c-561691e95df7 {Item:{id:\"minecraft:carrot_on_a_stick\",tag:{Damage:0},Count:1b}}");
        player.getServer().getCommandManager().executeWithPrefix(player.getServer().getCommandSource(), "data merge entity 51761ee8-fc92-4ca0-a7c4-d0a6e29e22f3 {ArmorItems:[{id:\"minecraft:leather_boots\",tag:{Damage:0},Count:1b},{id:\"minecraft:leather_leggings\",tag:{Damage:0}, Count:1b},{id:\"minecraft:leather_chestplate\",tag:{Damage:0}, Count:1b}]}");
        player.getServer().getCommandManager().executeWithPrefix(player.getServer().getCommandSource(), "data merge entity 5c1c1c49-3ba8-48d7-a9b9-6e766faba3ed {ArmorItems:[{},{},{},{id:\"minecraft:leather_helmet\",tag:{Damage:0}, Count:1b}]}");
        player.getServer().getCommandManager().executeWithPrefix(player.getServer().getCommandSource(), "summon omnimod:mansion_zombie -492 122 244 {IsBaby:0b}");
        player.getServer().getCommandManager().executeWithPrefix(player.getServer().getCommandSource(), "kill @e[type=item,x=-536,y=110,z=210,dx=58,dy=32,dz=44]");
        player.getServer().getCommandManager().executeWithPrefix(player.getServer().getCommandSource(), "tp @e[type=minecraft:chest_boat,x=-538,y=110,z=210,dx=58,dy=32,dz=44] -524 112 258");
        player.getServer().getCommandManager().executeWithPrefix(player.getServer().getCommandSource(), "tp @e[type=minecraft:boat,x=-538,y=110,z=210,dx=58,dy=32,dz=44] -524 112 258");
        player.getServer().getCommandManager().executeWithPrefix(player.getServer().getCommandSource(), "tp @e[type=minecraft:chest_minecart,x=-538,y=110,z=210,dx=58,dy=32,dz=44] -524 112 258");
        player.getServer().getCommandManager().executeWithPrefix(player.getServer().getCommandSource(), "tp @e[type=minecraft:donkey,x=-538,y=110,z=210,dx=58,dy=32,dz=44] -524 112 258");
        player.getServer().getCommandManager().executeWithPrefix(player.getServer().getCommandSource(), "tp @e[type=minecraft:mule,x=-538,y=110,z=210,dx=58,dy=32,dz=44] -524 112 258");

        return 1;
    }

    public static void mansionStop(ServerPlayerEntity player) {
        int totalInfluence = 0;
        for (ItemStack item : player.getInventory().main) {
            if (item.isOf(ModItems.INFLUENCE_TOKEN)) {
                totalInfluence += item.getOrCreateNbt().getInt("Value") * item.getCount();
            }
        }
        if (player.getOffHandStack().isOf(ModItems.INFLUENCE_TOKEN)) {
            totalInfluence += player.getOffHandStack().getOrCreateNbt().getInt("Value") * player.getOffHandStack().getCount();
        }
        Scoreboard scoreboard = player.getScoreboard();
        ScoreboardPlayerScore score = scoreboard.getPlayerScore(player.getName().getString(), scoreboard.getObjective("MansionPoints"));
        player.sendMessageToClient(Text.literal("Points earned: " + totalInfluence), false);
        if (score.getScore() < totalInfluence) {
            score.setScore(totalInfluence);
            player.sendMessageToClient(Text.literal("New high score"), false);
        }
        player.getInventory().readNbt(((Apprentice)player).omni$removeSavedInventory("mansion").writeNbt(new NbtList()));
        player.removeStatusEffect(StatusEffects.DARKNESS);
        player.removeStatusEffect(StatusEffects.REGENERATION);
        player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WITHER_DEATH, SoundCategory.PLAYERS, 1.0f, 0.5f);
        ((Apprentice)player).omni$setInMansion(false);
        player.changeGameMode(GameMode.SURVIVAL);
        player.getServer().getCommandManager().executeWithPrefix(player.getServer().getCommandSource(), "clone -536 40 210 -478 72 254 -536 110 210");
        player.getServer().getCommandManager().executeWithPrefix(player.getServer().getCommandSource(), "tp @e[type=omnimod:mansion_zombie] ~ -200 ~");
        player.getServer().getCommandManager().executeWithPrefix(player.getServer().getCommandSource(), "tag @a[tag=mansion] remove mansion");
    }
}
