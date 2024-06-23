package com.bion.omni.omnimod.mixin;

import net.minecraft.network.packet.s2c.play.ChangeUnlockedRecipesS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerRecipeBook;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(ServerRecipeBook.class)
public class ServerRecipeBookMixin {

    @Inject(method = "sendUnlockRecipesPacket", at = @At("HEAD"), cancellable = true)
    private void onSendUnlockRecipesPacket(ChangeUnlockedRecipesS2CPacket.Action action, ServerPlayerEntity player, List<Identifier> recipeIds, CallbackInfo ci) {
        if (action == ChangeUnlockedRecipesS2CPacket.Action.ADD) {
            // Prevent the recipe toast from being displayed by sending an empty toBeDisplayed list
            player.networkHandler.sendPacket(new ChangeUnlockedRecipesS2CPacket(
                    ChangeUnlockedRecipesS2CPacket.Action.INIT,
                    recipeIds,
                    List.copyOf(recipeIds), // Send an empty list to prevent the toast
                    ((ServerRecipeBook) (Object) this).getOptions()
            ));
            ci.cancel();
        }
    }
}
