package com.bion.omni.omnimod.mixin.accessor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ShulkerBulletEntity.class)
public interface ShulkerBulletEntityAccessor {
    @Mutable
    @Accessor("target")
    void setTarget(Entity target);

    @Mutable
    @Accessor("direction")
    void setDirection(Direction direction);

    @Invoker
    void invokeChangeTargetDirection(@Nullable Direction.Axis axis);
}
