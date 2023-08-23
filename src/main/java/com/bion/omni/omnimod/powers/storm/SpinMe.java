package com.bion.omni.omnimod.powers.storm;

import com.bion.omni.omnimod.powers.ImpulsePower;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SpinMe extends ImpulsePower {
    @Override
    public String getName() {
        return "Spin Me Right Round";
    }

    @Override
    public String getId() {
        return "spinMe";
    }

    @Override
    public String getPreRequisiteId() {
        return "mark";
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
    public boolean activate(ServerPlayerEntity user) {
        LivingEntity mark = ((Mark)(((Apprentice)user).getPowerById("mark"))).getTarget(user.getServerWorld());
        if (mark != null) {
            if (super.activate(user)) {
                if (mark instanceof ServerPlayerEntity player) {
                    player.networkHandler.requestTeleport(player.getX(), player.getY(), player.getZ(), player.getYaw() - 180, player.getPitch());
                } else {
                    mark.headYaw = mark.headYaw - 180;
                    mark.bodyYaw = mark.bodyYaw - 180;
                }
            }
        } else {
            user.sendMessage(Text.literal("No valid mark").formatted(Formatting.DARK_GRAY));
        }
        return false;
    }
}
