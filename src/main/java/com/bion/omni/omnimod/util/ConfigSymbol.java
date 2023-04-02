package com.bion.omni.omnimod.util;

import net.minecraft.text.MutableText;

public class ConfigSymbol {
    private final String symbol;
    private final MutableText description;
    private final String command;

    public ConfigSymbol(String symbol, MutableText description, String command) {
        this.symbol = symbol;
        this.description = description;
        this.command = command;
    }
    public ConfigSymbol(String symbol) {
        this(symbol, null, null);
    }
    public String getSymbol() {
        return symbol;
    }

    public MutableText getDescription () {
        return description;
    }

    public String getCommand() {
        return command;
    }
}
