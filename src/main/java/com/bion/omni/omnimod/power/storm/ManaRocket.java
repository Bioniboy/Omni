package com.bion.omni.omnimod.power.storm;

import com.bion.omni.omnimod.power.ImpulsePower;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class ManaRocket extends ImpulsePower {
    public ManaRocket(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Zoom";
    }

    @Override
    public String getId() {
        return "manaRocket";
    }

    @Override
    public String getPreRequisiteId() {
        return "makeItRain";
    }

    @Override
    public String getAdvancementId() {
        return "mana_rockets";
    }

    @Override
    public Integer getInfluenceCost() {
        return 45;
    }

    @Override
    public double getManaCost() {
        return 3;
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        if ((user.getWorld().isRaining() && ((Apprentice)user).omni$getMana() >= 1) || super.activate(user)) {
            if (user.getWorld().isRaining())
                ((Apprentice)user).omni$changeMana(-1);
            Vec3d vec3d = user.getRotationVector();
            Vec3d vec3d2 = user.getVelocity();
            user.addVelocity(vec3d.x * 1 + (vec3d.x * 1.5 - vec3d2.x) * 0.5, vec3d.y * 1 + (vec3d.y * 1.5 - vec3d2.y) * 0.5, vec3d.z * 1 + (vec3d.z * 1.5 - vec3d2.z) * 0.5);
            user.velocityModified = true;
            return true;
        } else {
            return false;
        }
    }
}
