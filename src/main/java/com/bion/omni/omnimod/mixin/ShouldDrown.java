package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.elements.Life;
import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class ShouldDrown {
    @Redirect(method="baseTick", at=@At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isSubmergedIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    boolean shouldDrown(LivingEntity entity, TagKey tagKey) {
        if (entity instanceof Apprentice player && player.omni$getElement() instanceof Life) {
            Power power = player.omni$getPowerById("transformFish");
            if (power != null && player.omni$getConfigValue("transformFish") == 1) {
                return !entity.isSubmergedInWater();
            }
        }
        return entity.isSubmergedInWater();
    }
}
