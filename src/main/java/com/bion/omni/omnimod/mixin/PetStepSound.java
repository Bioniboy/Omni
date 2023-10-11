package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.entity.custom.Pet;
import com.bion.omni.omnimod.mixin.accessor.EntityAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class PetStepSound {
    @Shadow
    public World getWorld() {throw new AssertionError();};
    @Inject(method="playStepSound", at=@At("HEAD"), cancellable = true)
    public void modifyStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
        if ((Object)this instanceof Pet pet && pet.getCustomType() != null) {
            ((EntityAccessor) pet.getCustomType().create(getWorld())).invokePlayStepSound(pos, state);
            ci.cancel();
        }
    }
}
