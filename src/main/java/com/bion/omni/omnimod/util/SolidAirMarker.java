package com.bion.omni.omnimod.util;

import com.bion.omni.omnimod.block.ModBlocks;
import com.bion.omni.omnimod.elements.Air;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MarkerEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class SolidAirMarker {
    int ticksLeft;
    MarkerEntity position;
    public SolidAirMarker(ServerWorld world, BlockPos pos, int ticksLeft) {
        this.ticksLeft = ticksLeft;
        position = EntityType.MARKER.spawn(world, pos, SpawnReason.NATURAL);
        if (world.getBlockState(pos).getBlock() == Blocks.AIR) {
            world.setBlockState(pos, ModBlocks.SOLID_AIR.getDefaultState());
        }
    }
    public boolean tick() {
        if (ticksLeft > 0 && position.getWorld().getBlockState(position.getBlockPos()).getBlock() == ModBlocks.SOLID_AIR) {
            Box box = new Box(position.getBlockPos()).expand(64);

            if (position.getWorld() instanceof ServerWorld serverWorld) {
                for (var entity : position.getWorld().getOtherEntities(null, box)) {
                    if (entity instanceof ServerPlayerEntity player && ((Apprentice)player).omni$getElement() instanceof Air) {
                        serverWorld.spawnParticles(player, ParticleTypes.CLOUD, true, position.getX(), position.getY() + 0.5, position.getZ(), 1, 0.3, 0.3, 0.3, 0);
                    }
                }

            }
            ticksLeft -= 1;
            return true;
        } else {
            end();
            return false;
        }
    }

    public void end() {
        if (position.getWorld().getBlockState(position.getBlockPos()).getBlock() == ModBlocks.SOLID_AIR) {
            position.getWorld().setBlockState(position.getBlockPos(), Block.postProcessState(Blocks.AIR.getDefaultState(), position.getWorld(), position.getBlockPos()), Block.NOTIFY_LISTENERS);
            position.discard();
        }
    }
}
