package com.bion.omni.omnimod.powers.storm;

import com.bion.omni.omnimod.powers.ImpulsePower;
import com.bion.omni.omnimod.powers.Mana;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.Explosion;

public class SelfDestruct extends ImpulsePower {

    @Override
    public String getName() {
        return "Self Destruct";
    }

    @Override
    public String getId() {
        return "selfDestruct";
    }

    @Override
    public String getPreRequisiteId() {
        return "goBoom";
    }

    @Override
    public Integer getInfluenceCost() {
        return 50;
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        float power = ((float) ((Apprentice) user).getMana() / (float) ((Apprentice) user).getManaMax()) * 6.0F;
        ((Apprentice) user).setMana(0);
        explode(user, power);
        user.damage(user.getWorld().getDamageSources().explosion(user, user), 999);
        Mana.manaShow(user);
        return true;
    }

    private void explode(ServerPlayerEntity user, float power) {
        user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 4.0f, (1.0f + (user.getWorld().random.nextFloat() - user.getWorld().random.nextFloat()) * 0.2f) * 0.7f);

        if (power < 2.0f) {
            user.getWorld().spawnParticles(ParticleTypes.EXPLOSION, user.getX(), user.getY(), user.getZ(), 1, 0, 0, 0, 0);
        } else {
            user.getWorld().spawnParticles(ParticleTypes.EXPLOSION_EMITTER, user.getX(), user.getY(), user.getZ(), 1, 0, 0, 0, 0);
        }
        Box box = new Box(user.getBlockPos()).expand(power + 1.0);
        for (Entity entity : user.getWorld().getOtherEntities(user, box)) {
            if (!(entity instanceof PlayerEntity || (entity instanceof HostileEntity hostileEntity && !hostileEntity.isPersistent()))) continue;
            double distance = Math.sqrt(entity.squaredDistanceTo(user.getPos()));
            double w = distance / (double)power;
            if (entity.isImmuneToExplosion() || !(w <= 1.0) || distance == 0.0) continue;
            double exposure = Explosion.getExposure(user.getPos(), entity);
            double ac = (1.0 - w) * exposure;
            entity.damage(user.getDamageSources().explosion(user, user), (int)((ac * ac + ac) / 2.0 * 7.0 * (double)power + 1.0));
            double ad = ac / distance;
            if (entity instanceof LivingEntity) {
                ad = ProtectionEnchantment.transformExplosionKnockback((LivingEntity)entity, ac);
            }
            Vec3d difference = entity.getPos().subtract(user.getPos());
            entity.setVelocity(entity.getVelocity().add(difference.x * ad, difference.y * ad, difference.z * ad));
        }

    }
}
