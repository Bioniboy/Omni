package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.power.magic.PotionCrafting;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LeveledCauldronBlock.class)
public abstract class PotionCauldron extends AbstractCauldronBlock {

    /**
     * Constructs a cauldron block.
     *
     * <p>The behavior map must match {@link CauldronBehavior#createMap} by providing
     * a nonnull value for <em>all</em> items.
     *
     * @param settings
     * @param behaviorMap the map containing cauldron behaviors for each item
     */
    public PotionCauldron(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
        super(settings, behaviorMap);
    }



    @Inject(method="onEntityCollision", at=@At("HEAD"))
    public void onItemCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (isFull(state) && entity instanceof ItemEntity item) {
            if (item.getOwner() != null && ((Apprentice)item.getOwner()).omni$getPowerById("potionCrafting") != null) {
                ((PotionCrafting)((Apprentice)item.getOwner()).omni$getPowerById("potionCrafting")).addItem(item.getStack(), pos, (ServerPlayerEntity) item.getOwner());
                entity.discard();
            }

        }
    }
}
