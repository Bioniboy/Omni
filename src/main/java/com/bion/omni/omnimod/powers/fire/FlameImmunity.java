package com.bion.omni.omnimod.powers.fire;

import com.bion.omni.omnimod.powers.ContinuousPower;
import net.minecraft.server.network.ServerPlayerEntity;

public class FlameImmunity extends ContinuousPower {
    @Override
    public String getName() {
        return "Flame Immunity";
    }

    @Override
    public String getId() {
        return "flameImmune";
    }

    @Override
    public String getAdvancementId() {
        return "flame_immune";
    }

    @Override
    public Integer getInfluenceCost() {
        return 80;
    }

    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        if (user.getFireTicks() > 0) {
            user.setFireTicks(0);
        }
    }
}
