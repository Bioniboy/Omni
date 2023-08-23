package com.bion.omni.omnimod.mixin.accessor;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerPlayerEntity.class)
public interface PlayerEntityFallStartPositionAccessor {
    @Accessor
    Vec3d getFallStartPos();

    @Accessor
    void setFallStartPos(Vec3d pos);
}
