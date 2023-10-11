package com.bion.omni.omnimod.power.water;

import com.bion.omni.omnimod.power.ContinuousPower;
import net.minecraft.server.network.ServerPlayerEntity;

public class TridentDamage extends ContinuousPower {
    public TridentDamage(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Trident Damage";
    }

    @Override
    public String getId() {
        return "tridentDamage";
    }

    @Override
    public String getAdvancementId() {
        return "trident_damage";
    }

    @Override
    public Integer getInfluenceCost() {
        return 30;
    }

    @Override
    public double getManaCost() {
        return 5;
    }

    @Override
    public void use(ServerPlayerEntity user) {

    }
}
