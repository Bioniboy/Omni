package com.bion.omni.omnimod.util;

import com.bion.omni.omnimod.OmniMod;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.HashMap;

public class DisplayIcon {
    public DisplayIcon(iconName p_icon, int p_lifeExpectancy, ServerPlayerEntity p_player) {
        name = p_icon;
        player = p_player;
        lifeExpectancy = p_lifeExpectancy;
        ticksLived = 0;
        isFading = false;
        isFaded = false;
        fadeCounter = 0;
        if (p_lifeExpectancy == 0) {
            isMortal = false;
        } else {
            isMortal = true;
        }
        activeManaBar = airManaBar;

        //Air Bar
        {
            //Air Base
            airManaBar.put(manaBarPieces.EMPTY, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_EMPTY));
            //Air Vignette
            airManaBar.put(manaBarPieces.VIGNETTE, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_VIGNETTE));
            //Air Fill
            {
                airManaBar.put(manaBarPieces.FILL_1, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_FILL_1));
                airManaBar.put(manaBarPieces.FILL_2, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_FILL_2));
                airManaBar.put(manaBarPieces.FILL_3, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_FILL_3));
                airManaBar.put(manaBarPieces.FILL_4, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_FILL_4));
                airManaBar.put(manaBarPieces.FILL_5, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_FILL_5));
                airManaBar.put(manaBarPieces.FILL_6, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_FILL_6));
                airManaBar.put(manaBarPieces.FILL_7, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_FILL_7));
                airManaBar.put(manaBarPieces.FILL_8, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_FILL_8));
                airManaBar.put(manaBarPieces.FILL_9, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_FILL_9));
                airManaBar.put(manaBarPieces.FILL_0, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_FILL_0));
            }
            //Air Numbers
            {
                airManaBar.put(manaBarPieces.NUM_1, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_1));
                airManaBar.put(manaBarPieces.NUM_2, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_2));
                airManaBar.put(manaBarPieces.NUM_3, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_3));
                airManaBar.put(manaBarPieces.NUM_4, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_4));
                airManaBar.put(manaBarPieces.NUM_5, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_5));
                airManaBar.put(manaBarPieces.NUM_6, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_6));
                airManaBar.put(manaBarPieces.NUM_7, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_7));
                airManaBar.put(manaBarPieces.NUM_8, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_8));
                airManaBar.put(manaBarPieces.NUM_9, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_9));
                airManaBar.put(manaBarPieces.NUM_0, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_0));
            }
            //Air Base Fade
            {
                airManaBar.put(manaBarPieces.BASE_FADE_1, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_BASE_FADE_1));
                airManaBar.put(manaBarPieces.BASE_FADE_2, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_BASE_FADE_2));
                airManaBar.put(manaBarPieces.BASE_FADE_3, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_BASE_FADE_3));
                airManaBar.put(manaBarPieces.BASE_FADE_4, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_BASE_FADE_4));
                airManaBar.put(manaBarPieces.BASE_FADE_5, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_BASE_FADE_5));
                airManaBar.put(manaBarPieces.BASE_FADE_6, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_BASE_FADE_6));
                airManaBar.put(manaBarPieces.BASE_FADE_7, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_BASE_FADE_7));
                airManaBar.put(manaBarPieces.BASE_FADE_8, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_BASE_FADE_8));
                airManaBar.put(manaBarPieces.BASE_FADE_9, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_BASE_FADE_9));
                airManaBar.put(manaBarPieces.BASE_FADE_0, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_BASE_FADE_0));
            }
            //Air Vignette Fade
            {
                airManaBar.put(manaBarPieces.VIGNETTE_FADE_1, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_VIGNETTE_FADE_1));
                airManaBar.put(manaBarPieces.VIGNETTE_FADE_2, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_VIGNETTE_FADE_2));
                airManaBar.put(manaBarPieces.VIGNETTE_FADE_3, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_VIGNETTE_FADE_3));
                airManaBar.put(manaBarPieces.VIGNETTE_FADE_4, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_VIGNETTE_FADE_4));
                airManaBar.put(manaBarPieces.VIGNETTE_FADE_5, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_VIGNETTE_FADE_5));
                airManaBar.put(manaBarPieces.VIGNETTE_FADE_6, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_VIGNETTE_FADE_6));
                airManaBar.put(manaBarPieces.VIGNETTE_FADE_7, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_VIGNETTE_FADE_7));
                airManaBar.put(manaBarPieces.VIGNETTE_FADE_8, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_VIGNETTE_FADE_8));
                airManaBar.put(manaBarPieces.VIGNETTE_FADE_9, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_VIGNETTE_FADE_9));
                airManaBar.put(manaBarPieces.VIGNETTE_FADE_0, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_VIGNETTE_FADE_0));
            }
            //Air Num 1 Fade
            {
                airManaBar.put(manaBarPieces.NUM_1_FADE_1, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_1_FADE_1));
                airManaBar.put(manaBarPieces.NUM_1_FADE_2, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_1_FADE_2));
                airManaBar.put(manaBarPieces.NUM_1_FADE_3, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_1_FADE_3));
                airManaBar.put(manaBarPieces.NUM_1_FADE_4, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_1_FADE_4));
                airManaBar.put(manaBarPieces.NUM_1_FADE_5, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_1_FADE_5));
                airManaBar.put(manaBarPieces.NUM_1_FADE_6, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_1_FADE_6));
                airManaBar.put(manaBarPieces.NUM_1_FADE_7, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_1_FADE_7));
                airManaBar.put(manaBarPieces.NUM_1_FADE_8, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_1_FADE_8));
                airManaBar.put(manaBarPieces.NUM_1_FADE_9, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_1_FADE_9));
                airManaBar.put(manaBarPieces.NUM_1_FADE_0, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_1_FADE_0));
            }
            //Air Num 2 Fade
            {
                airManaBar.put(manaBarPieces.NUM_2_FADE_1, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_2_FADE_1));
                airManaBar.put(manaBarPieces.NUM_2_FADE_2, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_2_FADE_2));
                airManaBar.put(manaBarPieces.NUM_2_FADE_3, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_2_FADE_3));
                airManaBar.put(manaBarPieces.NUM_2_FADE_4, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_2_FADE_4));
                airManaBar.put(manaBarPieces.NUM_2_FADE_5, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_2_FADE_5));
                airManaBar.put(manaBarPieces.NUM_2_FADE_6, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_2_FADE_6));
                airManaBar.put(manaBarPieces.NUM_2_FADE_7, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_2_FADE_7));
                airManaBar.put(manaBarPieces.NUM_2_FADE_8, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_2_FADE_8));
                airManaBar.put(manaBarPieces.NUM_2_FADE_9, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_2_FADE_9));
                airManaBar.put(manaBarPieces.NUM_2_FADE_0, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_2_FADE_0));
            }
            //Air Num 3 Fade
            {
                airManaBar.put(manaBarPieces.NUM_3_FADE_1, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_3_FADE_1));
                airManaBar.put(manaBarPieces.NUM_3_FADE_2, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_3_FADE_2));
                airManaBar.put(manaBarPieces.NUM_3_FADE_3, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_3_FADE_3));
                airManaBar.put(manaBarPieces.NUM_3_FADE_4, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_3_FADE_4));
                airManaBar.put(manaBarPieces.NUM_3_FADE_5, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_3_FADE_5));
                airManaBar.put(manaBarPieces.NUM_3_FADE_6, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_3_FADE_6));
                airManaBar.put(manaBarPieces.NUM_3_FADE_7, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_3_FADE_7));
                airManaBar.put(manaBarPieces.NUM_3_FADE_8, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_3_FADE_8));
                airManaBar.put(manaBarPieces.NUM_3_FADE_9, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_3_FADE_9));
                airManaBar.put(manaBarPieces.NUM_3_FADE_0, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_3_FADE_0));
            }
            //Air Num 4 Fade
            {
                airManaBar.put(manaBarPieces.NUM_4_FADE_1, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_4_FADE_1));
                airManaBar.put(manaBarPieces.NUM_4_FADE_2, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_4_FADE_2));
                airManaBar.put(manaBarPieces.NUM_4_FADE_3, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_4_FADE_3));
                airManaBar.put(manaBarPieces.NUM_4_FADE_4, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_4_FADE_4));
                airManaBar.put(manaBarPieces.NUM_4_FADE_5, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_4_FADE_5));
                airManaBar.put(manaBarPieces.NUM_4_FADE_6, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_4_FADE_6));
                airManaBar.put(manaBarPieces.NUM_4_FADE_7, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_4_FADE_7));
                airManaBar.put(manaBarPieces.NUM_4_FADE_8, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_4_FADE_8));
                airManaBar.put(manaBarPieces.NUM_4_FADE_9, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_4_FADE_9));
                airManaBar.put(manaBarPieces.NUM_4_FADE_0, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_4_FADE_0));
            }
            //Air Num 5 Fade
            {
                airManaBar.put(manaBarPieces.NUM_5_FADE_1, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_5_FADE_1));
                airManaBar.put(manaBarPieces.NUM_5_FADE_2, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_5_FADE_2));
                airManaBar.put(manaBarPieces.NUM_5_FADE_3, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_5_FADE_3));
                airManaBar.put(manaBarPieces.NUM_5_FADE_4, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_5_FADE_4));
                airManaBar.put(manaBarPieces.NUM_5_FADE_5, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_5_FADE_5));
                airManaBar.put(manaBarPieces.NUM_5_FADE_6, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_5_FADE_6));
                airManaBar.put(manaBarPieces.NUM_5_FADE_7, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_5_FADE_7));
                airManaBar.put(manaBarPieces.NUM_5_FADE_8, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_5_FADE_8));
                airManaBar.put(manaBarPieces.NUM_5_FADE_9, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_5_FADE_9));
                airManaBar.put(manaBarPieces.NUM_5_FADE_0, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_5_FADE_0));
            }
            //Air Num 6 Fade
            {
                airManaBar.put(manaBarPieces.NUM_6_FADE_1, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_6_FADE_1));
                airManaBar.put(manaBarPieces.NUM_6_FADE_2, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_6_FADE_2));
                airManaBar.put(manaBarPieces.NUM_6_FADE_3, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_6_FADE_3));
                airManaBar.put(manaBarPieces.NUM_6_FADE_4, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_6_FADE_4));
                airManaBar.put(manaBarPieces.NUM_6_FADE_5, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_6_FADE_5));
                airManaBar.put(manaBarPieces.NUM_6_FADE_6, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_6_FADE_6));
                airManaBar.put(manaBarPieces.NUM_6_FADE_7, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_6_FADE_7));
                airManaBar.put(manaBarPieces.NUM_6_FADE_8, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_6_FADE_8));
                airManaBar.put(manaBarPieces.NUM_6_FADE_9, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_6_FADE_9));
                airManaBar.put(manaBarPieces.NUM_6_FADE_0, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_6_FADE_0));
            }
            //Air Num 7 Fade
            {
                airManaBar.put(manaBarPieces.NUM_7_FADE_1, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_7_FADE_1));
                airManaBar.put(manaBarPieces.NUM_7_FADE_2, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_7_FADE_2));
                airManaBar.put(manaBarPieces.NUM_7_FADE_3, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_7_FADE_3));
                airManaBar.put(manaBarPieces.NUM_7_FADE_4, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_7_FADE_4));
                airManaBar.put(manaBarPieces.NUM_7_FADE_5, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_7_FADE_5));
                airManaBar.put(manaBarPieces.NUM_7_FADE_6, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_7_FADE_6));
                airManaBar.put(manaBarPieces.NUM_7_FADE_7, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_7_FADE_7));
                airManaBar.put(manaBarPieces.NUM_7_FADE_8, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_7_FADE_8));
                airManaBar.put(manaBarPieces.NUM_7_FADE_9, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_7_FADE_9));
                airManaBar.put(manaBarPieces.NUM_7_FADE_0, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_7_FADE_0));
            }
            //Air Num 8 Fade
            {
                airManaBar.put(manaBarPieces.NUM_8_FADE_1, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_8_FADE_1));
                airManaBar.put(manaBarPieces.NUM_8_FADE_2, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_8_FADE_2));
                airManaBar.put(manaBarPieces.NUM_8_FADE_3, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_8_FADE_3));
                airManaBar.put(manaBarPieces.NUM_8_FADE_4, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_8_FADE_4));
                airManaBar.put(manaBarPieces.NUM_8_FADE_5, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_8_FADE_5));
                airManaBar.put(manaBarPieces.NUM_8_FADE_6, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_8_FADE_6));
                airManaBar.put(manaBarPieces.NUM_8_FADE_7, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_8_FADE_7));
                airManaBar.put(manaBarPieces.NUM_8_FADE_8, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_8_FADE_8));
                airManaBar.put(manaBarPieces.NUM_8_FADE_9, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_8_FADE_9));
                airManaBar.put(manaBarPieces.NUM_8_FADE_0, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_8_FADE_0));
            }
            //Air Num 9 Fade
            {
                airManaBar.put(manaBarPieces.NUM_9_FADE_1, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_9_FADE_1));
                airManaBar.put(manaBarPieces.NUM_9_FADE_2, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_9_FADE_2));
                airManaBar.put(manaBarPieces.NUM_9_FADE_3, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_9_FADE_3));
                airManaBar.put(manaBarPieces.NUM_9_FADE_4, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_9_FADE_4));
                airManaBar.put(manaBarPieces.NUM_9_FADE_5, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_9_FADE_5));
                airManaBar.put(manaBarPieces.NUM_9_FADE_6, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_9_FADE_6));
                airManaBar.put(manaBarPieces.NUM_9_FADE_7, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_9_FADE_7));
                airManaBar.put(manaBarPieces.NUM_9_FADE_8, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_9_FADE_8));
                airManaBar.put(manaBarPieces.NUM_9_FADE_9, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_9_FADE_9));
                airManaBar.put(manaBarPieces.NUM_9_FADE_0, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_9_FADE_0));
            }
            //Air Num 0 Fade
            {
                airManaBar.put(manaBarPieces.NUM_0_FADE_1, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_0_FADE_1));
                airManaBar.put(manaBarPieces.NUM_0_FADE_2, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_0_FADE_2));
                airManaBar.put(manaBarPieces.NUM_0_FADE_3, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_0_FADE_3));
                airManaBar.put(manaBarPieces.NUM_0_FADE_4, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_0_FADE_4));
                airManaBar.put(manaBarPieces.NUM_0_FADE_5, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_0_FADE_5));
                airManaBar.put(manaBarPieces.NUM_0_FADE_6, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_0_FADE_6));
                airManaBar.put(manaBarPieces.NUM_0_FADE_7, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_0_FADE_7));
                airManaBar.put(manaBarPieces.NUM_0_FADE_8, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_0_FADE_8));
                airManaBar.put(manaBarPieces.NUM_0_FADE_9, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_0_FADE_9));
                airManaBar.put(manaBarPieces.NUM_0_FADE_0, ((Apprentice) player).omni$getChar(CustomCharDict.CharName.AIR_NUM_0_FADE_0));
            }
        }



        stormManaBar.put(manaBarPieces.EMPTY, ((Apprentice)player).omni$getChar(CustomCharDict.CharName.STORM_EMPTY));
        stormManaBar.put(manaBarPieces.FILL_1, ((Apprentice)player).omni$getChar(CustomCharDict.CharName.STORM_FILL_1));
        stormManaBar.put(manaBarPieces.FILL_2, ((Apprentice)player).omni$getChar(CustomCharDict.CharName.STORM_FILL_2));
        stormManaBar.put(manaBarPieces.FILL_3, ((Apprentice)player).omni$getChar(CustomCharDict.CharName.STORM_FILL_3));
        stormManaBar.put(manaBarPieces.FILL_4, ((Apprentice)player).omni$getChar(CustomCharDict.CharName.STORM_FILL_4));
        stormManaBar.put(manaBarPieces.FILL_5, ((Apprentice)player).omni$getChar(CustomCharDict.CharName.STORM_FILL_5));
        stormManaBar.put(manaBarPieces.FILL_6, ((Apprentice)player).omni$getChar(CustomCharDict.CharName.STORM_FILL_6));
        stormManaBar.put(manaBarPieces.FILL_7, ((Apprentice)player).omni$getChar(CustomCharDict.CharName.STORM_FILL_7));
        stormManaBar.put(manaBarPieces.FILL_8, ((Apprentice)player).omni$getChar(CustomCharDict.CharName.STORM_FILL_8));
        stormManaBar.put(manaBarPieces.FILL_9, ((Apprentice)player).omni$getChar(CustomCharDict.CharName.STORM_FILL_9));
        stormManaBar.put(manaBarPieces.FILL_0, ((Apprentice)player).omni$getChar(CustomCharDict.CharName.STORM_FILL_0));

//        switch (((Apprentice)player).omni$getElement().getName()){
//            case "Air" -> {activeManaBar = airManaBar;}
//
//        }
    }
    public void updateIcon(){
        if(ticksLived >= lifeExpectancy * 20 && isMortal){
            isFading = true;
        }
        if (isMortal){
            ageIcon(1);
        }
        iconText = genIcon();
    }
    private MutableText genIcon() {
        switch (name){
            case GRID -> {return genGrid();}
            case MANA_BAR -> {return genManaBarFill();}
            case MANA_BAR_BASE -> {return genManaBarBase();}
            case MANA_VIGNETTE -> {return genManaBarVignette();}
            case MANA_VALUE -> {return  genManaBarValue();}
            default -> {return Text.literal("Oops");}
        }
    }
    public void ageIcon(int p_ticksToAge){
        ticksLived += p_ticksToAge;
        if (ticksLived % 2 == 0){
            if (isFading && !isFaded){
                fadeCounter ++;
                if (fadeCounter >= 9) {
                    isFaded = true;
                }
            }
        }
    }
    public iconName getName() {
        return name;
    }
    public MutableText getIconText(){
        return iconText;
    }
    public boolean canDie(){
        return isMortal;
    }
    public boolean hasFaded(){
        return isFaded;
    }


    private final iconName name;
    private MutableText iconText;
    private final ServerPlayerEntity player;
    private final boolean isMortal;
    private int ticksLived;
    private int lifeExpectancy;
    private boolean isFading;
    private boolean isFaded;
    private int fadeCounter;
    private HashMap<manaBarPieces, CustomChar> activeManaBar;

    public enum iconName {
        GRID,
        MANA_BAR,
        MANA_BAR_BASE,
        MANA_BAR_FADE,
        MANA_VALUE,
        MANA_VIGNETTE,
        CLARITY_FIRE_EXP_BAR,
        CLARITY_WATER_EXP_BAR,
        CLARITY_EARTH_EXP_BAR,
        CLARITY_AIR_EXP_BAR
    }
    private enum manaBarPieces {
        EMPTY, VIGNETTE,
        FILL_1, FILL_2, FILL_3, FILL_4, FILL_5, FILL_6, FILL_7, FILL_8, FILL_9, FILL_0,
        NUM_1, NUM_2, NUM_3, NUM_4, NUM_5, NUM_6, NUM_7, NUM_8, NUM_9, NUM_0,
        BASE_FADE_1, BASE_FADE_2, BASE_FADE_3, BASE_FADE_4, BASE_FADE_5, BASE_FADE_6, BASE_FADE_7, BASE_FADE_8, BASE_FADE_9, BASE_FADE_0,
        VIGNETTE_FADE_1, VIGNETTE_FADE_2, VIGNETTE_FADE_3, VIGNETTE_FADE_4, VIGNETTE_FADE_5, VIGNETTE_FADE_6, VIGNETTE_FADE_7, VIGNETTE_FADE_8, VIGNETTE_FADE_9, VIGNETTE_FADE_0,
        NUM_1_FADE_0, NUM_2_FADE_0, NUM_3_FADE_0, NUM_4_FADE_0, NUM_5_FADE_0, NUM_6_FADE_0, NUM_7_FADE_0, NUM_8_FADE_0, NUM_9_FADE_0, NUM_0_FADE_0,
        NUM_1_FADE_1, NUM_2_FADE_1, NUM_3_FADE_1, NUM_4_FADE_1, NUM_5_FADE_1, NUM_6_FADE_1, NUM_7_FADE_1, NUM_8_FADE_1, NUM_9_FADE_1, NUM_0_FADE_1,
        NUM_1_FADE_2, NUM_2_FADE_2, NUM_3_FADE_2, NUM_4_FADE_2, NUM_5_FADE_2, NUM_6_FADE_2, NUM_7_FADE_2, NUM_8_FADE_2, NUM_9_FADE_2, NUM_0_FADE_2,
        NUM_1_FADE_3, NUM_2_FADE_3, NUM_3_FADE_3, NUM_4_FADE_3, NUM_5_FADE_3, NUM_6_FADE_3, NUM_7_FADE_3, NUM_8_FADE_3, NUM_9_FADE_3, NUM_0_FADE_3,
        NUM_1_FADE_4, NUM_2_FADE_4, NUM_3_FADE_4, NUM_4_FADE_4, NUM_5_FADE_4, NUM_6_FADE_4, NUM_7_FADE_4, NUM_8_FADE_4, NUM_9_FADE_4, NUM_0_FADE_4,
        NUM_1_FADE_5, NUM_2_FADE_5, NUM_3_FADE_5, NUM_4_FADE_5, NUM_5_FADE_5, NUM_6_FADE_5, NUM_7_FADE_5, NUM_8_FADE_5, NUM_9_FADE_5, NUM_0_FADE_5,
        NUM_1_FADE_6, NUM_2_FADE_6, NUM_3_FADE_6, NUM_4_FADE_6, NUM_5_FADE_6, NUM_6_FADE_6, NUM_7_FADE_6, NUM_8_FADE_6, NUM_9_FADE_6, NUM_0_FADE_6,
        NUM_1_FADE_7, NUM_2_FADE_7, NUM_3_FADE_7, NUM_4_FADE_7, NUM_5_FADE_7, NUM_6_FADE_7, NUM_7_FADE_7, NUM_8_FADE_7, NUM_9_FADE_7, NUM_0_FADE_7,
        NUM_1_FADE_8, NUM_2_FADE_8, NUM_3_FADE_8, NUM_4_FADE_8, NUM_5_FADE_8, NUM_6_FADE_8, NUM_7_FADE_8, NUM_8_FADE_8, NUM_9_FADE_8, NUM_0_FADE_8,
        NUM_1_FADE_9, NUM_2_FADE_9, NUM_3_FADE_9, NUM_4_FADE_9, NUM_5_FADE_9, NUM_6_FADE_9, NUM_7_FADE_9, NUM_8_FADE_9, NUM_9_FADE_9, NUM_0_FADE_9



    }
    private Text getSpace(int p_space){
        return Text.translatable("space." + p_space);
    }

    private MutableText positionIcon(double p_xAxisTranslation, int p_width, Text p_icon){
        MutableText preSpace = Text.translatable("space." + (int)((-(double)p_width/2) + p_xAxisTranslation));
        MutableText postSpace = Text.translatable("space." + (int)((-(double)p_width/2) - p_xAxisTranslation));
        MutableText positionedIcon = Text.literal("")
            .append(preSpace)
            .append(p_icon)
            .append(postSpace)
            .append(Text.translatable("space.-1"));

        return positionedIcon;
    }

    private MutableText genGrid(){
        return positionIcon(0, ((Apprentice)player).omni$getChar(CustomCharDict.CharName.GRID).getWidth(), ((Apprentice)player).omni$getChar(CustomCharDict.CharName.GRID).getSymbol());
    }

    private MutableText genManaBarBase(){
        if(isMortal){
            return positionIcon(0, activeManaBar.get(manaBarPieces.valueOf("BASE_FADE_" + fadeCounter)).getWidth(), activeManaBar.get(manaBarPieces.valueOf("BASE_FADE_" + fadeCounter)).getSymbol());
        }
        else{
            return positionIcon(0, activeManaBar.get(manaBarPieces.EMPTY).getWidth(),activeManaBar.get(manaBarPieces.EMPTY).getSymbol());
        }
    }
    private MutableText genManaBarFill() {
        MutableText manaBarFill = Text.literal("");

        int numFilledSegments = (int) (180 * (((Apprentice) player).omni$getMana() / ((Apprentice) player).omni$getManaMax()));
        for(int i = 1; i <= numFilledSegments; i++){
            if (i!= 1){
                manaBarFill.append(getSpace(-1));
            }
            int i_ = i;
            if (i >= 91){
                i_--;
            }
            int lastDigit = i_ % 10;
            switch (lastDigit){
                case 1 -> manaBarFill.append(activeManaBar.get(manaBarPieces.FILL_1).getSymbol());
                case 2 -> manaBarFill.append(activeManaBar.get(manaBarPieces.FILL_2).getSymbol());
                case 3 -> manaBarFill.append(activeManaBar.get(manaBarPieces.FILL_3).getSymbol());
                case 4 -> manaBarFill.append(activeManaBar.get(manaBarPieces.FILL_4).getSymbol());
                case 5 -> manaBarFill.append(activeManaBar.get(manaBarPieces.FILL_5).getSymbol());
                case 6 -> manaBarFill.append(activeManaBar.get(manaBarPieces.FILL_6).getSymbol());
                case 7 -> manaBarFill.append(activeManaBar.get(manaBarPieces.FILL_7).getSymbol());
                case 8 -> manaBarFill.append(activeManaBar.get(manaBarPieces.FILL_8).getSymbol());
                case 9 -> manaBarFill.append(activeManaBar.get(manaBarPieces.FILL_9).getSymbol());
                case 0 -> manaBarFill.append(activeManaBar.get(manaBarPieces.FILL_0).getSymbol());
            }
        }
      return positionIcon(-90.0 + ((double)numFilledSegments / 2), numFilledSegments, manaBarFill);
    }
    private MutableText genManaBarVignette(){
        MutableText vignette = Text.literal("");
        if (isFading){
            vignette.append(activeManaBar.get(manaBarPieces.valueOf("VIGNETTE_FADE_" + fadeCounter)).getSymbol());
        }
        else{
            vignette.append(activeManaBar.get(manaBarPieces.VIGNETTE).getSymbol());
        }
        return positionIcon(0, activeManaBar.get(manaBarPieces.VIGNETTE).getWidth(), vignette);

    }
    private MutableText genManaBarValue(){
        MutableText valueIcon = Text.literal("");
        String manaString = String.valueOf((int)((Apprentice)player).omni$getMana());
        int width = 0;
        for (int i = 0; i < manaString.length(); i++){
            char number = manaString.charAt(i);
            if(isFading){
                valueIcon.append(activeManaBar.get(manaBarPieces.valueOf("NUM_" + number + "_FADE_" + fadeCounter)).getSymbol());
            }else{
                valueIcon.append(activeManaBar.get(manaBarPieces.valueOf("NUM_" + number)).getSymbol());
            }
//            if(i < manaString.length() -1){
//                valueIcon.append(Text.translatable("space.-1"));
//            }
           width += activeManaBar.get(manaBarPieces.valueOf("NUM_" + number)).getWidth() + 1;
        }
        return positionIcon(0, width , valueIcon);
    }






    private final HashMap<manaBarPieces, CustomChar> airManaBar = new HashMap<manaBarPieces, CustomChar>();
    private final HashMap<manaBarPieces, CustomChar> clarityManaBar = new HashMap<manaBarPieces, CustomChar>();
    private final HashMap<manaBarPieces, CustomChar> deathManaBar = new HashMap<manaBarPieces, CustomChar>();
    private final HashMap<manaBarPieces, CustomChar> earthManaBar = new HashMap<manaBarPieces, CustomChar>();
    private final HashMap<manaBarPieces, CustomChar> fireManaBar = new HashMap<manaBarPieces, CustomChar>();
    private final HashMap<manaBarPieces, CustomChar> lifeManaBar = new HashMap<manaBarPieces, CustomChar>();
    private final HashMap<manaBarPieces, CustomChar> magicManaBar = new HashMap<manaBarPieces, CustomChar>();
    private final HashMap<manaBarPieces, CustomChar> moonManaBar = new HashMap<manaBarPieces, CustomChar>();
    private final HashMap<manaBarPieces, CustomChar> stormManaBar = new HashMap<manaBarPieces, CustomChar>();
    private final HashMap<manaBarPieces, CustomChar> sunManaBar = new HashMap<manaBarPieces, CustomChar>();
    private final HashMap<manaBarPieces, CustomChar> techManaBar = new HashMap<manaBarPieces, CustomChar>();
    private final HashMap<manaBarPieces, CustomChar> waterManaBar = new HashMap<manaBarPieces, CustomChar>();

}