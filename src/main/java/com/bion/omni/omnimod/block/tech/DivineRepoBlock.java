package com.bion.omni.omnimod.block.tech;

import com.bion.omni.omnimod.gui.DivineRepoGui;
import com.bion.omni.omnimod.power.tech.DivineRepo;
import com.bion.omni.omnimod.util.Apprentice;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DivineRepoBlock extends Block implements PolymerTexturedBlock {
    public DivineRepoBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {

        return Blocks.BARREL.getDefaultState();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ((Apprentice)player).omni$addPower(new DivineRepo(1));
        DivineRepo divineRepo = (DivineRepo)((Apprentice)player).omni$getPowerById("divineRepo");
        if(divineRepo == null){
            return ActionResult.FAIL;
        }
        DivineRepoGui repo = new DivineRepoGui((ServerPlayerEntity) player, divineRepo.get());
        repo.open();
        return ActionResult.SUCCESS;
    }
}
