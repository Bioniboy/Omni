package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.item.ModItems;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(CauldronBehavior.class)
public interface CauldronBehaviorMixin {
    @Inject(method = "registerBehavior", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void makeBackpacksWashable(CallbackInfo ci, Map<Item, CauldronBehavior> map2){
        map2.put(ModItems.BACKPACK_ITEM, CauldronBehavior.CLEAN_DYEABLE_ITEM);
    }
}
