package com.bion.omni.omnimod.entity.custom;

import com.bion.omni.omnimod.OmniMod;
import eu.pb4.polymer.core.api.entity.PolymerEntity;
import eu.pb4.polymer.virtualentity.api.tracker.DisplayTrackedData;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.provim.nylon.mixins.tracking.SynchedEntityDataMixin;

import java.util.List;

public class AccRockEntity extends LlamaSpitEntity implements PolymerEntity{
    public AccRockEntity(EntityType<? extends AccRockEntity> entityType, World world) {
        super(entityType, world);
    }

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
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.INTERPOLATION_DURATION, 4));
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.WIDTH, 4f));
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.HEIGHT, 4f));
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.LEFT_ROTATION, new Quaternionf(0f,0f,0f,1f)));
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.RIGHT_ROTATION, new Quaternionf(0f,0f,0f,1f)));
        OmniMod.LOGGER.info("hi");
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.TRANSLATION, new Vector3f(-2.5f,-2.5f,-2.5f)));
        data.add(DataTracker.SerializedEntry.of(DisplayTrackedData.SCALE, new Vector3f(5F)));
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(this.getDamageSources().thrown(this, this.getOwner()), 20.0f);
    }
    @Override
    public EntityType<?> getPolymerEntityType(ServerPlayerEntity player) {
        return EntityType.BLOCK_DISPLAY;

    }

    //    @Override
//    protected void onCollision(HitResult hitResult) {
//        super.onCollision(hitResult);
//        if (hitResult = damage(getOwner(),5);
//    }

    //    public void RockRenderer(FeatureRendererContext<PlayerEntity, EntityModel<PlayerEntity>> context, BlockRenderManager blockRenderManager) {
//        this.blockRenderManager = blockRenderManager;
//    }
//    private  BlockRenderManager blockRenderManager;
//
//    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, PlayerBody playerentity, float f, float g, float h, float j, float k, float l) {
//        BlockState blockState = endermanEntity.getCarriedBlock();
//        if (blockState == null) {
//            return;
//        }
//        matrixStack.push();
//        matrixStack.translate(0.0f, 0.6875f, -0.75f);
//        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(20.0f));
//        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45.0f));
//        matrixStack.translate(0.25f, 0.1875f, 0.25f);
//        float m = 0.5f;
//        matrixStack.scale(-0.5f, -0.5f, 0.5f);
//        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f));
//        this.blockRenderManager.renderBlockAsEntity(blockState, matrixStack, vertexConsumerProvider, i, OverlayTexture.DEFAULT_UV);
//        matrixStack.pop();
//    }

}
