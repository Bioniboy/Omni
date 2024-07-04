package com.bion.omni.omnimod.power.moon;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.block.ModBlocks;
import com.bion.omni.omnimod.util.TempBlock;
import com.bion.omni.omnimod.power.ImpulsePower;
import net.minecraft.block.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class DarkAura extends ImpulsePower {
    ArrayList<TempBlock> blockList = new ArrayList<TempBlock>();

    public DarkAura(int level) {
        super(level);
    }


    @Override
    public String getName() {
        return "Dark Aura " + getRomanNumeral(this.getLevel());
    }
    @Override
    public Integer getMaxLevel() {
        return 3;
    }

    @Override
    public String getId() {
        return "darkAura";
    }
    @Override
    public String getAdvancementId() {
        return "dark_aura" + (getLevel() == 1 ? "" : "_" + getLevel());
    }
    @Override
    public double getManaCost() {
        return 50;
    }
    @Override
    public Integer getInfluenceCost() {
        return switch (this.getLevel()) {
            case 1:
                yield 50;
            case 2:
                yield 30;
            case 3:
                yield 30;
            default:
                yield 0;
        };
    }
    @Override
    public Boolean hasConfig() {
        return true;
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        if (super.activate(user)) {
            int tickCounter = 0;
            int range = 0;
            switch (getLevel()) {
                case 1:
                    range = 16;
                    tickCounter = 200;
                    break;
                case 2:
                    range = 32;
                    tickCounter = 200;
                    break;
                case 3:
                    range = 32;
                    tickCounter = 400;
            }
            for (BlockPos blockPos : BlockPos.iterateOutwards(user.getBlockPos(), range, range, range)) {
                if (blockPos.getSquaredDistance(user.getBlockPos()) <= (range * range) && user.getWorld().getBlockState(blockPos).getLuminance() > 4) {
                    BlockState tempState;
                    BlockState prevState = user.getWorld().getBlockState(blockPos);
                    if (prevState.isFullCube(user.getWorld(), blockPos)) {
                        tempState = ModBlocks.DARK_BLOCK.getDefaultState();
                    } else if (prevState.getBlock() instanceof WallTorchBlock) {
                        OmniMod.LOGGER.info("Found wall torch");
                        OmniMod.LOGGER.info("Facing " + prevState.get(WallTorchBlock.FACING));
                        tempState = ModBlocks.BURNT_WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, prevState.get(WallTorchBlock.FACING));
                    } else if (prevState.getBlock() instanceof TorchBlock) {
                        tempState = ModBlocks.BURNT_TORCH.getDefaultState();
                    } else if (prevState.getBlock() instanceof CaveVines) {
                        tempState = Blocks.CAVE_VINES.getDefaultState();
                    } else {
                        tempState = ModBlocks.SMALL_DARK_BLOCK.getDefaultState();
                    }
                    user.getServerWorld().spawnParticles(user, ParticleTypes.SMOKE, true, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 5,0.2, 0, 0.2, 0);
                    blockList.add(new TempBlock(user.getServerWorld(), blockPos, tempState, tickCounter));
                }
            }

            //user.getWorld().setBlockState(user.getBlockPos(), Block.getStateFromRawId(0));

            return true;
        }
        return false;
    }

    @Override
    public boolean isTicking() {
        return blockList.size() > 0;
    }

    @Override
    public void tick(ServerPlayerEntity user) {
        blockList.removeIf(block -> !block.tick());
    }
    //    @Override
//    public void end(ServerPlayerEntity user) {
//        user.getWorld().setBlockState(blockPos, Block.postProcessState(Block.getStateFromRawId(block), user.getWorld(), user.getBlockPos()), Block.NOTIFY_LISTENERS);
//    }

    @Override
    public NbtCompound toNbt(RegistryWrapper.WrapperLookup registries) {
        for (TempBlock block : blockList) {
            block.end();
        }
        return super.toNbt(registries);
    }
}
