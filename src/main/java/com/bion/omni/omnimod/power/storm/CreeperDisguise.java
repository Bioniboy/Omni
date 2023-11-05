package com.bion.omni.omnimod.power.storm;

import com.bion.omni.omnimod.power.ContinuousPower;
import com.bion.omni.omnimod.util.EntityDisguise;
import net.minecraft.entity.EntityType;
import net.minecraft.server.network.ServerPlayerEntity;

public class CreeperDisguise extends ContinuousPower {
    public CreeperDisguise(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Creeper Disguise";
    }

    @Override
    public String getPreRequisiteId() {
        return "explosionResistance";
    }

    @Override
    public String getId() {
        return "creeperDisguise";
    }

    @Override
    public String getAdvancementId() {
        return "creeper_transform";
    }

    @Override
    public Integer getInfluenceCost() {
        return 30;
    }

    @Override
    public double getManaCost() {
        return 1;
    }

    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        if (!((EntityDisguise)user).isDisguised()) {
            ((EntityDisguise) user).disguiseAs(EntityType.CREEPER);
        }

    }
    //            var profile = new GameProfile(null, "Notch");
    //            SkullBlockEntity.loadProperties(profile, gameProfile -> {
    //                ((EntityDisguise) user).disguiseAs(EntityType.PLAYER);
    //                ((EntityDisguise) user).setGameProfile(profile);
    //            });
    //((EntityDisguise) user).setTrueSight(false);
    @Override
    public void stop(ServerPlayerEntity user) {
        ((EntityDisguise)user).removeDisguise();
    }

}
