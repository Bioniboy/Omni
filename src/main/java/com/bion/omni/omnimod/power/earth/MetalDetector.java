package com.bion.omni.omnimod.power.earth;

import com.bion.omni.omnimod.entity.ModEntities;
import com.bion.omni.omnimod.entity.custom.MetalGlow;
import com.bion.omni.omnimod.power.ContinuousPower;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class MetalDetector extends ContinuousPower {
    public MetalDetector(int level) {
        super(level);
    }
    @Override
    public String getName() {
        return "Metal Detector";
    }

    @Override
    public String getId() {
        return "metalDetector";
    }

    @Override
    public String getAdvancementId() {
        return "metal_detector";
    }

    @Override
    public Integer getInfluenceCost() {
        return 10;
    }
    @Override
    public String getPreRequisiteId() {
        return "haste";
    }

    @Override
    public double getManaCost() {
        return 2;
    }

    @Override
    public Boolean hasConfig() {
        return true; }

    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        MetalGlow glow = ModEntities.METAL_GLOW.create(user.getServerWorld());
        assert glow != null;
        glow.setPosition(user.getPos());
        glow.setGlowing(true);
        World world = user.getWorld();
        world.spawnEntity(glow);
    }
}
