package com.bion.omni.omnimod.block;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.elements.Air;
import com.bion.omni.omnimod.util.Apprentice;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.minecraft.block.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;


public class SolidAirBlock extends AirBlock implements PolymerBlock {
    public SolidAirBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.AIR;
    }


    @Override
    public BlockState getPolymerBlockState(BlockState state, ServerPlayerEntity player) {
        if (((Apprentice)player).getElement() != null && ((Apprentice)player).getElement() instanceof Air) {
            return Blocks.BARRIER.getDefaultState();
        } else {
            return Blocks.AIR.getDefaultState();
        }
    }

}
