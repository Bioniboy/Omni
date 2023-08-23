package com.bion.omni.omnimod.powers.clarity;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.powers.ImpulsePower;
import com.bion.omni.omnimod.util.Apprentice;
import com.mojang.brigadier.ParseResults;
import net.minecraft.command.CommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;

public class BlockHistory extends ImpulsePower {
    public BlockHistory(int level) {
        setLevel(level);
    }
    @Override
    public String getName() {
        OmniMod.LOGGER.info("Name:");
        OmniMod.LOGGER.info(getLevel().toString());
        OmniMod.LOGGER.info((getLevel() == 1 ? "" : "Cheap ") + "Block History");
        return (getLevel() == 1 ? "" : "Cheap ") + "Block History";
    }

    @Override
    public String getId() {
        return "blockHistory";
    }

    @Override
    public String getAdvancementId() {
        OmniMod.LOGGER.info("Advancement:");
        OmniMod.LOGGER.info(getLevel().toString());
        OmniMod.LOGGER.info((getLevel() == 1 ? "" : "cheap_") + "block_history");
        return (getLevel() == 1 ? "" : "cheap_") + "block_history";
    }

    @Override
    public Integer getMaxLevel() {
        return 2;
    }

    @Override
    public double getManaCost() {
        return switch(getLevel()) {
            case 1:
                yield 6;
            case 2:
                yield 4;
            default:
                yield 0;
        };
    }

    @Override
    public Integer getInfluenceCost() {
        return switch(getLevel()) {
            case 1:
                yield 40;
            case 2:
                yield 60;
            default:
                yield 0;
        };
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        BlockHitResult result = user.getWorld().raycast(new RaycastContext(user.getEyePos(), user.getEyePos().add(user.getRotationVector().multiply(5)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, user));
        double influence = ((Apprentice)user).getInfluence();
        if (result.getType().equals(HitResult.Type.BLOCK) && influence >= getManaCost()) {
            ((Apprentice)user).changeInfluence(-(int)getManaCost());

            BlockPos pos = result.getBlockPos();
            user.getServer().getCommandManager().executeWithPrefix(user.getCommandSource(), "ledger i " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
            return true;
        } else {
            return false;
        }
    }
}