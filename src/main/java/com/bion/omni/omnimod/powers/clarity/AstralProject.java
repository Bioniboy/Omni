package com.bion.omni.omnimod.powers.clarity;

import com.bion.omni.omnimod.entity.ModEntities;
import com.bion.omni.omnimod.entity.custom.PlayerBody;
import com.bion.omni.omnimod.powers.ContinuousPower;
import com.bion.omni.omnimod.powers.ImpulsePower;
import com.bion.omni.omnimod.util.Apprentice;
import com.bion.omni.omnimod.util.ConfigSymbol;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import net.minecraft.world.GameMode;

import java.util.ArrayList;

public class AstralProject extends ContinuousPower {
    private PlayerBody body = null;

    public PlayerBody getBody() {
        return body;
    }
    @Override
    public String getName() {
        return "Astral Project" + (getLevel() == 1 ? "" : " " + getRomanNumeral(getLevel()));
    }

    @Override
    public Integer getMaxLevel() {
        return 3;
    }

    @Override
    public void onDisconnect(ServerPlayerEntity user) {
        if (body != null)
            body.end();
    }

    @Override
    public String getId() {
        return "astralProject";
    }
    @Override
    public String getAdvancementId() {
        return getLevel() == 1 ? "root" : "astral_" + getLevel();
    }

    @Override
    public Integer getInfluenceCost() {
        return switch (getLevel()) {
            case 2:
                yield 40;
            case 3:
                yield 60;
            default:
                yield 0;
        };
    }

    @Override
    public double getManaCost() {
        return 4;
    }
    @Override
    public void start(ServerPlayerEntity user) {
        if (super.activate(user)) {
            body = ModEntities.PLAYER_BODY.create(user.getWorld());
            body.setPlayer(user);
            body.refreshPositionAndAngles(user.getX(), user.getY(), user.getZ(), user.getYaw(), user.getPitch());

            user.getWorld().spawnEntity(body);
            body.setHeadYaw(user.headYaw);
            body.setVelocity(user.getVelocity());
            body.fallDistance = user.fallDistance;
            Box entityBox = new Box(user.getBlockPos()).expand(32);
            for (Entity entity : user.getWorld().getOtherEntities(user, entityBox)) {
                if (entity instanceof HostileEntity mob && mob.getTarget() == user) {
                    mob.setTarget(body);
                }
            }
            user.changeGameMode(GameMode.SPECTATOR);
        }
    }

    @Override
    public void use(ServerPlayerEntity user) {
        int max_distance = switch(getLevel()) {
            case 1:
                yield 100;
            case 2:
                yield 200;
            case 3:
                yield 400;
            default:
                yield 0;
        };
        super.use(user);
        if (body.isRemoved() || user.getPos().squaredDistanceTo(body.getPos()) > max_distance * max_distance) {
            stop(user);
        }
    }

    @Override
    public void stop(ServerPlayerEntity user) {
        if (body != null)
            body.end();
    }

    @Override
    public ArrayList<ConfigSymbol> getConfigSymbols() {
        MutableText description = Text.literal("Activate ")
                .append(Text.literal(getName())
                        .formatted(Formatting.BLUE)
                );
        ArrayList<ConfigSymbol> configSymbols = new ArrayList<>();
        configSymbols.add(new ConfigSymbol("⬤",description, "1.close.noUnderline"));
        return configSymbols;
    }
}
