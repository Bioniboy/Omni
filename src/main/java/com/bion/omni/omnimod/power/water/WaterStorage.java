package com.bion.omni.omnimod.power.water;

import com.bion.omni.omnimod.power.ImpulsePower;
import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.FluidFillable;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;

public class WaterStorage extends ImpulsePower {
    private int waterStored = 0;

    public int getWaterStored() {
        return waterStored;
    }
    public void setWaterStored(int water) {
        waterStored = water;
    }

    public WaterStorage(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Water Storage" + (getLevel() > 1 ? " " + getRomanNumeral(getLevel()) : "");
    }

    @Override
    public String getId() {
        return "waterStorage";
    }

    @Override
    public NbtCompound toNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = super.toNbt(registries);
        nbt.putInt("waterStored", waterStored);
        return nbt;
    }

    @Override
    public String getAdvancementId() {
        return switch(getLevel()) {
            case 1:
                yield "water_storage";
            case 2:
                yield "water_storage_2";
            default:
                yield "";
        };
    }

    @Override
    public Power setNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.setNbt(nbt, registries);
        waterStored = nbt.getInt("waterStored");
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
        return 3;
    }
    private int getMaxWater() {
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
            BlockHitResult result = user.getWorld().raycast(new RaycastContext(user.getEyePos(), user.getEyePos().add(user.getRotationVector().multiply(5)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.SOURCE_ONLY, user));
            if (result.getType().equals(HitResult.Type.BLOCK)) {
                if (user.getWorld().getBlockState(result.getBlockPos()) == Blocks.WATER.getDefaultState()) {
                    if (waterStored < getMaxWater()) {
                        ((Apprentice)user).omni$changeMana(-getManaCost());
                        waterStored++;
                        user.sendMessageToClient(Text.literal("Water: " + waterStored + "/" + getMaxWater()).formatted(Formatting.DARK_AQUA), false);
                        user.getWorld().setBlockState(result.getBlockPos(), Blocks.AIR.getDefaultState());
                    }
                    else
                        user.sendMessageToClient(Text.literal("Water storage full").formatted(Formatting.DARK_AQUA), false);
                } else {
                    if (waterStored > 0) {
                        ((Apprentice)user).omni$changeMana(-getManaCost());
                        waterStored--;
                        user.sendMessageToClient(Text.literal("Water: " + waterStored + "/" + getMaxWater()).formatted(Formatting.DARK_AQUA), false);
                        if (user.getWorld().getDimension().ultrawarm()) {
                            int i = result.getBlockPos().getX();
                            int j = result.getBlockPos().getY();
                            int k = result.getBlockPos().getZ();
                            user.getWorld().playSound(user, result.getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (user.getWorld().random.nextFloat() - user.getWorld().random.nextFloat()) * 0.8f);
                            for (int l = 0; l < 8; ++l) {
                                user.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0, 0.0, 0.0);
                            }
                        }
                        else if (user.getWorld().getBlockState(result.getBlockPos()).getBlock() instanceof FluidFillable) {
                            ((FluidFillable)(user.getWorld().getBlockState(result.getBlockPos()).getBlock())).tryFillWithFluid(user.getWorld(), result.getBlockPos(), user.getWorld().getBlockState(result.getBlockPos()), (Fluids.WATER).getStill(false));
                        }
                        else if (user.getWorld().getBlockState(result.getBlockPos()).getBlock() instanceof FluidBlock)
                            user.getWorld().setBlockState(result.getBlockPos(), Blocks.WATER.getDefaultState());
                        else
                            user.getWorld().setBlockState(result.getBlockPos().offset(result.getSide()), Blocks.WATER.getDefaultState());
                    }
                    else
                        user.sendMessageToClient(Text.literal("Water storage empty").formatted(Formatting.DARK_AQUA), false);
                }
            }
            return true;
        }
        return false;
    }
}
