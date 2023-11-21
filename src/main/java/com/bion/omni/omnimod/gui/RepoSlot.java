package com.bion.omni.omnimod.gui;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class RepoSlot extends Slot {
    DivineRepoGui gui;
    public RepoSlot(Inventory inventory, int index, DivineRepoGui gui) {
        super(inventory, index, 0, 0);
        this.gui = gui;
    }

    @Override
    public void setStack(ItemStack stack) {
        super.setStack(stack);
        gui.shiftInventory(this.getIndex());
    }
}
