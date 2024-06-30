package com.bion.omni.omnimod.block;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.block.entity.BackpackBlockEntity;
import com.bion.omni.omnimod.gui.BackpackGui;
import com.bion.omni.omnimod.item.BackpackItem;
import com.bion.omni.omnimod.util.Apprentice;
import com.mojang.serialization.Codec;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
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
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.ChunkAttachment;
import eu.pb4.polymer.virtualentity.api.elements.InteractionElement;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import eu.pb4.polymer.virtualentity.api.tracker.InteractionTrackedData;
import eu.pb4.polymer.virtualentity.impl.VirtualEntityMod;
import eu.pb4.sgui.virtual.inventory.VirtualInventory;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.*;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.InteractionEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BackpackBlock extends BarrelBlock implements FactoryBlock {
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
        if (player.isSneaking() && player.getInventory().armor.get(2).isEmpty() && blockEntity instanceof BackpackBlockEntity backpackBlockEntity) {
            ItemStack itemStack = this.asItem().getDefaultStack();
            itemStack.applyComponentsFrom(backpackBlockEntity.createComponentMap());
            ItemStack leggings = player.getInventory().armor.get(3);
            player.getInventory().armor.set(2, itemStack);
            player.getInventory().armor.set(3, leggings);
            ((BackpackBlockEntity) blockEntity).clear();
            ((Apprentice)player).omni$setBackpackCooldown(5);
            player.playSoundToPlayer(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER.value(), SoundCategory.MASTER, 1, 1);

            // Remove the backpack block from the ground
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);

            return ActionResult.SUCCESS;  // Indicate the action was successful
        } else if (blockEntity instanceof BackpackBlockEntity backpackBlockEntity) {
            if (!player.isSneaking()) {
                ItemStack itemStack = this.asItem().getDefaultStack();
                itemStack.applyComponentsFrom(backpackBlockEntity.createComponentMap());
                openGUI((ServerPlayerEntity) player, itemStack, backpackBlockEntity);
            }
        }
        return ActionResult.CONSUME;  // Indicate the action was handled
    }
    public void openGUI(ServerPlayerEntity player, ItemStack backpackItem, Inventory inventory){
        BackpackGui GUI = new BackpackGui(player, backpackItem, (backpackItem.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT)).copyNbt().getInt("backpack_level"), inventory);
        GUI.open();
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new BackpackModel();
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);
        ItemStack itemStack = this.asItem().getDefaultStack();
        itemStack.applyComponentsFrom(blockEntity.createComponentMap());
        Block.dropStack(world, pos, itemStack);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BackpackBlockEntity(pos, state);
    }

    @Override
    public boolean tickElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return true;
    }

    public static final class BackpackModel extends BlockModel {
        public BackpackModel() {
            ItemDisplayElement item = new ItemDisplayElement();
            ItemStack largeBackpack = Items.FIREWORK_STAR.getDefaultStack();

            item.setItem(largeBackpack);
            item.ignorePositionUpdates();
            this.addElement(item);
        }

        @Override
        protected void onTick() {
            super.onTick();
            if (getAttachment() == null) return;
            BlockEntity blockEntity = getAttachment().getWorld().getBlockEntity(BlockPos.ofFloored(getAttachment().getPos()));
            ((ItemDisplayElement)getElements().getFirst()).getItem().set(DataComponentTypes.FIREWORK_EXPLOSION, new FireworkExplosionComponent(FireworkExplosionComponent.Type.CREEPER, IntList.of(((BackpackBlockEntity) blockEntity).getDyedColor().rgb()), IntList.of(), false, false));
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putInt("backpack_level", ((BackpackBlockEntity) blockEntity).getLevel());
            ((ItemDisplayElement)getElements().getFirst()).getItem().set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(850000 + ((BackpackBlockEntity) blockEntity).getLevel()));
        }
    }
}
