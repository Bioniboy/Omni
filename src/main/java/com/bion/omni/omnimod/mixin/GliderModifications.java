package com.bion.omni.omnimod.mixin;

import eu.pb4.glideaway.item.DyeableHangGliderItem;
import eu.pb4.glideaway.item.GlideItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GlideItems.class)
public abstract class GliderModifications {
    @ModifyConstant(
        method="<clinit>",
        constant = @Constant(intValue = 250),
        slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=hang_glider", ordinal = 0))
    )
    private static int replaceHangGliderMaxDamage(int maxDamage) {
        return 1000;
    }

    @ModifyConstant(
        method="<clinit>",
        constant = @Constant(intValue = 350),
        slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=cherry_hang_glider", ordinal = 0))
    )
    private static int replaceCherryGliderMaxDamage(int maxDamage) {
        return 1500;
    }

    @ModifyConstant(
            method="<clinit>",
            constant = @Constant(intValue = 350),
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=sculk_hang_glider", ordinal = 0))
    )
    private static int replaceSculkGliderMaxDamage(int maxDamage) {
        return 1500;
    }

    @ModifyConstant(
            method="<clinit>",
            constant = @Constant(intValue = 350),
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=azalea_hang_glider", ordinal = 0))
    )
    private static int replaceAzaleaGliderMaxDamage(int maxDamage) {
        return 1500;
    }
}
