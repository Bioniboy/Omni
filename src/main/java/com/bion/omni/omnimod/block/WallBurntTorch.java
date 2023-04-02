package com.bion.omni.omnimod.block;

import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.minecraft.block.*;
import net.minecraft.state.property.Properties;

public class WallBurntTorch extends WallTorchBlock implements PolymerBlock {
    public WallBurntTorch(Settings settings) {
        super(settings, null);
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.REDSTONE_WALL_TORCH;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return Blocks.REDSTONE_WALL_TORCH.getDefaultState()
                .with(Properties.LIT, false)
                .with(WallRedstoneTorchBlock.FACING, state.get(WallRedstoneTorchBlock.FACING));
    }

}
