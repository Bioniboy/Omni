package com.bion.omni.omnimod.item.tome;

import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import com.bion.omni.omnimod.elements.Element;
import com.bion.omni.omnimod.elements.Storm;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class Cyclonomicon extends Tome {
    private final Element element = new Storm();

    public Cyclonomicon(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipContext context, @Nullable ServerPlayerEntity player) {
        ItemStack out = PolymerItemUtils.createItemStack(itemStack, context, player);
        NbtCompound nbt = out.getNbt();
        assert nbt != null;
        nbt.putInt("CustomModelData", 850003);
        out.setNbt(nbt);
        return out;
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public String getTitleIndent() {
        return "    \uf803";
    }

    @Override
    public String getTitle() {
        return "Cyclonomicon";
    }

    @Override
    public char getBorder1() {
        return '\uE009';
    }

    @Override
    public char getBorder2() {
        return '\uE00a';
    }
}
