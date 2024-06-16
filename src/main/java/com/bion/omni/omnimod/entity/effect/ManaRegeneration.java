package com.bion.omni.omnimod.entity.effect;

import com.bion.omni.omnimod.util.Apprentice;
import eu.pb4.polymer.core.api.other.PolymerStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ManaRegeneration extends StatusEffect implements PolymerStatusEffect {

    protected ManaRegeneration() {
        super(StatusEffectCategory.BENEFICIAL, 110011);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof Apprentice apprentice && apprentice.omni$getElement() != null) {
            apprentice.omni$changeMana(1);

            if (apprentice.omni$getMana() > apprentice.omni$getManaMax()) {
                apprentice.omni$setMana(apprentice.omni$getManaMax());
            }
        }

        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 50 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        }
        return true;
    }

}
