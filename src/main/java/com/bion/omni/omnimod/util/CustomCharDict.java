package com.bion.omni.omnimod.util;

import net.minecraft.text.Text;

import java.util.HashMap;

public class CustomCharDict {
    public CustomCharDict(){
        chars.put(CharName.GRID, new CustomChar(182, 280, Text.literal("\ue049")));

        //Air
        chars.put(CharName.AIR_EMPTY, new CustomChar(182, 7, Text.literal("\ue04a")));

        chars.put(CharName.AIR_FILL_1, new CustomChar(1, 7, Text.literal("\ue04b")));
        chars.put(CharName.AIR_FILL_2, new CustomChar(1, 7, Text.literal("\ue04c")));
        chars.put(CharName.AIR_FILL_3, new CustomChar(1, 7, Text.literal("\ue04d")));
        chars.put(CharName.AIR_FILL_4, new CustomChar(1, 7, Text.literal("\ue04e")));
        chars.put(CharName.AIR_FILL_5, new CustomChar(1, 7, Text.literal("\ue04f")));
        chars.put(CharName.AIR_FILL_6, new CustomChar(1, 7, Text.literal("\ue050")));
        chars.put(CharName.AIR_FILL_7, new CustomChar(1, 7, Text.literal("\ue051")));
        chars.put(CharName.AIR_FILL_8, new CustomChar(1, 7, Text.literal("\ue052")));
        chars.put(CharName.AIR_FILL_9, new CustomChar(1, 7, Text.literal("\ue053")));
        chars.put(CharName.AIR_FILL_0, new CustomChar(1, 7, Text.literal("\ue054")));

        chars.put(CharName.AIR_BASE_FADE_0, new CustomChar(182, 7, Text.literal("\ue055")));
        chars.put(CharName.AIR_BASE_FADE_1, new CustomChar(182, 7, Text.literal("\ue056")));
        chars.put(CharName.AIR_BASE_FADE_2, new CustomChar(182, 7, Text.literal("\ue057")));
        chars.put(CharName.AIR_BASE_FADE_3, new CustomChar(182, 7, Text.literal("\ue058")));
        chars.put(CharName.AIR_BASE_FADE_4, new CustomChar(182, 7, Text.literal("\ue059")));
        chars.put(CharName.AIR_BASE_FADE_5, new CustomChar(182, 7, Text.literal("\ue05a")));
        chars.put(CharName.AIR_BASE_FADE_6, new CustomChar(182, 7, Text.literal("\ue05b")));
        chars.put(CharName.AIR_BASE_FADE_7, new CustomChar(182, 7, Text.literal("\ue05c")));
        chars.put(CharName.AIR_BASE_FADE_8, new CustomChar(182, 7, Text.literal("\ue05d")));
        chars.put(CharName.AIR_BASE_FADE_9, new CustomChar(182, 7, Text.literal("\ue05e")));

        chars.put(CharName.AIR_VIGNETTE, new CustomChar(53, 19, Text.literal("\ue060")));
        chars.put(CharName.AIR_VIGNETTE_FADE_0, new CustomChar(53, 19, Text.literal("\ue061")));
        chars.put(CharName.AIR_VIGNETTE_FADE_1, new CustomChar(53, 19, Text.literal("\ue062")));
        chars.put(CharName.AIR_VIGNETTE_FADE_2, new CustomChar(53, 19, Text.literal("\ue063")));
        chars.put(CharName.AIR_VIGNETTE_FADE_3, new CustomChar(53, 19, Text.literal("\ue064")));
        chars.put(CharName.AIR_VIGNETTE_FADE_4, new CustomChar(53, 19, Text.literal("\ue065")));
        chars.put(CharName.AIR_VIGNETTE_FADE_5, new CustomChar(53, 19, Text.literal("\ue066")));
        chars.put(CharName.AIR_VIGNETTE_FADE_6, new CustomChar(53, 19, Text.literal("\ue067")));
        chars.put(CharName.AIR_VIGNETTE_FADE_7, new CustomChar(53, 19, Text.literal("\ue068")));
        chars.put(CharName.AIR_VIGNETTE_FADE_8, new CustomChar(53, 19, Text.literal("\ue069")));
        chars.put(CharName.AIR_VIGNETTE_FADE_9, new CustomChar(53, 19, Text.literal("\ue06a")));

        chars.put(CharName.AIR_NUM_1, new CustomChar(9, 12, Text.literal("\ue06b")));
        chars.put(CharName.AIR_NUM_2, new CustomChar(9, 12, Text.literal("\ue06c")));
        chars.put(CharName.AIR_NUM_3, new CustomChar(9, 12, Text.literal("\ue06d")));
        chars.put(CharName.AIR_NUM_4, new CustomChar(9, 12, Text.literal("\ue06e")));
        chars.put(CharName.AIR_NUM_5, new CustomChar(9, 12, Text.literal("\ue06f")));
        chars.put(CharName.AIR_NUM_6, new CustomChar(9, 12, Text.literal("\ue070")));
        chars.put(CharName.AIR_NUM_7, new CustomChar(9, 12, Text.literal("\ue071")));
        chars.put(CharName.AIR_NUM_8, new CustomChar(9, 12, Text.literal("\ue072")));
        chars.put(CharName.AIR_NUM_9, new CustomChar(9, 12, Text.literal("\ue073")));
        chars.put(CharName.AIR_NUM_0, new CustomChar(9, 12, Text.literal("\ue074")));

        chars.put(CharName.AIR_NUM_1_FADE_0, new CustomChar(9, 12, Text.literal("\ue080")));
        chars.put(CharName.AIR_NUM_1_FADE_1, new CustomChar(9, 12, Text.literal("\ue081")));
        chars.put(CharName.AIR_NUM_1_FADE_2, new CustomChar(9, 12, Text.literal("\ue082")));
        chars.put(CharName.AIR_NUM_1_FADE_3, new CustomChar(9, 12, Text.literal("\ue083")));
        chars.put(CharName.AIR_NUM_1_FADE_4, new CustomChar(9, 12, Text.literal("\ue084")));
        chars.put(CharName.AIR_NUM_1_FADE_5, new CustomChar(9, 12, Text.literal("\ue085")));
        chars.put(CharName.AIR_NUM_1_FADE_6, new CustomChar(9, 12, Text.literal("\ue086")));
        chars.put(CharName.AIR_NUM_1_FADE_7, new CustomChar(9, 12, Text.literal("\ue087")));
        chars.put(CharName.AIR_NUM_1_FADE_8, new CustomChar(9, 12, Text.literal("\ue088")));
        chars.put(CharName.AIR_NUM_1_FADE_9, new CustomChar(9, 12, Text.literal("\ue089")));

        chars.put(CharName.AIR_NUM_2_FADE_0, new CustomChar(9, 12, Text.literal("\ue090")));
        chars.put(CharName.AIR_NUM_2_FADE_1, new CustomChar(9, 12, Text.literal("\ue091")));
        chars.put(CharName.AIR_NUM_2_FADE_2, new CustomChar(9, 12, Text.literal("\ue092")));
        chars.put(CharName.AIR_NUM_2_FADE_3, new CustomChar(9, 12, Text.literal("\ue093")));
        chars.put(CharName.AIR_NUM_2_FADE_4, new CustomChar(9, 12, Text.literal("\ue094")));
        chars.put(CharName.AIR_NUM_2_FADE_5, new CustomChar(9, 12, Text.literal("\ue095")));
        chars.put(CharName.AIR_NUM_2_FADE_6, new CustomChar(9, 12, Text.literal("\ue096")));
        chars.put(CharName.AIR_NUM_2_FADE_7, new CustomChar(9, 12, Text.literal("\ue097")));
        chars.put(CharName.AIR_NUM_2_FADE_8, new CustomChar(9, 12, Text.literal("\ue098")));
        chars.put(CharName.AIR_NUM_2_FADE_9, new CustomChar(9, 12, Text.literal("\ue099")));

        chars.put(CharName.AIR_NUM_3_FADE_0, new CustomChar(9, 12, Text.literal("\ue0a0")));
        chars.put(CharName.AIR_NUM_3_FADE_1, new CustomChar(9, 12, Text.literal("\ue0a1")));
        chars.put(CharName.AIR_NUM_3_FADE_2, new CustomChar(9, 12, Text.literal("\ue0a2")));
        chars.put(CharName.AIR_NUM_3_FADE_3, new CustomChar(9, 12, Text.literal("\ue0a3")));
        chars.put(CharName.AIR_NUM_3_FADE_4, new CustomChar(9, 12, Text.literal("\ue0a4")));
        chars.put(CharName.AIR_NUM_3_FADE_5, new CustomChar(9, 12, Text.literal("\ue0a5")));
        chars.put(CharName.AIR_NUM_3_FADE_6, new CustomChar(9, 12, Text.literal("\ue0a6")));
        chars.put(CharName.AIR_NUM_3_FADE_7, new CustomChar(9, 12, Text.literal("\ue0a7")));
        chars.put(CharName.AIR_NUM_3_FADE_8, new CustomChar(9, 12, Text.literal("\ue0a8")));
        chars.put(CharName.AIR_NUM_3_FADE_9, new CustomChar(9, 12, Text.literal("\ue0a9")));

        chars.put(CharName.AIR_NUM_4_FADE_0, new CustomChar(9, 12, Text.literal("\ue0b0")));
        chars.put(CharName.AIR_NUM_4_FADE_1, new CustomChar(9, 12, Text.literal("\ue0b1")));
        chars.put(CharName.AIR_NUM_4_FADE_2, new CustomChar(9, 12, Text.literal("\ue0b2")));
        chars.put(CharName.AIR_NUM_4_FADE_3, new CustomChar(9, 12, Text.literal("\ue0b3")));
        chars.put(CharName.AIR_NUM_4_FADE_4, new CustomChar(9, 12, Text.literal("\ue0b4")));
        chars.put(CharName.AIR_NUM_4_FADE_5, new CustomChar(9, 12, Text.literal("\ue0b5")));
        chars.put(CharName.AIR_NUM_4_FADE_6, new CustomChar(9, 12, Text.literal("\ue0b6")));
        chars.put(CharName.AIR_NUM_4_FADE_7, new CustomChar(9, 12, Text.literal("\ue0b7")));
        chars.put(CharName.AIR_NUM_4_FADE_8, new CustomChar(9, 12, Text.literal("\ue0b8")));
        chars.put(CharName.AIR_NUM_4_FADE_9, new CustomChar(9, 12, Text.literal("\ue0b9")));

        chars.put(CharName.AIR_NUM_5_FADE_0, new CustomChar(9, 12, Text.literal("\ue0c0")));
        chars.put(CharName.AIR_NUM_5_FADE_1, new CustomChar(9, 12, Text.literal("\ue0c1")));
        chars.put(CharName.AIR_NUM_5_FADE_2, new CustomChar(9, 12, Text.literal("\ue0c2")));
        chars.put(CharName.AIR_NUM_5_FADE_3, new CustomChar(9, 12, Text.literal("\ue0c3")));
        chars.put(CharName.AIR_NUM_5_FADE_4, new CustomChar(9, 12, Text.literal("\ue0c4")));
        chars.put(CharName.AIR_NUM_5_FADE_5, new CustomChar(9, 12, Text.literal("\ue0c5")));
        chars.put(CharName.AIR_NUM_5_FADE_6, new CustomChar(9, 12, Text.literal("\ue0c6")));
        chars.put(CharName.AIR_NUM_5_FADE_7, new CustomChar(9, 12, Text.literal("\ue0c7")));
        chars.put(CharName.AIR_NUM_5_FADE_8, new CustomChar(9, 12, Text.literal("\ue0c8")));
        chars.put(CharName.AIR_NUM_5_FADE_9, new CustomChar(9, 12, Text.literal("\ue0c9")));

        chars.put(CharName.AIR_NUM_6_FADE_0, new CustomChar(9, 12, Text.literal("\ue0d0")));
        chars.put(CharName.AIR_NUM_6_FADE_1, new CustomChar(9, 12, Text.literal("\ue0d1")));
        chars.put(CharName.AIR_NUM_6_FADE_2, new CustomChar(9, 12, Text.literal("\ue0d2")));
        chars.put(CharName.AIR_NUM_6_FADE_3, new CustomChar(9, 12, Text.literal("\ue0d3")));
        chars.put(CharName.AIR_NUM_6_FADE_4, new CustomChar(9, 12, Text.literal("\ue0d4")));
        chars.put(CharName.AIR_NUM_6_FADE_5, new CustomChar(9, 12, Text.literal("\ue0d5")));
        chars.put(CharName.AIR_NUM_6_FADE_6, new CustomChar(9, 12, Text.literal("\ue0d6")));
        chars.put(CharName.AIR_NUM_6_FADE_7, new CustomChar(9, 12, Text.literal("\ue0d7")));
        chars.put(CharName.AIR_NUM_6_FADE_8, new CustomChar(9, 12, Text.literal("\ue0d8")));
        chars.put(CharName.AIR_NUM_6_FADE_9, new CustomChar(9, 12, Text.literal("\ue0d9")));

        chars.put(CharName.AIR_NUM_7_FADE_0, new CustomChar(9, 12, Text.literal("\ue0e0")));
        chars.put(CharName.AIR_NUM_7_FADE_1, new CustomChar(9, 12, Text.literal("\ue0e1")));
        chars.put(CharName.AIR_NUM_7_FADE_2, new CustomChar(9, 12, Text.literal("\ue0e2")));
        chars.put(CharName.AIR_NUM_7_FADE_3, new CustomChar(9, 12, Text.literal("\ue0e3")));
        chars.put(CharName.AIR_NUM_7_FADE_4, new CustomChar(9, 12, Text.literal("\ue0e4")));
        chars.put(CharName.AIR_NUM_7_FADE_5, new CustomChar(9, 12, Text.literal("\ue0e5")));
        chars.put(CharName.AIR_NUM_7_FADE_6, new CustomChar(9, 12, Text.literal("\ue0e6")));
        chars.put(CharName.AIR_NUM_7_FADE_7, new CustomChar(9, 12, Text.literal("\ue0e7")));
        chars.put(CharName.AIR_NUM_7_FADE_8, new CustomChar(9, 12, Text.literal("\ue0e8")));
        chars.put(CharName.AIR_NUM_7_FADE_9, new CustomChar(9, 12, Text.literal("\ue0e9")));

        chars.put(CharName.AIR_NUM_8_FADE_0, new CustomChar(9, 12, Text.literal("\ue0f0")));
        chars.put(CharName.AIR_NUM_8_FADE_1, new CustomChar(9, 12, Text.literal("\ue0f1")));
        chars.put(CharName.AIR_NUM_8_FADE_2, new CustomChar(9, 12, Text.literal("\ue0f2")));
        chars.put(CharName.AIR_NUM_8_FADE_3, new CustomChar(9, 12, Text.literal("\ue0f3")));
        chars.put(CharName.AIR_NUM_8_FADE_4, new CustomChar(9, 12, Text.literal("\ue0f4")));
        chars.put(CharName.AIR_NUM_8_FADE_5, new CustomChar(9, 12, Text.literal("\ue0f5")));
        chars.put(CharName.AIR_NUM_8_FADE_6, new CustomChar(9, 12, Text.literal("\ue0f6")));
        chars.put(CharName.AIR_NUM_8_FADE_7, new CustomChar(9, 12, Text.literal("\ue0f7")));
        chars.put(CharName.AIR_NUM_8_FADE_8, new CustomChar(9, 12, Text.literal("\ue0f8")));
        chars.put(CharName.AIR_NUM_8_FADE_9, new CustomChar(9, 12, Text.literal("\ue0f9")));

        chars.put(CharName.AIR_NUM_9_FADE_0, new CustomChar(9, 12, Text.literal("\ue100")));
        chars.put(CharName.AIR_NUM_9_FADE_1, new CustomChar(9, 12, Text.literal("\ue101")));
        chars.put(CharName.AIR_NUM_9_FADE_2, new CustomChar(9, 12, Text.literal("\ue102")));
        chars.put(CharName.AIR_NUM_9_FADE_3, new CustomChar(9, 12, Text.literal("\ue103")));
        chars.put(CharName.AIR_NUM_9_FADE_4, new CustomChar(9, 12, Text.literal("\ue104")));
        chars.put(CharName.AIR_NUM_9_FADE_5, new CustomChar(9, 12, Text.literal("\ue105")));
        chars.put(CharName.AIR_NUM_9_FADE_6, new CustomChar(9, 12, Text.literal("\ue106")));
        chars.put(CharName.AIR_NUM_9_FADE_7, new CustomChar(9, 12, Text.literal("\ue107")));
        chars.put(CharName.AIR_NUM_9_FADE_8, new CustomChar(9, 12, Text.literal("\ue108")));
        chars.put(CharName.AIR_NUM_9_FADE_9, new CustomChar(9, 12, Text.literal("\ue109")));

        chars.put(CharName.AIR_NUM_0_FADE_0, new CustomChar(9, 12, Text.literal("\ue110")));
        chars.put(CharName.AIR_NUM_0_FADE_1, new CustomChar(9, 12, Text.literal("\ue111")));
        chars.put(CharName.AIR_NUM_0_FADE_2, new CustomChar(9, 12, Text.literal("\ue112")));
        chars.put(CharName.AIR_NUM_0_FADE_3, new CustomChar(9, 12, Text.literal("\ue113")));
        chars.put(CharName.AIR_NUM_0_FADE_4, new CustomChar(9, 12, Text.literal("\ue114")));
        chars.put(CharName.AIR_NUM_0_FADE_5, new CustomChar(9, 12, Text.literal("\ue115")));
        chars.put(CharName.AIR_NUM_0_FADE_6, new CustomChar(9, 12, Text.literal("\ue116")));
        chars.put(CharName.AIR_NUM_0_FADE_7, new CustomChar(9, 12, Text.literal("\ue117")));
        chars.put(CharName.AIR_NUM_0_FADE_8, new CustomChar(9, 12, Text.literal("\ue118")));
        chars.put(CharName.AIR_NUM_0_FADE_9, new CustomChar(9, 12, Text.literal("\ue119")));




        chars.put(CharName.STORM_EMPTY, new CustomChar(182, 7, Text.literal("\ue056")));
        chars.put(CharName.STORM_FILL_1, new CustomChar(1, 7, Text.literal("\ue057")));
        chars.put(CharName.STORM_FILL_2, new CustomChar(1, 7, Text.literal("\ue058")));
        chars.put(CharName.STORM_FILL_3, new CustomChar(1, 7, Text.literal("\ue059")));
        chars.put(CharName.STORM_FILL_4, new CustomChar(1, 7, Text.literal("\ue05A")));
        chars.put(CharName.STORM_FILL_5, new CustomChar(1, 7, Text.literal("\ue05B")));
        chars.put(CharName.STORM_FILL_6, new CustomChar(1, 7, Text.literal("\ue05C")));
        chars.put(CharName.STORM_FILL_7, new CustomChar(1, 7, Text.literal("\ue05D")));
        chars.put(CharName.STORM_FILL_8, new CustomChar(1, 7, Text.literal("\ue05E")));
        chars.put(CharName.STORM_FILL_9, new CustomChar(1, 7, Text.literal("\ue05f")));
        chars.put(CharName.STORM_FILL_0, new CustomChar(1, 7, Text.literal("\ue060")));

    }
    private HashMap <CharName, CustomChar> chars = new HashMap<CharName, CustomChar>();

    public CustomChar getChar(CharName p_name){
        return chars.get(p_name);
    }
    public enum CharName{
        GRID,

    //-----------------
    //Mana Bar Pieces
    //-----------------
        AIR_EMPTY,
        AIR_FILL_1, AIR_FILL_2, AIR_FILL_3, AIR_FILL_4, AIR_FILL_5, AIR_FILL_6, AIR_FILL_7, AIR_FILL_8, AIR_FILL_9, AIR_FILL_0,
        AIR_VIGNETTE,
        AIR_NUM_1, AIR_NUM_2, AIR_NUM_3, AIR_NUM_4, AIR_NUM_5, AIR_NUM_6, AIR_NUM_7, AIR_NUM_8, AIR_NUM_9, AIR_NUM_0,

        AIR_BASE_FADE_1,AIR_BASE_FADE_2,AIR_BASE_FADE_3,AIR_BASE_FADE_4,AIR_BASE_FADE_5,
        AIR_BASE_FADE_6,AIR_BASE_FADE_7,AIR_BASE_FADE_8,AIR_BASE_FADE_9, AIR_BASE_FADE_0,
        AIR_VIGNETTE_FADE_1, AIR_VIGNETTE_FADE_2, AIR_VIGNETTE_FADE_3, AIR_VIGNETTE_FADE_4, AIR_VIGNETTE_FADE_5,
        AIR_VIGNETTE_FADE_6, AIR_VIGNETTE_FADE_7, AIR_VIGNETTE_FADE_8, AIR_VIGNETTE_FADE_9, AIR_VIGNETTE_FADE_0,
        AIR_NUM_1_FADE_1, AIR_NUM_1_FADE_2, AIR_NUM_1_FADE_3, AIR_NUM_1_FADE_4, AIR_NUM_1_FADE_5, AIR_NUM_1_FADE_6, AIR_NUM_1_FADE_7, AIR_NUM_1_FADE_8, AIR_NUM_1_FADE_9, AIR_NUM_1_FADE_0,
        AIR_NUM_2_FADE_1, AIR_NUM_2_FADE_2, AIR_NUM_2_FADE_3, AIR_NUM_2_FADE_4, AIR_NUM_2_FADE_5, AIR_NUM_2_FADE_6, AIR_NUM_2_FADE_7, AIR_NUM_2_FADE_8, AIR_NUM_2_FADE_9, AIR_NUM_2_FADE_0,
        AIR_NUM_3_FADE_1, AIR_NUM_3_FADE_2, AIR_NUM_3_FADE_3, AIR_NUM_3_FADE_4, AIR_NUM_3_FADE_5, AIR_NUM_3_FADE_6, AIR_NUM_3_FADE_7, AIR_NUM_3_FADE_8, AIR_NUM_3_FADE_9, AIR_NUM_3_FADE_0,
        AIR_NUM_4_FADE_1, AIR_NUM_4_FADE_2, AIR_NUM_4_FADE_3, AIR_NUM_4_FADE_4, AIR_NUM_4_FADE_5, AIR_NUM_4_FADE_6, AIR_NUM_4_FADE_7, AIR_NUM_4_FADE_8, AIR_NUM_4_FADE_9, AIR_NUM_4_FADE_0,
        AIR_NUM_5_FADE_1, AIR_NUM_5_FADE_2, AIR_NUM_5_FADE_3, AIR_NUM_5_FADE_4, AIR_NUM_5_FADE_5, AIR_NUM_5_FADE_6, AIR_NUM_5_FADE_7, AIR_NUM_5_FADE_8, AIR_NUM_5_FADE_9, AIR_NUM_5_FADE_0,
        AIR_NUM_6_FADE_1, AIR_NUM_6_FADE_2, AIR_NUM_6_FADE_3, AIR_NUM_6_FADE_4, AIR_NUM_6_FADE_5, AIR_NUM_6_FADE_6, AIR_NUM_6_FADE_7, AIR_NUM_6_FADE_8, AIR_NUM_6_FADE_9, AIR_NUM_6_FADE_0,
        AIR_NUM_7_FADE_1, AIR_NUM_7_FADE_2, AIR_NUM_7_FADE_3, AIR_NUM_7_FADE_4, AIR_NUM_7_FADE_5, AIR_NUM_7_FADE_6, AIR_NUM_7_FADE_7, AIR_NUM_7_FADE_8, AIR_NUM_7_FADE_9, AIR_NUM_7_FADE_0,
        AIR_NUM_8_FADE_1, AIR_NUM_8_FADE_2, AIR_NUM_8_FADE_3, AIR_NUM_8_FADE_4, AIR_NUM_8_FADE_5, AIR_NUM_8_FADE_6, AIR_NUM_8_FADE_7, AIR_NUM_8_FADE_8, AIR_NUM_8_FADE_9, AIR_NUM_8_FADE_0,
        AIR_NUM_9_FADE_1, AIR_NUM_9_FADE_2, AIR_NUM_9_FADE_3, AIR_NUM_9_FADE_4, AIR_NUM_9_FADE_5, AIR_NUM_9_FADE_6, AIR_NUM_9_FADE_7, AIR_NUM_9_FADE_8, AIR_NUM_9_FADE_9, AIR_NUM_9_FADE_0,
        AIR_NUM_0_FADE_1, AIR_NUM_0_FADE_2, AIR_NUM_0_FADE_3, AIR_NUM_0_FADE_4, AIR_NUM_0_FADE_5, AIR_NUM_0_FADE_6, AIR_NUM_0_FADE_7, AIR_NUM_0_FADE_8, AIR_NUM_0_FADE_9, AIR_NUM_0_FADE_0,


        STORM_EMPTY, STORM_FILL_1, STORM_FILL_2, STORM_FILL_3, STORM_FILL_4, STORM_FILL_5, STORM_FILL_6, STORM_FILL_7, STORM_FILL_8, STORM_FILL_9, STORM_FILL_0,
    //-----------------
    //Book Borders
    //-----------------
        AIR_BOOK_BORDER_1,
        AIR_BOOK_BORDER_2,
        CLARITY_BOOK_BORDER_1,
        CLARITY_BOOK_BORDER_2,
        STORM_BOOK_BORDER_1,
        STORM_BOOK_BORDER_2,
        EARTH_BOOK_BORDER_1,
        EARTH_BOOK_BORDER_2,
        WATER_BOOK_BORDER_1,
        WATER_BOOK_BORDER_2,
        DEATH_BOOK_BORDER_1,
        DEATH_BOOK_BORDER_2,
        MOON_BOOK_BORDER_1,
        MOON_BOOK_BORDER_2,
        FIRE_BOOK_BORDER_1,
        FIRE_BOOK_BORDER_2,
        SUN_BOOK_BORDER_1,
        SUN_BOOK_BORDER_2,
        MAGIC_BOOK_BORDER_1,
        MAGIC_BOOK_BORDER_2,
        LIFE_BOOK_BORDER_1,
        LIFE_BOOK_BORDER_2,
    //---------------------
    //Clarity Experience Bars
    //---------------------
        CLARITY_EXP_BAR_FIRE_EMPTY,
        CLARITY_EXP_BAR_FIRE_END,
        CLARITY_EXP_BAR_FIRE_FILL_1,
        CLARITY_EXP_BAR_FIRE_FILL_2,
        CLARITY_EXP_BAR_FIRE_FILL_3,
        CLARITY_EXP_BAR_FIRE_FILL_4,
        CLARITY_EXP_BAR_FIRE_FILL_5,
        CLARITY_EXP_BAR_FIRE_FILL_6,
        CLARITY_EXP_BAR_FIRE_FILL_7,
        CLARITY_EXP_BAR_FIRE_FILL_8,
        CLARITY_EXP_BAR_FIRE_FILL_9,
        CLARITY_EXP_BAR_FIRE_FILL_PARTITION,

        CLARITY_EXP_BAR_WATER_EMPTY,
        CLARITY_EXP_BAR_WATER_END,
        CLARITY_EXP_BAR_WATER_FILL_1,
        CLARITY_EXP_BAR_WATER_FILL_2,
        CLARITY_EXP_BAR_WATER_FILL_3,
        CLARITY_EXP_BAR_WATER_FILL_4,
        CLARITY_EXP_BAR_WATER_FILL_5,
        CLARITY_EXP_BAR_WATER_FILL_6,
        CLARITY_EXP_BAR_WATER_FILL_7,
        CLARITY_EXP_BAR_WATER_FILL_8,
        CLARITY_EXP_BAR_WATER_FILL_9,
        CLARITY_EXP_BAR_WATER_FILL_PARTITION,

        CLARITY_EXP_BAR_EARTH_EMPTY,
        CLARITY_EXP_BAR_EARTH_END,
        CLARITY_EXP_BAR_EARTH_FILL_1,
        CLARITY_EXP_BAR_EARTH_FILL_2,
        CLARITY_EXP_BAR_EARTH_FILL_3,
        CLARITY_EXP_BAR_EARTH_FILL_4,
        CLARITY_EXP_BAR_EARTH_FILL_5,
        CLARITY_EXP_BAR_EARTH_FILL_6,
        CLARITY_EXP_BAR_EARTH_FILL_7,
        CLARITY_EXP_BAR_EARTH_FILL_8,
        CLARITY_EXP_BAR_EARTH_FILL_9,
        CLARITY_EXP_BAR_EARTH_FILL_PARTITION,

        CLARITY_EXP_BAR_AIR_EMPTY,
        CLARITY_EXP_BAR_AIR_END,
        CLARITY_EXP_BAR_AIR_FILL_1,
        CLARITY_EXP_BAR_AIR_FILL_2,
        CLARITY_EXP_BAR_AIR_FILL_3,
        CLARITY_EXP_BAR_AIR_FILL_4,
        CLARITY_EXP_BAR_AIR_FILL_5,
        CLARITY_EXP_BAR_AIR_FILL_6,
        CLARITY_EXP_BAR_AIR_FILL_7,
        CLARITY_EXP_BAR_AIR_FILL_8,
        CLARITY_EXP_BAR_AIR_FILL_9,
        CLARITY_EXP_BAR_AIR_FILL_PARTITION,





    }
}
