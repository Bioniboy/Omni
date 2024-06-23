package com.bion.omni.omnimod.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.world.StructureWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StructurePiece.class)
public class StructurePieceMixin {
    @Inject(method="addBlock", at=@At("HEAD"), cancellable = true)
    void removeEndPortals(StructureWorldAccess world, BlockState block, int x, int y, int z, BlockBox box, CallbackInfo ci) {
        if (block.isOf(Blocks.END_PORTAL_FRAME) || (block.isOf(Blocks.END_PORTAL))) {
            ci.cancel();
        }
    }
}
