package com.bion.omni.omnimod.power.storm;

import com.bion.omni.omnimod.power.ImpulsePower;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public class HammerTime extends ImpulsePower {
    LivingEntity victim = null;
    Vec3d pos = null;
    float pitch = 0;
    float yaw = 0;

    public HammerTime(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Hammer Time";
    }

    @Override
    public String getId() {
        return "hammerTime";
    }

    @Override
    public String getAdvancementId() {
        return "hammer_time";
    }

    @Override
    public Integer getInfluenceCost() {
        return 30;
    }

    @Override
    public double getManaCost() {
        return 30;
    }

    @Override
    public String getPreRequisiteId() {
        return "spinMe";
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        LivingEntity mark = ((Mark)(((Apprentice)user).omni$getPowerById("mark"))).getTarget(user.getServerWorld());
        if (mark != null) {
            if (super.activate(user)) {
                tickCounter = 20;
                victim = mark;
                pos = mark.getPos();
                yaw = mark.getYaw();
                pitch = mark.getPitch();
                return true;
            }
        } else {
            user.sendMessage(Text.literal("No valid mark").formatted(Formatting.DARK_GRAY));
        }
        return false;
    }

    @Override
    public void tick(ServerPlayerEntity user) {

        if (victim instanceof ServerPlayerEntity player) {
            player.networkHandler.requestTeleport(pos.x, pos.y, pos.z, yaw, pitch);
        } else {
            victim.setPosition(pos);
            victim.setPitch(pitch);
            victim.setYaw(yaw);
        }
        super.tick(user);
    }

    @Override
    public void end(ServerPlayerEntity user) {
        victim = null;
        pos = null;
    }
}
