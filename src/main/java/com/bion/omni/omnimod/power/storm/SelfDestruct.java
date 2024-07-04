package com.bion.omni.omnimod.power.storm;

import com.bion.omni.omnimod.power.ImpulsePower;
import com.bion.omni.omnimod.power.Mana;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class SelfDestruct extends ImpulsePower {

    public SelfDestruct(int level) {
        super(level);
    }

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
    public String getAdvancementId() {
        return "big_boom";
    }

    @Override
    public Integer getInfluenceCost() {
        return 30;
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        float power = ((float) ((Apprentice) user).omni$getMana() / (float) ((Apprentice) user).omni$getManaMax()) * 6.0F;
        ((Apprentice) user).omni$setMana(0);
        explode(user, power);
        user.damage(user.getWorld().getDamageSources().explosion(user, user), 999);
//        Mana.manaShow(user);
        return true;
    }

    private void explode(ServerPlayerEntity user, float power) {
        user.getWorld().createExplosion(user, user.getX(), user.getY(), user.getZ(), power, World.ExplosionSourceType.NONE);

    }
}
