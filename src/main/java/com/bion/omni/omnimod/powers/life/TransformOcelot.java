package com.bion.omni.omnimod.powers.life;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.powers.ContinuousPower;
import com.bion.omni.omnimod.util.EntityDisguise;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class TransformOcelot extends TransformPower {

    @Override
    public String getName() {
        return "Transform Ocelot";
    }

    @Override
    public String getId() {
        return "transformOcelot";
    }

    @Override
    public String getAdvancementId() {
        return "ocelot";
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
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 1, true, false));
    }

    @Override
    public EntityType<? extends LivingEntity> getDisguiseType() {
        return EntityType.OCELOT;
    }

}
