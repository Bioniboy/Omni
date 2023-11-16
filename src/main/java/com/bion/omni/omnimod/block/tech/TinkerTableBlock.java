package com.bion.omni.omnimod.block.tech;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.gui.TinkerTableGui;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.SimplePolymerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TinkerTableBlock extends Block implements PolymerTexturedBlock {
   private final BlockState polymerBlockState;
    public TinkerTableBlock(Settings settings) {
        super(settings);
        this.polymerBlockState = PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(new Identifier(OmniMod.MOD_ID,"block/tinker_table")));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        TinkerTableGui tinkerTable = new TinkerTableGui((ServerPlayerEntity) player);
        tinkerTable.open();
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return polymerBlockState.getBlock();
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return polymerBlockState;
    }
}
