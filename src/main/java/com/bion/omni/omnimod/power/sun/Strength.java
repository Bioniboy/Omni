package com.bion.omni.omnimod.power.sun;

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

public class Strength extends ContinuousPower {
    private int strengthLevel = 0;

    public Strength(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Strength";
    }

    @Override
    public String getId() {
        return "strength";
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        if (strengthLevel < 4) {
            strengthLevel += 1;
        } else {
            strengthLevel = 0;
        }
        return true;
    }

    @Override
    public double getManaCost() {
        return strengthLevel + 1;
    }

    @Override
    public boolean isActive(ServerPlayerEntity user) {
        return super.isActive(user);
    }
    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 10, strengthLevel, true, false));
    }

    @Override
    public void stop(ServerPlayerEntity user) {
        user.removeStatusEffect(StatusEffects.STRENGTH);
    }

    @Override
    public ArrayList<ConfigSymbol> getConfigSymbols() {
        ArrayList<ConfigSymbol> configSymbols = super.getConfigSymbols();
        return configSymbols;
    }

    @Override
    public NbtCompound toNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = super.toNbt(registries);
        nbt.putInt("strengthLevel", strengthLevel);
        return nbt;
    }

    @Override
    public Power setNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.setNbt(nbt, registries);
        strengthLevel = nbt.getInt("strengthLevel");
        return this;
    }

    @Override
    public String getAdvancementId() {
        return "root";
    }
}
