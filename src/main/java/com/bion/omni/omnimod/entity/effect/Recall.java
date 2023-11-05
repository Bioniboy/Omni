package com.bion.omni.omnimod.entity.effect;

import com.bion.omni.omnimod.util.Apprentice;
import eu.pb4.polymer.core.api.other.PolymerStatusEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Set;

public class Recall extends InstantStatusEffect implements PolymerStatusEffect {
    protected Recall() {
        super(StatusEffectCategory.BENEFICIAL, 0x8d5bbd);
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        if (target instanceof Apprentice apprentice && apprentice.omni$getHomePos() != null) {
            Vec3d homePos = apprentice.omni$getHomePos();
            target.teleport((ServerWorld) apprentice.omni$getHomeWorld(), homePos.x, homePos.y,  homePos.z, EnumSet.noneOf(PositionFlag.class), target.getYaw(), target.getPitch());
            target.refreshPositionAfterTeleport(target.getPos());
            target.getWorld().playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.5f, 1.3f / (target.getWorld().getRandom().nextFloat() * 0.4f + 0.8f));
        }
    }
}
