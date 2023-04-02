package com.bion.omni.omnimod.powers.air;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.block.ModBlocks;
import com.bion.omni.omnimod.powers.ImpulsePower;
import com.bion.omni.omnimod.util.SolidAirMarker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.RaycastContext;

import java.util.ArrayList;
import java.util.List;

public class SolidAir extends ImpulsePower {
    private final List<SolidAirMarker> solidAirMarkers = new ArrayList<>();

    @Override
    public void onDisconnect(ServerPlayerEntity user) {
        for (var solidAirMarker : solidAirMarkers) {
            solidAirMarker.end();
        }
    }
    public SolidAir(Integer level) {
        this.setLevel(level);
    }

    @Override
    public Integer getMaxLevel() {
        return 3;
    }

    @Override
    public String getName() {
        return "Solid Air " + getRomanNumeral(getLevel());
    }

    @Override
    public String getId() {
        return "solidAir";
    }

    @Override
    public double getManaCost() {
        return 10;
    }

    @Override
    public Integer getInfluenceCost() {
        return switch (getLevel()) {
            case 1:
                yield 50;
            case 2:
                yield 65;
            case 3:
                yield 80;
            default:
                yield 0;
        };
    }
    private int getBlockMax() {
        return switch (getLevel()) {
            case 1:
                yield 8;
            case 2:
                yield 16;
            case 3:
                yield 32;
            default:
                yield 0;
        };
    }
    @Override
    public String getAdvancementId() {
        return "solid_air" + (getLevel() == 1 ? "" : "_" + getLevel());
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        if (super.activate(user)) {
            BlockHitResult result = user.getWorld().raycast(new RaycastContext(user.getEyePos(), user.getEyePos().add(user.getRotationVector().multiply(5)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, user));
            BlockPos pos;
            //OmniMod.LOGGER.info(result.getType().name());
            if (result.getType().equals(HitResult.Type.BLOCK)) {
                pos = result.getBlockPos().add(result.getSide().getOffsetX(), result.getSide().getOffsetY(), result.getSide().getOffsetZ());
            } else {
                pos = BlockPos.ofFloored(user.getEyePos().add(0, -0.75, 0).add(user.getRotationVector().multiply(2)));
            }
            solidAirMarkers.add(new SolidAirMarker(user.getWorld(), pos, 36000));
            if (solidAirMarkers.size() > getBlockMax()) {
                solidAirMarkers.get(0).end();
                solidAirMarkers.remove(0);
            }
        }
        return false;
    }

    @Override
    public boolean isTicking() {
        return solidAirMarkers.size() > 0;
    }

    @Override
    public void tick(ServerPlayerEntity user) {
        solidAirMarkers.removeIf(marker -> !marker.tick());
    }
}
