package com.bion.omni.omnimod.block;

import com.bion.omni.omnimod.element.Air;
import com.bion.omni.omnimod.util.Apprentice;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.minecraft.block.*;
import net.minecraft.server.network.ServerPlayerEntity;


public class SolidAirBlock extends AirBlock implements PolymerBlock {
    public SolidAirBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return Blocks.AIR.getDefaultState();
    }


    @Override
    public BlockState getPolymerBlockState(BlockState state, ServerPlayerEntity player) {
        if (((Apprentice)player).omni$getElement() != null && ((Apprentice)player).omni$getElement() instanceof Air) {
            return Blocks.BARRIER.getDefaultState();
        } else {
            return Blocks.AIR.getDefaultState();
        }
    }

}
