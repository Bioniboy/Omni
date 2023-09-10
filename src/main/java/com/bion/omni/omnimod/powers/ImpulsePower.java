package com.bion.omni.omnimod.powers;

import com.bion.omni.omnimod.util.Apprentice;
import com.bion.omni.omnimod.util.ConfigSymbol;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;

public abstract class ImpulsePower extends Power {
    protected int tickCounter = 0;
    public boolean isTicking() {
        return tickCounter > 0;
    }

    public void tick(ServerPlayerEntity user) {
        assert tickCounter > 0;
        tickCounter -= 1;
        if (tickCounter == 0) {
            end(user);
        }
    }

    public void end(ServerPlayerEntity user) { }
    @Override
    public boolean activate(ServerPlayerEntity user) {
        double mana = ((Apprentice)user).omni$getMana();
        if (mana >= getManaCost()) {
            ((Apprentice)user).omni$changeMana(-getManaCost());
            return true;
        } else {
            return false;
        }
    }
    @Override
    public ArrayList<ConfigSymbol> getConfigSymbols() {
        ArrayList<ConfigSymbol> configSymbols = new ArrayList<>();
        configSymbols.add(new ConfigSymbol("⬤"));
        return configSymbols;
    }
}
