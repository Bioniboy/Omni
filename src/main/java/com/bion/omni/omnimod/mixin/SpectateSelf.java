package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.power.clarity.AstralProject;
import com.bion.omni.omnimod.util.Apprentice;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class SpectateSelf extends PlayerEntity {
    public SpectateSelf(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method="setCameraEntity", at = @At("HEAD"))
    public void checkProjectPower(@Nullable Entity entity, CallbackInfo ci) {
        AstralProject power = (AstralProject) ((Apprentice) this).omni$getPowerById("astralProject");
        if (power != null && power.getBody() == entity) {
            power.stop((ServerPlayerEntity) (Object) this);
        }
    }
}
