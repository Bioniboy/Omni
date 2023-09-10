package com.bion.omni.omnimod.powers;

import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;


public class ChangeWandPage extends ImpulsePower {
    @Override
    public String getName() {
        return "Change Wand Page";
    }

    @Override
    public String getId() {
        return "changeWandPage";
    }
    @Override
    public Boolean hasConfig() {
        return true;
    }

    @Override
    public boolean activate(ServerPlayerEntity player) {
        ((Apprentice)player).omni$changeWandPage();
        player.networkHandler.sendPacket(new OverlayMessageS2CPacket(Text.literal("Wand Page: " + ((Apprentice)player).omni$getWandPage()).formatted(((Apprentice)player).omni$getElement().getColor())));
        return true;
    }
}
