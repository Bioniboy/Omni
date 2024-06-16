package com.bion.omni.omnimod.item.tome;

import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import com.bion.omni.omnimod.element.Element;
import com.bion.omni.omnimod.element.Moon;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class Noctonomicon extends Tome {
    private final Element element = new Moon();

    public Noctonomicon(Settings settings, Item polymerItem) { super(settings, polymerItem); }

    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, RegistryWrapper.WrapperLookup lookup, @Nullable ServerPlayerEntity player) {
        ItemStack out = PolymerItemUtils.createItemStack(itemStack, tooltipType, lookup, player);
        out.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(850007));
        return out;
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public String getTitleIndent() {
        return "    \uf804";
    }

    @Override
    public String getTitle() {
        return "Noctonomicon";
    }

    @Override
    public char getBorder1() {
        return '\uE011';
    }

    @Override
    public char getBorder2() {
        return '\uE012';
    }
}
