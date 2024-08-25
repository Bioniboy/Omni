package com.bion.omni.omnimod.entity.custom;

import com.bion.omni.omnimod.OmniMod;
import eu.pb4.polymer.core.api.entity.PolymerEntity;
import eu.pb4.polymer.virtualentity.api.tracker.DisplayTrackedData;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.joml.Vector3f;


import java.util.List;


public class MetalGlow extends DisplayEntity.BlockDisplayEntity implements PolymerEntity {
    public MetalGlow(EntityType<? extends DisplayEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public EntityType<?> getPolymerEntityType(ServerPlayerEntity player) {
        return EntityType.BLOCK_DISPLAY;
    }

    public void addEntityToTeam(ServerWorld world, DisplayEntity.BlockDisplayEntity entity, String whichore, String colorofblock) {
        MinecraftServer server = world.getServer();
        ServerScoreboard scoreboard = server.getScoreboard();

        // Ensure the team exists
        if (server.getScoreboard().getTeam(whichore) == null) {
            server.getScoreboard().addTeam(whichore);
            server.getScoreboard().getTeam(whichore).setColor(Formatting.byName(colorofblock));
        }
        // Add the entity to the team
        Team team = scoreboard.getTeam(whichore);
        scoreboard.addScoreHolderToTeam(entity.getUuidAsString(), team);
        //OmniMod.LOGGER.info(colorofblock);
    }

    @Override
    public void modifyRawTrackedData(List<DataTracker.SerializedEntry<?>> data, ServerPlayerEntity player, boolean initial) {
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.Block.BLOCK_STATE, Blocks.WHITE_STAINED_GLASS.getDefaultState()));
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.TELEPORTATION_DURATION, 4));
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.SCALE, new Vector3f(1F)));
    }

    @Override
    protected void refreshData(boolean shouldLerp, float lerpProgress) {
    }

    public void setLifeduration(int lifeduration) {
        this.lifeduration = lifeduration;
    }

    int lifeduration =0;
    //now there wont be thousands of blocks lying around
    @Override
    public void tick(){
        lifeduration++;
        World world = this.getWorld();
        BlockPos blockpos = this.getBlockPos();
        Block block = world.getBlockState(blockpos).getBlock();
        if(block == Blocks.AIR)
            discard();
        if(lifeduration>21){
            discard();
        }
    }

}
