package com.bion.omni.omnimod.entity.custom;

import com.bion.omni.omnimod.entity.ModEntities;
import com.bion.omni.omnimod.mixin.accessor.ShulkerBulletEntityAccessor;
import com.google.common.base.MoreObjects;
import eu.pb4.polymer.core.api.entity.PolymerEntity;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaBulletEntity extends ShulkerBulletEntity implements PolymerEntity {
    private List<StatusEffectInstance> effects;
    private boolean splash;
    private boolean lingering;
    private int color;


    public ManaBulletEntity(EntityType<? extends ShulkerBulletEntity> entityType, World world) {
        super(entityType, world);
    }

    public ManaBulletEntity setManaBulletEntity(LivingEntity owner, Entity target, Direction.Axis axis, List<StatusEffectInstance> effects, boolean splash, boolean lingering, int color) {
        this.splash = splash;
        this.lingering = lingering;
        this.setOwner(owner);
        BlockPos blockPos = owner.getBlockPos();
        double d = (double)blockPos.getX() + 0.5;
        double e = (double)blockPos.getY() + 0.5;
        double f = (double)blockPos.getZ() + 0.5;
        this.refreshPositionAndAngles(d, e, f, this.getYaw(), this.getPitch());
        ((ShulkerBulletEntityAccessor)this).setTarget(target);
        ((ShulkerBulletEntityAccessor)this).setDirection(Direction.UP);
        ((ShulkerBulletEntityAccessor)this).invokeChangeTargetDirection(axis);
        this.effects = effects;
        this.color = color;
        return this;
    }

    @Override
    public EntityType<?> getPolymerEntityType(ServerPlayerEntity player) {
        return EntityType.SHULKER_BULLET;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        Entity entity2 = this.getOwner();
        LivingEntity livingEntity = entity2 instanceof LivingEntity ? (LivingEntity)entity2 : null;
        boolean bl = entity.damage(this.getDamageSources().mobProjectile(this, livingEntity), 4.0f);
        if (bl) {
            this.applyDamageEffects(livingEntity, entity);
            if (effects != null) {
                if (splash) {
                    applySplashPotion(entity);
                } else if (lingering) {
                    applyLingeringPotion();
                } else if (entity instanceof LivingEntity livingEntity2) {
                    for (StatusEffectInstance statusEffectInstance : effects) {
                        if (statusEffectInstance.getEffectType().isInstant()) {
                            statusEffectInstance.getEffectType().applyInstantEffect(getOwner(), getOwner(), livingEntity2, statusEffectInstance.getAmplifier(), 1.0);
                            continue;
                        }
                        livingEntity2.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
                    }
                }
            }
        }
    }
    private void applySplashPotion(@Nullable Entity entity) {
        Box box = this.getBoundingBox().expand(4.0, 2.0, 4.0);
        List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
        if (!list.isEmpty()) {
            Entity entity2 = this.getEffectCause();
            for (LivingEntity livingEntity : list) {
                double d;
                if (!livingEntity.isAffectedBySplashPotions() || !((d = this.squaredDistanceTo(livingEntity)) < 16.0)) continue;
                double e = livingEntity == entity ? 1.0 : 1.0 - Math.sqrt(d) / 4.0;
                for (StatusEffectInstance statusEffectInstance : effects) {
                    StatusEffect statusEffect = statusEffectInstance.getEffectType();
                    if (statusEffect.isInstant()) {
                        statusEffect.applyInstantEffect(this, this.getOwner(), livingEntity, statusEffectInstance.getAmplifier(), e);
                        continue;
                    }
                    int i2 = statusEffectInstance.mapDuration(i -> (int)(e * (double)i + 0.5));
                    StatusEffectInstance statusEffectInstance2 = new StatusEffectInstance(statusEffect, i2, statusEffectInstance.getAmplifier(), statusEffectInstance.isAmbient(), statusEffectInstance.shouldShowParticles());
                    if (statusEffectInstance2.isDurationBelow(20)) continue;
                    livingEntity.addStatusEffect(statusEffectInstance2, entity2);
                }
            }
        }
        this.getWorld().syncWorldEvent(WorldEvents.SPLASH_POTION_SPLASHED, this.getBlockPos(), color);
    }

    private void applyLingeringPotion() {
        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            areaEffectCloudEntity.setOwner((LivingEntity)entity);
        }
        areaEffectCloudEntity.setRadius(3.0f);
        areaEffectCloudEntity.setRadiusOnUse(-0.5f);
        areaEffectCloudEntity.setWaitTime(10);
        areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
        for (StatusEffectInstance statusEffectInstance : effects) {
            areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
        }
        areaEffectCloudEntity.setColor(color);
        this.getWorld().spawnEntity(areaEffectCloudEntity);
        this.getWorld().syncWorldEvent(WorldEvents.SPLASH_POTION_SPLASHED, this.getBlockPos(), color);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (effects != null) {
            if (splash) {
                applySplashPotion(null);
            } else if (lingering)
                applyLingeringPotion();
        }
        return super.damage(source, amount);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (effects != null) {
            if (splash) {
                applySplashPotion(null);
            } else if (lingering)
                applyLingeringPotion();
        }
        super.onBlockHit(blockHitResult);
    }
}
