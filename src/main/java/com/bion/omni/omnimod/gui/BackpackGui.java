package com.bion.omni.omnimod.gui;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment;
import eu.pb4.polymer.virtualentity.api.elements.BlockDisplayElement;
import eu.pb4.polymer.virtualentity.api.elements.TextDisplayElement;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import org.joml.Vector3f;

public class BackpackGui extends SimpleGui {
    private SimpleInventory backpackInventory;
    public BackpackGui(ServerPlayerEntity player, SimpleInventory backpackInventory){
        super(ScreenHandlerType.SHULKER_BOX, player, false);
        this.backpackInventory = backpackInventory;
        for(int i = 0; i < 27; i++){
            setSlotRedirect(i, new Slot(backpackInventory, i, 0, 0));
        }
    }
    public SimpleInventory getBackpackInventory(){
        return backpackInventory;
    }

    @Override
    public void close(boolean screenHandlerIsClosed) {
        super.close(screenHandlerIsClosed);
    }
}
