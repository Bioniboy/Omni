package com.bion.omni.omnimod.item.tech;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtInt;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class Augmentation extends Item implements PolymerItem {
    private final int modelID;
    public Augmentation(Settings settings, int modelID) {
        super(settings);
        this.modelID = modelID;
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return Items.STICK;
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, RegistryWrapper.WrapperLookup lookup, @Nullable ServerPlayerEntity player) {

        ItemStack item = PolymerItemUtils.createItemStack(itemStack, tooltipType, lookup, player);
        item.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(modelID));
        return item;
    }
}
