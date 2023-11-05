package com.bion.omni.omnimod.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
        return switch (itemStack.getOrCreateNbt().getInt("Value")) {
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
        return Text.literal("+" + stack.getOrCreateNbt().getInt("Value") + " Influence");
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        int value = stack.getOrCreateNbt().getInt("Value");
        if (value > 15)
            return Rarity.EPIC;
        if (value > 8)
            return Rarity.RARE;
        return Rarity.UNCOMMON;
    }
}
