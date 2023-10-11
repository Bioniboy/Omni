package com.bion.omni.omnimod.power.air;

import com.bion.omni.omnimod.power.ContinuousPower;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.server.network.ServerPlayerEntity;

public class WaterMovement extends ContinuousPower {
    public WaterMovement(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Water Movement";
    }

    @Override
    public String getId() {
        return "waterMovement";
    }

    @Override
    public String getAdvancementId() {
        return "water_movement";
    }

    @Override
    public String getPreRequisiteId() {
        return "dolphinsGrace";
    }

    @Override
    public Integer getInfluenceCost() {
        return 70;
    }

    @Override
    public boolean isActive(ServerPlayerEntity user) {
        return super.isActive(user) && user.isSubmergedInWater();
    }

    @Override
    public void use(ServerPlayerEntity user) {
        if (user.isSprinting()) {
            user.getAbilities().flying = false;
            user.setSwimming(true);
            user.sendAbilitiesUpdate();
        }
        else if (!user.getAbilities().flying && !user.isSprinting()) {
            user.getAbilities().flying = true;
            user.sendAbilitiesUpdate();
        }
    }

    @Override
    public void stop(ServerPlayerEntity user) {
        user.getAbilities().flying = false;
        user.sendAbilitiesUpdate();
    }
}
