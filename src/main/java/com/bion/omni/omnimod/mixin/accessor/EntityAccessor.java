package com.bion.omni.omnimod.mixin.accessor;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("FLAGS")
    static TrackedData<Byte> getFLAGS() {
        throw new AssertionError();
    }
    @Accessor("hasVisualFire")
    void setHasVisualFire(boolean fire);

    @Invoker
    void invokePlayStepSound(BlockPos pos, BlockState state);

}
