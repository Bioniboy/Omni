package com.bion.omni.omnimod.power;

import com.bion.omni.omnimod.util.Apprentice;
import com.bion.omni.omnimod.util.ConfigSymbol;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;

public abstract class ContinuousPower extends Power {
    public boolean isActive = false;

    public ContinuousPower(int level) {
        super(level);
    }

    public void use(ServerPlayerEntity user) {
        ((Apprentice)user).omni$getCosts().put(getId(), getManaCost());
    }
    public void stop(ServerPlayerEntity user) {

    }

    public void start(ServerPlayerEntity user) {

    }
    public boolean isActive(ServerPlayerEntity user) {
        return ((Apprentice)user).omni$getConfigValue(getId()) == 1;
    }

    @Override
    public ArrayList<ConfigSymbol> getConfigSymbols() {
        ArrayList<ConfigSymbol> configSymbols = new ArrayList<>();
        configSymbols.add(new ConfigSymbol("✔"));
        configSymbols.add(new ConfigSymbol("✖"));
        return configSymbols;
    }
}
