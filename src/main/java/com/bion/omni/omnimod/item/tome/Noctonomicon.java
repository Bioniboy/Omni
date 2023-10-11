package com.bion.omni.omnimod.item.tome;

import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import com.bion.omni.omnimod.element.Element;
import com.bion.omni.omnimod.element.Moon;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class Noctonomicon extends Tome {
    private final Element element = new Moon();

    public Noctonomicon(Settings settings, Item polymerItem) { super(settings, polymerItem); }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipContext context, @Nullable ServerPlayerEntity player) {
        ItemStack out = PolymerItemUtils.createItemStack(itemStack, context, player);
        NbtCompound nbt = out.getNbt();
        assert nbt != null;
        nbt.putInt("CustomModelData", 850007);
        out.setNbt(nbt);
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
