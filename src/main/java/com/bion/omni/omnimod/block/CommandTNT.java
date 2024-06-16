package com.bion.omni.omnimod.block;

import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CommandBlock;

public class CommandTNT extends CommandBlock implements PolymerBlock {
    public CommandTNT(Settings settings, boolean auto) {
        super(auto, settings);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return Blocks.TNT.getDefaultState();
    }
}
