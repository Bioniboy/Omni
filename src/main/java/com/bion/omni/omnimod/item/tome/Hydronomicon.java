package com.bion.omni.omnimod.item.tome;

import com.bion.omni.omnimod.element.Air;
import com.bion.omni.omnimod.element.Element;
import com.bion.omni.omnimod.element.Water;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class Hydronomicon extends Tome {
    private final Element element = new Water();

    public Hydronomicon(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipContext context, @Nullable ServerPlayerEntity player) {
        ItemStack out = PolymerItemUtils.createItemStack(itemStack, context, player);
        NbtCompound nbt = out.getNbt();
        assert nbt != null;
        nbt.putInt("CustomModelData", 850002);
        out.setNbt(nbt);
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
