package com.bion.omni.omnimod.item.tome;

import com.bion.omni.omnimod.element.Air;
import com.bion.omni.omnimod.element.Element;
import com.bion.omni.omnimod.element.Water;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class Hydronomicon extends Tome {
    private final Element element = new Water();

    public Hydronomicon(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, RegistryWrapper.WrapperLookup lookup, @Nullable ServerPlayerEntity player) {
        ItemStack out = PolymerItemUtils.createItemStack(itemStack, tooltipType, lookup, player);
        out.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(850002));
        return out;
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public String getTitleIndent() {
        return "    ";
    }

    @Override
    public String getTitle() {
        return "Hydronomicon";
    }

    @Override
    public char getBorder1() {
        return '\uE00d';
    }

    @Override
    public char getBorder2() {
        return '\uE00e';
    }
}
