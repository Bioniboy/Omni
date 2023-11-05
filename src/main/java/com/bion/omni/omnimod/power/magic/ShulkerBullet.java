package com.bion.omni.omnimod.power.magic;

import com.bion.omni.omnimod.entity.ModEntities;
import com.bion.omni.omnimod.entity.custom.ManaBulletEntity;
import com.bion.omni.omnimod.power.ImpulsePower;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

import java.util.List;

public class ShulkerBullet extends ImpulsePower {
    public ShulkerBullet(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return getLevel() == 1 ? "Shulker Bullet" : "Potion Bullet";
    }

    @Override
    public String getId() {
        return "shulkerBullet";
    }

    @Override
    public String getAdvancementId() {
        return getLevel() == 1 ? "shulker_bullet" : "potion_bullet";
    }

    @Override
    public double getManaCost() {
        return 5;
    }

    @Override
    public Integer getMaxLevel() {
        return 2;
    }

    @Override
    public Integer getInfluenceCost() {
        return getLevel() == 1 ? 20 : 40;
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        EntityHitResult hitResult = ProjectileUtil.raycast(user.getCameraEntity(), user.getEyePos(), user.getEyePos().add(user.getRotationVector().multiply(128)), new Box(user.getBlockPos()).expand(128), EntityPredicates.VALID_LIVING_ENTITY, 16384);
        if (hitResult != null && hitResult.getEntity() instanceof LivingEntity entity)
        {
            if (super.activate(user)) {
                List<StatusEffectInstance> effects = null;
                ItemStack stack;
                if (((Apprentice)user).omni$getMana() >= getManaCost() && getLevel() >= 2 && (stack = user.getOffHandStack()).getItem() instanceof PotionItem) {
                    ((Apprentice)user).omni$changeMana(-getManaCost());
                    effects = PotionUtil.getPotionEffects(user.getOffHandStack());
                    if (!user.getAbilities().creativeMode) {
                        stack.decrement(1);
                        if (stack.isEmpty()) {
                            user.setStackInHand(Hand.OFF_HAND, new ItemStack(Items.GLASS_BOTTLE));
                        } else
                            user.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
                    }
                }
                user.getServerWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.PLAYERS, 0.5f, 1.0f / (user.getServerWorld().getRandom().nextFloat() * 0.4f + 0.8f));
                user.getServerWorld().spawnEntity(ModEntities.MANA_BULLET.create(user.getServerWorld()).setManaBulletEntity(user, entity, Direction.Axis.Y, effects, user.getOffHandStack().getItem() instanceof SplashPotionItem, user.getOffHandStack().getItem() instanceof LingeringPotionItem, PotionUtil.getColor(user.getOffHandStack())));

            }
        }
        return false;
    }
}
