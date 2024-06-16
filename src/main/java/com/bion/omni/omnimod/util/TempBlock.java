package com.bion.omni.omnimod.util;

import com.bion.omni.omnimod.OmniMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MarkerEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;

public class TempBlock {
    int blockType;
    int ticksLeft;
    NbtCompound blockEntity = null;
    MarkerEntity position;
    public TempBlock(ServerWorld world, BlockPos pos, BlockState tempState, int ticks) {
        ticksLeft = ticks;
        position = EntityType.MARKER.spawn(world, pos, SpawnReason.NATURAL);
        blockType = Block.getRawIdFromState(world.getBlockState(pos));
        if (world.getBlockState(pos).hasBlockEntity()) {
            blockEntity = world.getBlockEntity(pos).createNbt(world.getRegistryManager());
            Clearable.clear(world.getBlockEntity(pos));
        }
        world.setBlockState(pos, tempState);
    }
//    public void start(int ticksLeft) {
//        dataTracker.set(BLOCK_TYPE, Block.getRawIdFromState(getWorld().getBlockState(getBlockPos())));
//        dataTracker.set(TICKS_LEFT, ticksLeft);
//        getWorld().setBlockState(getBlockPos(), Block.getStateFromRawId(0));
//
//    }
    public boolean tick() {
        if (ticksLeft > 0) {
            ticksLeft -= 1;
            return true;
        } else {
            end();
            return false;
        }
    }
    public void end() {
        position.getWorld().setBlockState(position.getBlockPos(), Block.postProcessState(Block.getStateFromRawId(blockType), position.getWorld(), position.getBlockPos()), Block.NOTIFY_LISTENERS);
        if (blockEntity != null) {
            position.getWorld().getBlockEntity(position.getBlockPos()).read(blockEntity, position.getWorld().getRegistryManager());
        }
        position.discard();
    }
}
