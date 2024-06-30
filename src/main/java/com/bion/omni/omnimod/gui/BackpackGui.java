package com.bion.omni.omnimod.gui;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.item.BackpackItem;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;

public class BackpackGui extends SimpleGui {
    private Inventory backpackInventory;
    private ItemStack backpackItem;
    private int level;

    public BackpackGui(ServerPlayerEntity player, ItemStack backpackItemStack, int level, Inventory inventory) {
        super(switch (level){
            case 1: yield ScreenHandlerType.GENERIC_9X1;
            case 2: yield ScreenHandlerType.GENERIC_9X2;
            case 3: yield ScreenHandlerType.GENERIC_9X3;
            case 4: yield ScreenHandlerType.GENERIC_9X4;
            case 5: yield ScreenHandlerType.GENERIC_9X5;
            case 6: yield ScreenHandlerType.GENERIC_9X6;
            default:
                throw new IllegalStateException("Unexpected value: " + level);
        }, player, false);
        this.level = level;
        backpackInventory = inventory;
        this.backpackItem = backpackItemStack;
        OmniMod.LOGGER.info("Gui");
        for (int i = 0; i < 9 * level; i++) {
            setSlotRedirect(i, new Slot(backpackInventory, i, 0, 0));
            if (!backpackInventory.getStack(i).isEmpty()) {
                OmniMod.LOGGER.info("I: " + i + " Item: " + backpackInventory.getStack(i).getItem().getName());
            }
        }
        MutableText title = Text.literal("");
        if(backpackItemStack.hasEnchantments()){
            title.append("blah");
        }
        if(backpackItemStack.get(DataComponentTypes.CUSTOM_NAME) != null){
            title.append(backpackItemStack.get(DataComponentTypes.CUSTOM_NAME).toString());
        }else{
            title.append(BackpackItem.getBackpackName(level));
        }
        this.setTitle(title);
    }

    @Override
    public void close(boolean screenHandlerIsClosed) {
        super.close(screenHandlerIsClosed);
        DefaultedList<ItemStack> itemStacks = DefaultedList.ofSize(9 * level, ItemStack.EMPTY);
        for(int i = 0; i < 9 * level; i++){
            ItemStack itemStack = backpackInventory.getStack(i);
            itemStacks.set(i, itemStack);
        }
        backpackItem.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(itemStacks));
    }
}
