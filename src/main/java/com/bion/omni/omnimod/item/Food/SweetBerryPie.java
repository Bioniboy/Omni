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

public class SweetBerryPie extends Item implements PolymerItem {
    public SweetBerryPie(Settings settings) {
        super(settings);

    }
    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return Items.PUMPKIN_PIE;
    }

//    @Override
//    public Item ShapelessRecipe({
//        "type": "minecraft:crafting_shapeless",
//                "category": "misc",
//                "ingredients": [
//        {
//            "item": "minecraft:sweet_berries"
//        },
//        {
//            "item": "minecraft:sugar"
//        },
//        {
//            "item": "minecraft:egg"
//        }
//  ],
//        "result": {
//            "count": 1,
//                    "id": "minecraft:pumpkin_pie"
//        })

//    }
    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, RegistryWrapper.WrapperLookup lookup, @Nullable ServerPlayerEntity player) {

        ItemStack item = PolymerItemUtils.createItemStack(itemStack, tooltipType, lookup, player);
        item.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(1));
        return item;
    }
}
