package com.bion.omni.omnimod.block;

import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BurntTorch extends TorchBlock implements PolymerBlock {
    public BurntTorch(Settings settings) {
        super(settings, null);
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.REDSTONE_TORCH;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return Blocks.REDSTONE_TORCH.getDefaultState()
                .with(Properties.LIT, false);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
    }
}
