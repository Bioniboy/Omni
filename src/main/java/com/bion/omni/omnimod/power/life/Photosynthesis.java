package com.bion.omni.omnimod.power.life;

import com.bion.omni.omnimod.power.ContinuousPower;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;

public class Photosynthesis extends ContinuousPower {
    int tickCounter = 0;

    public Photosynthesis(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Photosynthesis";
    }

    @Override
    public String getId() {
        return "photosynthesis";
    }

    @Override
    public String getAdvancementId() {
        return "root";
    }

    @Override
    public void use(ServerPlayerEntity user) {
        ServerWorld world = user.getServerWorld();
        int skyLight = world.getLightLevel(LightType.SKY, user.getBlockPos()) - world.getAmbientDarkness();
        float skyAngle = world.getSkyAngleRadians(1.0f);
        if (skyLight > 0) {
            float zeroRadians = skyAngle < (float)Math.PI ? 0.0f : (float)Math.PI * 2;
            skyAngle += (zeroRadians - skyAngle) * 0.2f;
            skyLight = Math.round((float)skyLight * MathHelper.cos(skyAngle));
        }
        skyLight = MathHelper.clamp(skyLight, 0, 15);
        if (skyLight > 5) {
            tickCounter += 1;
            if (tickCounter >= 600) {
                user.getHungerManager().add(1, 1);
                tickCounter = 0;
            }
        } else {
            tickCounter = 0;
        }
    }
}
