package com.bion.omni.omnimod.powers.clarity;

import com.bion.omni.omnimod.entity.ModEntities;
import com.bion.omni.omnimod.entity.custom.PlayerBody;
import com.bion.omni.omnimod.powers.ImpulsePower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;

public class AstralProject extends ImpulsePower {
    private PlayerBody body = null;

    @Override
    public NbtCompound toNbt() {
        body.end();
        return super.toNbt();
    }

    @Override
    public String getName() {
        return "Astral Project";
    }

    @Override
    public String getId() {
        return "astralProject";
    }
    @Override
    public String getAdvancementId() {
        return "astral_project";
    }

    @Override
    public double getManaCost() {
        return 10;
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        if (super.activate(user)) {
            tickCounter = 100;
            body = ModEntities.PLAYER_BODY.create(user.getWorld());
            body.setPlayer(user);
            body.refreshPositionAndAngles(user.getX(), user.getY(), user.getZ(), user.getYaw(), user.getPitch());
            user.getWorld().spawnEntity(body);
            body.setVelocity(user.getVelocity());
            user.changeGameMode(GameMode.SPECTATOR);
            return true;
        }
        return false;
    }

    @Override
    public void tick(ServerPlayerEntity user) {
        if (body == null) {
            tickCounter = 0;
        }
        super.tick(user);
    }

    @Override
    public void end(ServerPlayerEntity user) {
        body.end();
    }

}
