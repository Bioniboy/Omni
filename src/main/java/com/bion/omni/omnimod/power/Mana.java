package com.bion.omni.omnimod.power;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.util.Apprentice;

import com.bion.omni.omnimod.util.CustomCharDict;
import com.bion.omni.omnimod.util.DisplayIcon;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

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
//        if (player.omni$getMana() != prevMana) {
//            manaShow((ServerPlayerEntity) player);
//        }

    }

//    private static Text positionIcon(int p_xAxisTranslation, int p_width, Text p_icon){
//        p_xAxisTranslation *= -1;
//        //check if odd
//        String odd = "0";
//        int lastDigit = p_width % 10;
//        if(lastDigit == 1 || lastDigit == 3 || lastDigit == 5 || lastDigit == 7 || lastDigit == 9){
//            odd = "-2";
//        }
//
//        //compensate for something not being centered
//        int notCentered = -1;
//        if(p_xAxisTranslation != 0){
//            notCentered = 1;
//        }
//
//        //compute pre-space
//        MutableText preSpace = Text.translatable("space." + (-1*((p_width/2) + p_xAxisTranslation)));
//        preSpace.append(Text.translatable("space." + odd));
//
//        //compute post-space
//        MutableText postSpace = Text.translatable("space." + (notCentered*((p_width/2) + p_xAxisTranslation)));
//        postSpace.append(Text.translatable("space." + odd));
//
//        //Make final text
//        MutableText positionedIcon = Text.literal("");
//        positionedIcon.append(preSpace);
//        positionedIcon.append(p_icon);
//        positionedIcon.append(postSpace);
//
//        return positionedIcon;
//    }

//    public static void manaShow(ServerPlayerEntity player) {
//        Function<Text, Packet<?>> constructor = OverlayMessageS2CPacket::new;
//        double ratio = (((Apprentice)player).omni$getMana() * 100) / ((Apprentice)player).omni$getManaMax();
//        MutableText manaBar = Text.literal("");
//
//        manaBar.append(positionIcon(0, 182, Text.literal(CharsMap.getChar(CustomCharDict.CharName.Hotbar_Grid).getSymbol())));
//
//        manaBar.append(positionIcon(-90, 1, Text.literal(CharsMap.getChar(CustomCharDict.CharName.AIR_FILL_0).getSymbol())));
//
//
//
//
////        manaBar.append(Text.literal(CharsMap.getChar(CustomCharDict.CharName.MANA_BAR_BORDER).getSymbol()).formatted(Formatting.WHITE));
////        for (int i = 0; i < 100; i++) {
////            manaBar.append(Text.literal(CharsMap.getChar(CustomCharDict.CharName.NEG_1).getSymbol()));
////            if (i < ratio) {
////                manaBar.append(Text.literal(CharsMap.getChar(CustomCharDict.CharName.MANA_BAR_FILL).getSymbol()));
////            } else if (i > ratio) {
////                manaBar.append(Text.literal(CharsMap.getChar(CustomCharDict.CharName.MANA_BAR_EMPTY).getSymbol()));
////            } else {
////                manaBar.append(Text.literal(CharsMap.getChar(CustomCharDict.CharName.MANA_BAR_END).getSymbol()));
////            }
////        }
//        //manaBar.append(Text.literal(getNegSpace(100)));
////        for (int i = 0; i < numbers.length() * 2.5; i++) {
////            manaBar.append("\uf801");
////        }
////        Formatting color = ((Apprentice)player).omni$getElement() != null ? ((Apprentice)player).omni$getElement().getColor() : Formatting.WHITE;
////        //manaBar.append(Text.literal(numbers + "\uf81A").formatted(color));
////        for (int i = 0; i < numbers.length() * 2.5; i++) {
////            manaBar.append("\uf801");
////        }
//
//
//        player.networkHandler.sendPacket(constructor.apply(manaBar));
//    }

    public static void manaInitialize(Apprentice player) {
        player.omni$setMana(60);
        player.omni$setManaRegenLevel(1);
        player.omni$setManaMaxLevel(1);
    }

}
