package com.bion.omni.omnimod.gui;

import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class DivineRepoGui extends SimpleGui {
    private SimpleInventory repoInventory;
    public DivineRepoGui(ServerPlayerEntity player, SimpleInventory repoInventory) {
        super(ScreenHandlerType.GENERIC_9X6, player, false);
        this.repoInventory = repoInventory;
        refreshInventory();


    }
    public void refreshInventory(){
        DefaultedList<ItemStack> repoList = this.repoInventory.stacks;
        for(int i = 0; i < repoList.size(); i++){
            setSlotRedirect(i, new RepoSlot(repoInventory, i,this));
            if(repoList.get(i).isEmpty()){
                break;
            }
        }
    }
    public void shiftInventory(int index){
        for(int i = index; i< repoInventory.size(); i++){
            if(!repoInventory.getStack(i+1).isEmpty()){
                repoInventory.setStack(i, repoInventory.getStack(i+1));
                repoInventory.removeStack(i+1);
            }else{
                break;
            }
        }
        refreshInventory();
    }

}
