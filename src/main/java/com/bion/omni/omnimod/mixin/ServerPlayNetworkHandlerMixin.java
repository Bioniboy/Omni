package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.item.BackpackItem;
import com.bion.omni.omnimod.util.Apprentice;
import com.mojang.datafixers.kinds.App;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.RecipeBookDataC2SPacket;
import net.minecraft.network.packet.c2s.play.RecipeCategoryOptionsC2SPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin extends ServerCommonNetworkHandler
        implements ServerPlayPacketListener,
        PlayerAssociatedNetworkHandler,
        TickablePacketListener {
    @Shadow public ServerPlayerEntity player;
    @Unique
    private boolean inventoryOpen = true;

    public ServerPlayNetworkHandlerMixin(MinecraftServer server, ClientConnection connection, ConnectedClientData clientData) {
        super(server, connection, clientData);
    }

    @Inject(method = "onRecipeCategoryOptions", at = @At("TAIL"))
    public void detectBookButtonPress(RecipeCategoryOptionsC2SPacket packet, CallbackInfo ci){
        ((Apprentice)player).omni$setIsRecipeBookOpen(!((Apprentice) player).omni$getIsRecipeBookOpen());
    }

    @Inject(method = "onRecipeBookData", at = @At("TAIL"))
    public void stalkRecipeBook(RecipeBookDataC2SPacket packet, CallbackInfo ci){
        if(!((Apprentice)player).omni$getIsRecipeBookOpen()){
            //close book
        }
        if(player.isSneaking() && !inventoryOpen && player.getInventory().armor.get(2).getItem() instanceof BackpackItem){
            OmniMod.LOGGER.info("inventory open: " + inventoryOpen);
            player.closeHandledScreen();
            ((BackpackItem) player.getInventory().armor.get(2).getItem()).openGUI(player, player.getInventory().armor.get(2));
        }
        inventoryOpen = true;
    }
    @Inject(method = "onCloseHandledScreen", at = @At("TAIL"))
    public void stalkCloseGUI(CloseHandledScreenC2SPacket packet, CallbackInfo ci){

        if(inventoryOpen){
            RecipeManager recipeManager = server.getRecipeManager();
            Collection<RecipeEntry<?>> allRecipes = recipeManager.values();
            player.lockRecipes(allRecipes);
            player.unlockRecipes(allRecipes);

            if(!((Apprentice)player).omni$getIsRecipeBookOpen()){
               //open Recipe Book
            }

            inventoryOpen = false;
        }
    }
    @Inject(method = "onPlayerInteractBlock", at = @At("TAIL"))
    public void onPlayerInteractBlock(PlayerInteractBlockC2SPacket packet, CallbackInfo ci) {
        ServerPlayNetworkHandler handler = (ServerPlayNetworkHandler) (Object) this;
        PlayerEntity player = handler.player;
        BlockHitResult blockHitResult = packet.getBlockHitResult();
        BlockPos blockPos = blockHitResult.getBlockPos();
        World world = player.getWorld();

        // Check if the player is sneaking, main hand is empty, and armor slot has a BackpackItem
        if (player.isSneaking() && player.getMainHandStack().isEmpty() && player.getInventory().armor.get(2).getItem() instanceof BackpackItem && ((Apprentice)player).omni$getBackpackCooldown() == 0) {
            ItemStack backpack = player.getInventory().armor.get(2);
            Direction direction = blockHitResult.getSide();
            ActionResult actionResult = world.getBlockState(blockPos).onUse(world, player, blockHitResult);

            // Only place the backpack if the block interaction was not successful
            if (!actionResult.isAccepted()) {
                BlockHitResult result = world.raycast(new RaycastContext(player.getEyePos(), player.getEyePos().add(player.getRotationVector().multiply(5)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, player));
                backpack.useOnBlock(new ItemPlacementContext(player, Hand.MAIN_HAND, backpack, result));
                player.swingHand(Hand.MAIN_HAND, true);
                player.playSoundToPlayer(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER.value(), SoundCategory.MASTER, 1, 1);
            }
        }
    }
}