package com.bion.omni.omnimod.power.earth;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.entity.ModEntities;
import com.bion.omni.omnimod.entity.custom.MetalGlow;
import com.bion.omni.omnimod.power.ContinuousPower;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.awt.*;

public class MetalDetector extends ContinuousPower {
    public MetalDetector(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Metal Detector";
    }

    @Override
    public String getId() {
        return "metalDetector";
    }

    @Override
    public String getAdvancementId() {
        return "metal_detector";
    }

    @Override
    public Integer getInfluenceCost() {
        return 10;
    }

    @Override
    public String getPreRequisiteId() {
        return "haste";
    }

    @Override
    public double getManaCost() {
        return 2;
    }

    @Override
    public Boolean hasConfig() {
        return true;
    }

    int tickcounter = 0;

    String[][] listOfOres = {
            {"redstone"},
            {"diamond"},
            {"emerald"},
            {"iron"},
            {"lapis"},
            {"copper"},
            {"coal"},
            {"gold"},
            {"quartz"},
            {"debris"}
    };
public static void removeTeam(MinecraftServer server, String teamName) {
    // Get the main world scoreboard
    Scoreboard scoreboard = server.getScoreboard();

    // Get the team by name
    Team team = scoreboard.getTeam(teamName);

    // Check if the team exists
    if (team != null) {
        // Remove the team from the scoreboard
        scoreboard.removeTeam(team);
        //OmniMod.LOGGER.info(teamName + " has been removed.");
    } else {
        //OmniMod.LOGGER.info(teamName + " does not exist.");
    }
}


    @Override
    public void stop(ServerPlayerEntity user){
        MinecraftServer server = user.getServer();
        tickcounter = 0;
        isActive = false;
        assert server != null;
        for (int i = 0; i < 10; i++) {
            removeTeam(server, listOfOres[i][0]);
        }
        killAnyGlows(user.getServerWorld(), user);
    }

    public static boolean isOccupied(ServerWorld world, BlockPos blockPos) {
        Box boundingBox = new Box(blockPos);
        //OmniMod.LOGGER.info(String.valueOf(boundingBox));
        if(world.getOtherEntities(null, boundingBox, entity -> entity instanceof MetalGlow).isEmpty()){

            return false;
        }
        else{
            world.getOtherEntities(null, boundingBox, entity -> entity instanceof MetalGlow).forEach(entity -> {
                if (entity instanceof MetalGlow metalGlow) {
                    metalGlow.setLifeduration(0);
                    //OmniMod.LOGGER.info("refreshed glow");
                }
            });
            return true;
        }
    }

    public static void killAnyGlows(ServerWorld world, ServerPlayerEntity user) {
        double radius = 10;
        BlockPos playerPos = user.getBlockPos();
        // Calculate the coordinates for the bounding box
        double minX = playerPos.getX() - radius;
        double minY = playerPos.getY() - radius;
        double minZ = playerPos.getZ() - radius;
        double maxX = playerPos.getX() + radius;
        double maxY = playerPos.getY() + radius;
        double maxZ = playerPos.getZ() + radius;

        // Create the bounding box
        Box boundingBox = new Box(minX, minY, minZ, maxX, maxY, maxZ);
        //this counts the entities found/discarded
        //OmniMod.LOGGER.info(String.valueOf(world.getOtherEntities(null, boundingBox, entity -> entity instanceof MetalGlow).size()));
        world.getOtherEntities(null, boundingBox, entity -> entity instanceof MetalGlow).forEach(Entity::discard);
    }

    public void spawnGlow(ServerPlayerEntity user, BlockPos pos, String whichore, String color){
        MetalGlow glow = ModEntities.METAL_GLOW.create(user.getServerWorld());
        assert glow != null;
        ServerWorld serverWorld = user.getServerWorld();
        World world = user.getWorld();
        glow.setPosition(Vec3d.of(pos));
        glow.setGlowing(true);
        glow.addEntityToTeam(serverWorld, glow, whichore, color);
        world.spawnEntity(glow);

    }

    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        isActive = true;
        ServerWorld serverWorld = user.getServerWorld();
        World world = user.getWorld();
        int radius = 5;
        MetalGlow glow = ModEntities.METAL_GLOW.create(user.getServerWorld());
        assert glow != null;
        int radiusSquared = radius * radius;
        if (tickcounter == 0) {
            tickcounter = 20;
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        if (x * x + y * y + z * z <= radiusSquared) {
                            BlockPos pos = user.getBlockPos().add(x, y, z);
                            Block block = world.getBlockState(pos).getBlock();
                            if (!isOccupied(serverWorld, pos)) {
                                //i could prolly make this smaller with an array, but it gave me too much grief to get the privilege of efficiency
                                if (block == Blocks.REDSTONE_ORE) {
                                    spawnGlow(user, pos, "redstone", "DARK_BLUE");//dont question the colors
                                } else if (block == Blocks.DEEPSLATE_REDSTONE_ORE) {
                                    spawnGlow(user, pos, "redstone", "DARK_BLUE");
                                } else if (block == Blocks.DIAMOND_ORE) {
                                    spawnGlow(user, pos, "diamond", "YELLOW");
                                } else if (block == Blocks.DEEPSLATE_DIAMOND_ORE) {
                                    spawnGlow(user, pos, "diamond", "YELLOW");
                                } else if (block == Blocks.DEEPSLATE_EMERALD_ORE) {
                                    spawnGlow(user, pos, "emerald", "GREEN");
                                } else if (block == Blocks.EMERALD_ORE) {
                                    spawnGlow(user, pos, "emerald", "GREEN");
                                } else if (block == Blocks.DEEPSLATE_IRON_ORE) {
                                    spawnGlow(user, pos, "iron", "GRAY");
                                } else if (block == Blocks.IRON_ORE) {
                                    spawnGlow(user, pos, "iron", "GRAY");
                                } else if (block == Blocks.DEEPSLATE_LAPIS_ORE) {
                                    spawnGlow(user, pos, "lapis", "DARK_RED");
                                } else if (block == Blocks.LAPIS_ORE) {
                                    spawnGlow(user, pos, "lapis", "DARK_RED");
                                } else if (block == Blocks.DEEPSLATE_COPPER_ORE) {
                                    spawnGlow(user, pos, "copper", "DARK_AQUA");
                                } else if (block == Blocks.COPPER_ORE) {
                                    spawnGlow(user, pos, "copper", "DARK_AQUA");
                                } else if (block == Blocks.DEEPSLATE_COAL_ORE) {
                                    spawnGlow(user, pos, "coal", "BLACK");
                                } else if (block == Blocks.COAL_ORE) {
                                    spawnGlow(user, pos, "coal", "BLACK");
                                } else if (block == Blocks.DEEPSLATE_GOLD_ORE) {
                                    spawnGlow(user, pos, "gold", "AQUA");
                                } else if (block == Blocks.GOLD_ORE) {
                                    spawnGlow(user, pos, "gold", "AQUA");
                                } else if (block == Blocks.NETHER_GOLD_ORE) {
                                    spawnGlow(user, pos, "gold", "AQUA");
                                } else if (block == Blocks.NETHER_QUARTZ_ORE) {
                                    spawnGlow(user, pos, "quartz", "WHITE");
                                } else if (block == Blocks.ANCIENT_DEBRIS) {
                                    spawnGlow(user, pos, "debris", "DARK_PURPLE");
                                }
                            }
                        }
                    }
                }
            }
        } else tickcounter--;
    }
}
