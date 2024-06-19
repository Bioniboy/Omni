package com.bion.omni.omnimod.block;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.block.entity.BackpackBlockEntity;
import com.bion.omni.omnimod.gui.BackpackGui;
import com.bion.omni.omnimod.item.BackpackItem;
import com.mojang.serialization.Codec;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.block.SimplePolymerBlock;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.VirtualEntityUtils;
import eu.pb4.polymer.virtualentity.api.attachment.ChunkAttachment;
import eu.pb4.polymer.virtualentity.api.elements.InteractionElement;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import eu.pb4.polymer.virtualentity.api.tracker.InteractionTrackedData;
import eu.pb4.polymer.virtualentity.impl.VirtualEntityMod;
import eu.pb4.sgui.virtual.inventory.VirtualInventory;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.*;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.decoration.InteractionEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BackpackBlock extends BarrelBlock implements FactoryBlock {
    private Integer level = 1;
    public Integer getLevel() { return level; }
    public BackpackBlock(Settings settings) {
        super(settings.nonOpaque());
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return Blocks.BARRIER.getDefaultState();
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {


        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BackpackBlockEntity) {
            player.openHandledScreen((BackpackBlockEntity)blockEntity);
        }

        return ActionResult.CONSUME;
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        ItemDisplayElement item = new ItemDisplayElement();
        ElementHolder elementHolder = new ElementHolder();
        ItemStack largeBackpack = Items.LEATHER_CHESTPLATE.getDefaultStack();
        largeBackpack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(850001));
        if (largeBackpack.getItem() == Items.LEATHER_CHESTPLATE) {
            if(world.shouldTick(pos)){
                BlockEntity blockEntity = world.getBlockEntity(pos);
                OmniMod.LOGGER.info("Anything" + ((BackpackBlockEntity)blockEntity).getDyedColor().rgb());
                largeBackpack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(((BackpackBlockEntity)blockEntity).getDyedColor().rgb(), true));
            }
        }
        item.setItem(largeBackpack);
        item.ignorePositionUpdates();
        elementHolder.addElement(item);
        return elementHolder;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BackpackBlockEntity(pos, state);
    }


}
