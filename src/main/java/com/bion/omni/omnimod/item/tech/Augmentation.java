package com.bion.omni.omnimod.item.tech;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtInt;
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
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipContext context, @Nullable ServerPlayerEntity player) {

        ItemStack item = PolymerItemUtils.createItemStack(itemStack, context, player);
        item.setSubNbt("CustomModelData", NbtInt.of(modelID));
        return item;
    }
}
