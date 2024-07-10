package com.bion.omni.omnimod.power.earth;

import com.bion.omni.omnimod.power.ImpulsePower;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.*;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Accretion extends ImpulsePower {
    private Vec3d playerPos = null;
    public Accretion(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Accretion";
    }

    @Override
    public String getId() {
        return "accretion";
    }
    @Override
    public String getAdvancementId() {
        return "haste";
    }

    @Override
    public String getPreRequisiteId() {
        return null;
    }
    @Override
    public double getManaCost() {
        return 2;
    }
    @Override
    public Boolean hasConfig() { return true; }
    @Override
    public boolean activate(ServerPlayerEntity user) {
        super.activate(user);
        tickCounter = 5;


        return true;

    }

    public class Rock
            extends ThrownItemEntity {
        private static final EntityType entityType = EntityType.CAT;

        public Rock(World world) {
            super(entityType, world);
        }
        public Rock(World world, LivingEntity owner) {
            super((EntityType<? extends ThrownItemEntity>) EntityType.SNOWBALL, owner, world);
        }

        public Rock(World world, double x, double y, double z) {
            super((EntityType<? extends ThrownItemEntity>) EntityType.SNOWBALL, x, y, z, world);
        }


        @Override
        protected Item getDefaultItem() {
            return Items.COBBLESTONE;

        }
        private ParticleEffect getParticleParameters() {
            ItemStack itemStack = this.getStack();
            return itemStack.isEmpty() || itemStack.isOf(this.getDefaultItem()) ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
        }

        @Override
        public void handleStatus(byte status) {
            if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
                ParticleEffect particleEffect = this.getParticleParameters();
                for (int i = 0; i < 8; ++i) {
                    this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
                }
            }
        }
    }
    public void tick(ServerPlayerEntity user) {
        super.tick(user);
        for (double x = -2; x <= 2; x += 0.2) {
            for (double y = -2; y <= 2; y += 0.2) {
                for (double z = -2; z <= 2; z += 0.2) {
                    double distance = new Vec3d(user.getX() + x, user.getY() + y, user.getZ() + z).distanceTo(user.getPos());
                    if (user.getRandom().nextDouble() > 0.5 && distance > 1.9 && distance <= 2.0) {
                        user.getServerWorld().spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.COBBLESTONE.getDefaultState()), user.getX() + x, user.getY() + y + 1, user.getZ() + z, 1, 0, 0, 2, 2);
                    }
                }
            }
        }
//        if(tickCounter == 1){
//        new Rock(user.getWorld());
//            Item YotedRock = new SnowballItem(new Item.Settings()YotedRock);
//
//            if (!world.isClient) {
//                SnowballEntity snowballEntity = new SnowballEntity(world, user);
//                snowballEntity.setItem(itemStack);
//                snowballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
//                world.spawnEntity(snowballEntity);
//            }
//        }

    }

    @Override
    public boolean isTicking() {
        return super.isTicking();
    }
}
