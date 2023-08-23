package com.bion.omni.omnimod.powers.moon;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.powers.ImpulsePower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;

import java.util.List;

public class BlindnessWave extends ImpulsePower {
    public BlindnessWave(Integer level) {
        this.setLevel(level);
    }
    @Override
    public String getName() {
        return "Blindness Wave " + getRomanNumeral(this.getLevel());
    }
    @Override
    public Integer getMaxLevel() {
        return 3;
    }

    @Override
    public String getId() {
        return "blindnessWave";
    }
    @Override
    public String getAdvancementId() {
        return "blind_wave" + (getLevel() == 1 ? "" : "_" + getLevel());
    }
    @Override
    public String getPreRequisiteId() {
        return "nightVision";
    }
    @Override
    public double getManaCost() {
        return 50;
    }
    @Override
    public Integer getInfluenceCost() {
        return switch (this.getLevel()) {
            case 1:
                yield 50;
            case 2:
                yield 70;
            case 3:
                yield 80;
            default:
                yield 0;
        };
    }
    @Override
    public Boolean hasConfig() {
        return true;
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {


        if (super.activate(user)) {
            int mobDuration = 0;
            int playerDuration = 0;
            boolean affectPlayers = false;
            switch (getLevel()) {
                case 1 -> mobDuration = 120;
                case 2 -> {
                    mobDuration = 120;
                    playerDuration = 60;
                    affectPlayers = true;
                }
                case 3 -> {
                    mobDuration = 240;
                    playerDuration = 120;
                }
            }
            user.getServerWorld().spawnParticles(user, ParticleTypes.SMOKE, true, user.getX(), user.getY(), user.getZ(), 50,0.2, 0, 0.2, 0.5);
            Box entityBox = new Box(user.getBlockPos()).expand(32);
            OmniMod.LOGGER.info(entityBox.toString());
            List<Entity> entityList = user.getWorld().getOtherEntities(null, entityBox);
            OmniMod.LOGGER.info(entityList.toString());
            for (Entity entity : entityList) {
                OmniMod.LOGGER.info(entity.getEntityName());
                if (!(entity instanceof PlayerEntity) && entity instanceof LivingEntity mob) {
                    mob.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, mobDuration, 2));
                }
            }
            if (affectPlayers) {
                Box playerBox = new Box(user.getBlockPos()).expand(10);
                for (Entity entity : user.getWorld().getOtherEntities(user, playerBox)) {
                    if (entity instanceof PlayerEntity player) {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, playerDuration, 0));
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
