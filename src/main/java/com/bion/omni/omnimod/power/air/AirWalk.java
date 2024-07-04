package com.bion.omni.omnimod.power.air;

import com.bion.omni.omnimod.block.ModBlocks;
import com.bion.omni.omnimod.block.SolidAirBlock;
import com.bion.omni.omnimod.power.ContinuousPower;
import com.bion.omni.omnimod.util.Apprentice;
import com.bion.omni.omnimod.util.SolidAirMarker;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class AirWalk extends ContinuousPower {
    boolean wasSneaking = false;
    int tickCooldown = 0;
    private final List<SolidAirMarker> solidAirMarkers = new ArrayList<>();

    public AirWalk(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Air Walk";
    }

    @Override
    public String getId() {
        return "airWalk";
    }
    @Override
    public String getAdvancementId() {
        return "air_walk";
    }

    @Override
    public Integer getInfluenceCost() {
        return 40;
    }
    public void setTickCooldown(int cooldown) {
        tickCooldown = cooldown;
    }
    @Override
    public void use(ServerPlayerEntity user) {
        if (tickCooldown == 0 && ((Apprentice)user).omni$getMana() >= 3) {
            boolean used = false;
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    if (user.getWorld().getBlockState(user.getBlockPos().add(x, -1, z)).getBlock() == Blocks.AIR) {
                        solidAirMarkers.add(new SolidAirMarker(user.getServerWorld(), user.getBlockPos().add(x, -1, z), 600));
                        used = true;
                    }
                }
            }
            if (used) {
                ((Apprentice) user).omni$changeMana(-3);
            }
        }
        if (user.isSneaking() && !wasSneaking && user.getWorld().getBlockState(user.getBlockPos().add(0, -1, 0)).getBlock() instanceof SolidAirBlock) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    if (user.getWorld().getBlockState(user.getBlockPos().add(x, -1, z)).getBlock() == ModBlocks.SOLID_AIR) {
                        user.getWorld().setBlockState(user.getBlockPos().add(x, -1, z), Blocks.AIR.getDefaultState());
                    }
                }
            }

            tickCooldown = 6;
        }
        wasSneaking = user.isSneaking();
        solidAirMarkers.removeIf(marker -> !marker.tick());
        if (tickCooldown > 0) {
            tickCooldown -= 1;
        }
    }

    @Override
    public void stop(ServerPlayerEntity user) {
        for (var marker : solidAirMarkers) {
            marker.end();
        }
        solidAirMarkers.clear();
    }

    @Override
    public void onDisconnect(ServerPlayerEntity user) {
        stop(user);
    }
}
