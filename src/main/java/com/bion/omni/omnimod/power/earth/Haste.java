package com.bion.omni.omnimod.power.earth;

import com.bion.omni.omnimod.power.ContinuousPower;
import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.util.Apprentice;
import com.bion.omni.omnimod.util.ConfigSymbol;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

public class Haste extends ContinuousPower {
    private int hasteLevel = 0;

    public Haste(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Haste";
    }

    @Override
    public String getId() {
        return "haste";
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        if (hasteLevel < 3) {
            hasteLevel += 1;
        } else {
            hasteLevel = 0;
        }
        return true;
    }

    @Override
    public double getManaCost() {
        return hasteLevel + 1;
    }

    @Override
    public boolean isActive(ServerPlayerEntity user) {
        return super.isActive(user);
    }
    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 10, hasteLevel, true, false));
    }

    @Override
    public void stop(ServerPlayerEntity user) {
        user.removeStatusEffect(StatusEffects.HASTE);
    }

    @Override
    public ArrayList<ConfigSymbol> getConfigSymbols() {
        ArrayList<ConfigSymbol> configSymbols = super.getConfigSymbols();
        return configSymbols;
    }

    @Override
    public NbtCompound toNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = super.toNbt(registries);
        nbt.putInt("hasteLevel", hasteLevel);
        return nbt;
    }

    @Override
    public Power setNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.setNbt(nbt, registries);
        hasteLevel = nbt.getInt("hasteLevel");
        return this;
    }

    @Override
    public String getAdvancementId() {
        return "root";
    }
}
