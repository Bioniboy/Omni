package com.bion.omni.omnimod.block;

import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BadTrapdoor extends TrapdoorBlock implements PolymerBlock {

    public BadTrapdoor(Settings settings, BlockSetType blockSetType) {
        super(settings, blockSetType);
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.BIRCH_TRAPDOOR;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return Blocks.BIRCH_TRAPDOOR.getDefaultState().with(TrapdoorBlock.FACING, state.get(TrapdoorBlock.FACING)).with(TrapdoorBlock.OPEN, state.get(TrapdoorBlock.OPEN)).with(TrapdoorBlock.HALF, state.get(TrapdoorBlock.HALF));
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.isCreative()) {
            return super.onUse(state, world, pos, player, hand, hit);
        } else {
            return ActionResult.SUCCESS;
        }

    }
}
