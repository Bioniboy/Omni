package com.bion.omni.omnimod.item;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.block.BackpackBlock;
import com.bion.omni.omnimod.gui.BackpackGui;
import eu.pb4.polymer.core.api.item.PolymerBlockItem;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import net.minecraft.block.Block;
import net.minecraft.component.Component;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.config.builder.api.CompositeFilterComponentBuilder;
import org.jetbrains.annotations.Nullable;

public class BackpackItem extends PolymerBlockItem {
    public BackpackItem(Item.Settings settings, Item polymerItem, Block block) {
        super(block, settings, polymerItem);
        OmniMod.LOGGER.info("" + this.getComponents().getTypes());
    }
    SimpleInventory backpackInventory = new SimpleInventory(27);
    private Integer level = 1;

    public int getColor(ItemStack itemStack) {
        return DyedColorComponent.getColor(itemStack, DyeColor.BROWN.getEntityColor());
    }

    public Integer getLevel() { return level; }
    public SimpleInventory get(){
        return backpackInventory;
    }



    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        OmniMod.LOGGER.info("Click: " + clickType);
        return super.onStackClicked(stack, slot, clickType, player);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        OmniMod.LOGGER.info("Click: " + slot);
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, RegistryWrapper.WrapperLookup lookup, @Nullable ServerPlayerEntity player) {
        ItemStack out = PolymerItemUtils.createItemStack(itemStack, tooltipType, lookup, player);
        out.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(850001));
        return out;
    }
}
