package com.bion.omni.omnimod.item.tome;

import com.bion.omni.omnimod.element.Clarity;
import com.bion.omni.omnimod.element.Element;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class Libronomicon extends Tome {

    private final Element element = new Clarity();
    public Libronomicon(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }
    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipContext context, @Nullable ServerPlayerEntity player) {
        ItemStack out = PolymerItemUtils.createItemStack(itemStack, context, player);
        NbtCompound nbt = out.getNbt();
        assert nbt != null;
        nbt.putInt("CustomModelData", 850004);
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
        return "Libronomicon";
    }

    @Override
    public char getBorder1() {
        return '\uE007';
    }

    @Override
    public char getBorder2() {
        return '\uE008';
    }
}
