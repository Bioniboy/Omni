package com.bion.omni.omnimod.powers.fire;

import com.bion.omni.omnimod.powers.ImpulsePower;
import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;

public class LavaStorage extends ImpulsePower {
    private int lavaStored = 0;
    public LavaStorage(int level) {
        setLevel(level);
    }
    @Override
    public String getName() {
        return "Lava Storage" + (getLevel() > 1 ? " " + getRomanNumeral(getLevel()) : "");
    }

    @Override
    public String getId() {
        return "lavaStorage";
    }

    @Override
    public NbtCompound toNbt() {
        NbtCompound nbt = super.toNbt();
        nbt.putInt("lavaStored", lavaStored);
        return nbt;
    }

    @Override
    public Power setNbt(NbtCompound nbt) {
        super.setNbt(nbt);
        lavaStored = nbt.getInt("lavaStored");
        return this;
    }

    @Override
    public Integer getInfluenceCost() {
        return switch(getLevel()) {
            case 1:
                yield 40;
            case 2:
                yield 70;
            default:
                yield 0;
        };
    }

    @Override
    public Integer getMaxLevel() {
        return 2;
    }

    @Override
    public double getManaCost() {
        return 5;
    }
    private int getMaxLava() {
        return switch(getLevel()) {
            case 1:
                yield 50;
            case 2:
                yield 100;
            default:
                yield 0;
        };
    }
    @Override
    public boolean activate(ServerPlayerEntity user) {
        if (((Apprentice)user).omni$getMana() >= getManaCost()) {
            BlockHitResult result = user.getWorld().raycast(new RaycastContext(user.getEyePos(), user.getEyePos().add(user.getRotationVector().multiply(5)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, user));
            if (result.getType().equals(HitResult.Type.BLOCK)) {
                if (user.getWorld().getBlockState(result.getBlockPos()) == Blocks.LAVA.getDefaultState()) {
                    if (lavaStored < getMaxLava()) {
                        ((Apprentice)user).omni$changeMana(-getManaCost());
                        lavaStored++;
                        user.sendMessageToClient(Text.literal("Lava: " + lavaStored + "/" + getMaxLava()).formatted(Formatting.GOLD), false);
                        user.getWorld().setBlockState(result.getBlockPos(), Blocks.AIR.getDefaultState());
                    }
                    else
                        user.sendMessageToClient(Text.literal("Lava storage full").formatted(Formatting.GOLD), false);
                } else {
                    if (lavaStored > 0) {
                        ((Apprentice)user).omni$changeMana(-getManaCost());
                        lavaStored--;
                        user.sendMessageToClient(Text.literal("Lava: " + lavaStored + "/" + getMaxLava()).formatted(Formatting.GOLD), false);
                        if (user.getWorld().getBlockState(result.getBlockPos()).getBlock() instanceof FluidBlock)
                            user.getWorld().setBlockState(result.getBlockPos(), Blocks.LAVA.getDefaultState());
                        else
                            user.getWorld().setBlockState(result.getBlockPos().offset(result.getSide()), Blocks.LAVA.getDefaultState());
                    }
                    else
                        user.sendMessageToClient(Text.literal("Lava storage empty").formatted(Formatting.GOLD), false);
                }
            }
            return true;
        }
        return false;
    }
}
