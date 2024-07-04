package com.bion.omni.omnimod.power.storm;

import com.bion.omni.omnimod.power.ImpulsePower;
import com.bion.omni.omnimod.power.Mana;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;

public class Yeet extends ImpulsePower {
    public Yeet(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return getLevel() == 1 ? "YEET!" : "BE GONE!";
    }

    @Override
    public String getId() {
        return "yeet";
    }

    @Override
    public Integer getMaxLevel() {
        return 2;
    }

    @Override
    public String getAdvancementId() {
        return getLevel() == 1 ? "yeet" : "be_gone";
    }
    @Override
    public Integer getInfluenceCost() {
        return switch (getLevel()) {
            case 1:
                yield 50;
            case 2:
                yield 30;
            default:
                yield 0;
        };
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        //OmniMod.LOGGER.info(user.raycast(5, 1.0F, false).getType().name());
        EntityHitResult hitResult = ProjectileUtil.raycast(user.getCameraEntity(), user.getEyePos(), user.getEyePos().add(user.getRotationVector().multiply(4)), new Box(user.getBlockPos()).expand(5), EntityPredicates.VALID_LIVING_ENTITY, 16);
        if (hitResult != null) {
            float percentFull = (float)((Apprentice)user).omni$getMana() / 60;
            if (percentFull > 1) percentFull = 1;
            int strength = Math.round((float)Math.random() * (float)Math.floor(6F * percentFull));
            if (strength > 6) strength = 6;
            user.sendMessage(Text.literal("Knockback Strength: " + strength).formatted(Formatting.DARK_GRAY));
            ((Apprentice)user).omni$changeMana(-strength * 10);
//            Mana.manaShow(user);
            LivingEntity entity = ((LivingEntity)hitResult.getEntity());
            double dx = user.getX() - entity.getX();
            double dz = user.getZ() - entity.getZ();
            while (dx * dx + dz * dz < 1.0E-4) {
                dx = (Math.random() - Math.random()) * 0.01;
                dz = (Math.random() - Math.random()) * 0.01;
            }
            //entity.knockbackVelocity = (float)(MathHelper.atan2(dz, dx) * 57.2957763671875 - (double)entity.getYaw());
            entity.takeKnockback(4f * getLevel() * ((float)strength / 6), dx, dz);
            if (entity instanceof ServerPlayerEntity player) {
                player.velocityModified = true;
            }
            return true;
        }
        return false;
    }
}
