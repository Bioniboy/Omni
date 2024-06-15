package com.bion.omni.omnimod.util;

import net.minecraft.text.MutableText;

public class CustomChar {
    public CustomChar(int p_width, int p_height, MutableText p_symbol) {
        width = p_width;
        height = p_height;
        symbol = p_symbol;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public MutableText getSymbol(){
        return symbol;
    }
    private int width;
    private int height;
    private MutableText symbol;
}



