package com.bion.omni.omnimod.entity.custom;

import com.bion.omni.omnimod.OmniMod;
import eu.pb4.polymer.core.api.entity.PolymerEntity;
import eu.pb4.polymer.virtualentity.api.tracker.DisplayTrackedData;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.joml.Vector3f;
import java.util.List;
import java.util.Optional;

public class AccRockEntity extends LlamaSpitEntity implements PolymerEntity{
    public AccRockEntity(EntityType<? extends AccRockEntity> entityType, World world) {
        super(entityType, world);
    }

    public void spawnParticles(int count) {
        if (!this.getWorld().isClient) {
            ((ServerWorld)this.getEntityWorld()).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.COBBLESTONE.getDefaultState()), this.getX(), this.getY(), this.getZ(), count, .75, .75, .75,2);
        }
    }
    public static final ExplosionBehavior EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(false, true, Optional.empty(), Optional.empty());

    @Override
    public void tick() {
        super.tick();
        setYaw(0);
        setPitch(0);
    }

    @Override
    public void modifyRawTrackedData(List<DataTracker.SerializedEntry<?>> data, ServerPlayerEntity player, boolean initial) {
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.Block.BLOCK_STATE, Blocks.COBBLESTONE.getDefaultState()));
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.TELEPORTATION_DURATION, 4));
//        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.INTERPOLATION_DURATION, 100));
//        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.WIDTH, .5f));
//        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.HEIGHT, .5f));
//        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.LEFT_ROTATION, new Quaternionf(0f,0f,0f,1f)));
//        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.RIGHT_ROTATION, new Quaternionf(0f,0f,0f,1f)));
//        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.TRANSLATION, new Vector3f(-2.5f,-2.5f,-2.5f)));
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.SCALE, new Vector3f(1F)));
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        OmniMod.LOGGER.info("hit entity");
        spawnParticles(400);
        entityHitResult.getEntity().damage(this.getDamageSources().thrown(this, this.getOwner()), 20.0f);
        World world = this.getWorld();
        world.playSound(null, BlockPos.ofFloored(this.getPos()), SoundEvent.of(Identifier.of(OmniMod.MOD_ID, "rockhit")), SoundCategory.PLAYERS, .7F, world.random.nextFloat() * 0.1F + 0.9F);
        this.createExplosion(this.getPos());
        this.discard();
    }

//    @Override
//    protected void createExplosion(Vec3d pos) {
//        this.getWorld().createExplosion(this, null, EXPLOSION_BEHAVIOR, pos.getX(), pos.getY(), pos.getZ(), 3.0f, false, World.ExplosionSourceType.TRIGGER, null, null, null);
//    }

    protected void createExplosion(Vec3d pos) {
        this.getWorld().createExplosion(this, null, EXPLOSION_BEHAVIOR, pos.getX(), pos.getY(), pos.getZ(), 3.0f, false, World.ExplosionSourceType.TRIGGER, false, ParticleTypes.GUST_EMITTER_SMALL, ParticleTypes.GUST_EMITTER_LARGE, SoundEvents.ENTITY_BREEZE_WIND_BURST);
    }
    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        spawnParticles(400);
        OmniMod.LOGGER.info("hit block");
        World world = this.getWorld();
        this.createExplosion(this.getPos());
        world.playSound(null, BlockPos.ofFloored(this.getPos()), SoundEvent.of(Identifier.of(OmniMod.MOD_ID, "rockhit")), SoundCategory.PLAYERS, .7F, world.random.nextFloat() * 0.1F + 0.9F);
    }
//    @Override
//    protected void onCollision(HitResult hitResult) {
//        super.onCollision(hitResult);
//        OmniMod.LOGGER.info("hit");
//        if (!this.getWorld().isClient) {
//            for (int moreparticles = 0; moreparticles < 300; moreparticles++) {
//                this.getEntityWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.COBBLESTONE.getDefaultState()), this.getX(), this.getY(), this.getZ(), .5, .5, .5);
////                OmniMod.LOGGER.info(String.valueOf(moreparticles));
//            }
//        }
//    }
    @Override
    public EntityType<?> getPolymerEntityType(ServerPlayerEntity player) {
        return EntityType.BLOCK_DISPLAY;

    }

}
