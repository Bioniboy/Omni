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

public class Soleonomicon extends Tome {
    private final Element element = new Storm();

    public Soleonomicon(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipContext context, @Nullable ServerPlayerEntity player) {
        ItemStack out = PolymerItemUtils.createItemStack(itemStack, context, player);
        NbtCompound nbt = out.getNbt();
        assert nbt != null;
        nbt.putInt("CustomModelData", 850008);
        out.setNbt(nbt);
        return out;
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public String getTitleIndent() {
        return "   ";
    }

    @Override
    public String getTitle() {
        return "Soleonomicon";
    }

    @Override
    public char getBorder1() {
        return '\uE015';
    }

    @Override
    public char getBorder2() {
        return '\uE016';
    }
}
