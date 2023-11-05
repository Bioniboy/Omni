package com.bion.omni.omnimod.power;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.util.Apprentice;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.intprovider.ClampedIntProvider;

import java.util.Hashtable;
import java.util.function.Function;

public class Mana {
    public static void manaUpdate(Apprentice player) {

        double prevMana = player.omni$getMana();
        Hashtable<String, Double> costs = player.omni$getCosts();
        for (var costId : costs.keySet()) {

            player.omni$changeMana(-costs.get(costId));
        }
        costs.clear();
        //player.getElement().isInDomain((ServerPlayerEntity) player);
        if (player.omni$getMana() < player.omni$getManaMax()) {
            player.omni$changeMana(player.omni$getManaRegen());
            if (player.omni$getElement().isInDomain((ServerPlayerEntity) player)) {
                OmniMod.LOGGER.info("In domain");
                player.omni$changeMana(Math.min(player.omni$getManaRegen(), 1));
            }
            if (player.omni$getMana() > player.omni$getManaMax()) {
                player.omni$setMana(player.omni$getManaMax());
            }
        }
        if (player.omni$getMana() != prevMana) {
            manaShow((ServerPlayerEntity) player);
        }

    }

    public static void manaShow(ServerPlayerEntity player) {
        Function<Text, Packet<?>> constructor = OverlayMessageS2CPacket::new;
        double ratio = (((Apprentice)player).omni$getMana() * 100) / ((Apprentice)player).omni$getManaMax();
        MutableText manaBar = Text.literal("\uf80b\uf808\uf806");
        String numbers = Math.round((float)((Apprentice)player).omni$getMana()) + "/" + ((Apprentice)player).omni$getManaMax();
//        int spaces = 0;
//        while (spaces + numbers.length() < 15) {
//            manaBar.append(" ");
//            spaces += 1;
//        }
        manaBar.append(Text.literal("\ue001").formatted(Formatting.WHITE));
        for (int i = 0; i < 100; i++) {
            manaBar.append(Text.literal("\uf801"));
            if (i < ratio) {
                manaBar.append(Text.literal("\uE002"));
            } else if (i > ratio) {
                manaBar.append(Text.literal("\uE004"));
            } else {
                manaBar.append(Text.literal("\uE003"));
            }
        }
        manaBar.append("\uf801\uE001\uf80A\uF809\uf803");
        for (int i = 0; i < numbers.length() * 2.5; i++) {
            manaBar.append("\uf801");
        }
        Formatting color = ((Apprentice)player).omni$getElement() != null ? ((Apprentice)player).omni$getElement().getColor() : Formatting.WHITE;
        manaBar.append(Text.literal(numbers + "\uf81A").formatted(color));
        for (int i = 0; i < numbers.length() * 2.5; i++) {
            manaBar.append("\uf801");
        }

        player.networkHandler.sendPacket(constructor.apply(manaBar));
    }

    public static void manaInitialize(Apprentice player) {
        player.omni$setMana(60);
        player.omni$setManaRegenLevel(1);
        player.omni$setManaMaxLevel(1);
    }
}
