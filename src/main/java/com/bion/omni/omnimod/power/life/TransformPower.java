package com.bion.omni.omnimod.power.life;

import com.bion.omni.omnimod.power.ContinuousPower;
import com.bion.omni.omnimod.util.Apprentice;
import com.bion.omni.omnimod.util.EntityDisguise;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public abstract class TransformPower extends ContinuousPower {
    protected static final UUID TRANSFORM_HEALTH_ID = UUID.fromString("87f46a96-686f-4796-b035-22e16ee9e039");

    public TransformPower(int level) {
        super(level);
    }

    public abstract EntityType<? extends LivingEntity> getDisguiseType();
    @Override
    public void onDisconnect(ServerPlayerEntity user) {
        stop(user);
    }
    @Override
    public void stop(ServerPlayerEntity user) {
        ((EntityDisguise)user).removeDisguise();
        EntityAttributeInstance entityAttributeInstance = user.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (entityAttributeInstance == null) {
            return;
        }
        if (entityAttributeInstance.getModifier(TRANSFORM_HEALTH_ID) != null) {
            entityAttributeInstance.removeModifier(TRANSFORM_HEALTH_ID);
        }
        ((Apprentice)user).omni$setConfig(getId(), 0);
        user.setNoGravity(false);
    }

    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        float customHealth = getDisguiseType().create(user.getWorld()).getMaxHealth();
        if (!(((EntityDisguise)user).getDisguiseType() == getDisguiseType())) {
            ((EntityDisguise) user).disguiseAs(getDisguiseType());
            for (var power : ((Apprentice)user).omni$getAllPowers()) {
                if (power instanceof TransformPower transform && transform.getDisguiseType() != getDisguiseType()) {
                    ((Apprentice)user).omni$setConfig(power.getId(), 0);
                }
            }
        }
        EntityAttributeModifier transformHealth = new EntityAttributeModifier(TRANSFORM_HEALTH_ID, "Transform health", customHealth - user.defaultMaxHealth, EntityAttributeModifier.Operation.ADDITION);
        EntityAttributeInstance entityAttributeInstance = user.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (entityAttributeInstance.getModifier(TRANSFORM_HEALTH_ID) == null) {
            entityAttributeInstance.addTemporaryModifier(transformHealth);
            if (user.getHealth() > customHealth)
                user.setHealth(customHealth);
        }
    }

}
