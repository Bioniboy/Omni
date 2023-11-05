package com.bion.omni.omnimod.entity.effect;

import com.bion.omni.omnimod.util.Apprentice;
import eu.pb4.polymer.core.api.other.PolymerStatusEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.Nullable;

public class Mark extends InstantStatusEffect implements PolymerStatusEffect {
    protected Mark() {
        super(StatusEffectCategory.BENEFICIAL, 0x240f42);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        if (target instanceof Apprentice apprentice) {
            apprentice.omni$setHome(target.getPos(), target.getWorld());
            target.getWorld().playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, SoundCategory.PLAYERS, 0.5f, 1.3f / (target.getWorld().getRandom().nextFloat() * 0.4f + 0.8f));
        }
    }
}
