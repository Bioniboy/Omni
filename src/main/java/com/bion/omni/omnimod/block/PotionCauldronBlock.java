package com.bion.omni.omnimod.block;

import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;

public class PotionCauldronBlock extends CauldronBlock implements PolymerBlock {
    public PotionCauldronBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {

        return Blocks.CAULDRON.getDefaultState();
    }
}
