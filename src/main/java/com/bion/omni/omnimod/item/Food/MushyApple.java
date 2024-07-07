package com.bion.omni.omnimod.item.Food;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class MushyApple extends Item implements PolymerItem {
    public MushyApple(Settings settings) {
        super(settings);

    }
    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return Items.HONEY_BOTTLE;
    }
    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, RegistryWrapper.WrapperLookup lookup, @Nullable ServerPlayerEntity player) {

        ItemStack item = PolymerItemUtils.createItemStack(itemStack, tooltipType, lookup, player);
        item.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(29001));
        return item;
    }

}

