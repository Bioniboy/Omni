package com.bion.omni.omnimod.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import org.jetbrains.annotations.Nullable;

public class InfluenceToken extends Item implements PolymerItem {

    public InfluenceToken(Settings settings) {
        super(settings);
    }



    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return switch (itemStack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getInt("Value")) {
            case 30:
                yield Items.NETHER_STAR;
            case 15:
                yield Items.ENCHANTED_GOLDEN_APPLE;
            case 10:
                yield Items.DIAMOND;
            case 8:
                yield Items.EMERALD;
            default:
                yield Items.GOLD_INGOT;
        };
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.literal("+" + stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getInt("Value") + " Influence");
    }
    
    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, RegistryWrapper.WrapperLookup lookup, @Nullable ServerPlayerEntity player) {
        int value = itemStack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getInt("Value");
        Rarity rarity = Rarity.UNCOMMON;
        if (value > 15)
            rarity = Rarity.EPIC;
        if (value > 8)
            rarity = Rarity.RARE;
        itemStack.set(DataComponentTypes.RARITY, rarity);
        return PolymerItemUtils.createItemStack(itemStack, tooltipType, lookup, player);
    }
}
