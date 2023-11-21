package com.bion.omni.omnimod.power.magic;

import com.bion.omni.omnimod.power.ContinuousPower;
import com.bion.omni.omnimod.power.ImpulsePower;
import eu.pb4.sgui.api.gui.SimpleGui;
import eu.pb4.sgui.api.gui.SimpleGuiBuilder;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class RemoteEnderChest extends ImpulsePower {
    public RemoteEnderChest(int level) {
        super(level);
    }
    boolean active = true;

    @Override
    public String getName() {
        return "Remote Ender Chest";
    }

    @Override
    public String getId() {
        return "remoteEnderChest";
    }

    @Override
    public String getAdvancementId() {
        return "remote_echest";
    }

    @Override
    public Integer getInfluenceCost() {
        return 20;
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        if (super.activate(user)) {
//            SimpleGuiBuilder chestBuilder = new SimpleGuiBuilder(ScreenHandlerType.GENERIC_9X3, false);
//            for (int i = 0; i < user.getEnderChestInventory().size(); i++) {
//                chestBuilder.setSlotRedirect(i, new Slot(user.getEnderChestInventory(), i, 0, 0));
//            }
//            chestBuilder.setTitle(Text.literal("Ender Chest"));
//            chestBuilder.build(user).open();
//            SimpleGui chestBuilder = new SimpleGui(ScreenHandlerType.GENERIC_9X2, user, false);
//            chestBuilder.open();
            active = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean isTicking() {
        return !active;
    }

    @Override
    public void tick(ServerPlayerEntity user) {
        SimpleGui chestBuilder = new SimpleGui(ScreenHandlerType.GENERIC_9X4, user, false);
        for (int i = 0; i < user.getEnderChestInventory().size(); i++) {
            chestBuilder.setSlotRedirect(i, new Slot(user.getEnderChestInventory(), i, 0, 0));
        }
        chestBuilder.setTitle(Text.literal("Ender Chest"));

        user.closeHandledScreen();
        user.currentScreenHandler.disableSyncing();
        user.currentScreenHandler.updateToClient();
        chestBuilder.open();
        active = true;
    }


}
