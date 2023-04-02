package com.bion.omni.omnimod.block;

import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.block.PolymerHeadBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlayerSkullBlock;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class SmallDarkBlock extends PlayerSkullBlock implements PolymerHeadBlock {
    public SmallDarkBlock(Settings settings) {
        super(settings);
    }



    @Override
    public String getPolymerSkinValue(BlockState state, BlockPos pos, ServerPlayerEntity player) {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGZjMjM3MmI0NTc1NDJjNjU0ODNhZmExNDQyZTFjMzNhNWZmNzU4ZDM2MmVjZWM0MzQ4Nzk1MTcyODI0ZDg2OSJ9fX0=";
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.PLAYER_HEAD;
    }
}
