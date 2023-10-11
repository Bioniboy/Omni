package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.GuardianEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class TeamMixin {
    @Inject(method="isTeammate", at=@At("HEAD"), cancellable = true)
    void isTeammate(Entity other, CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this instanceof GuardianEntity && other instanceof Apprentice apprentice) {
            if (apprentice.omni$getConfigValue("guardianPassivity") == 1) {
                cir.setReturnValue(true);
            }
        }
    }
}
