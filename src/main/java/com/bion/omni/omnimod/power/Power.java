package com.bion.omni.omnimod.power;

import com.bion.omni.omnimod.util.ConfigSymbol;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;

public abstract class Power {
    public Power(int level) {
        setLevel(level);
    }
    private Integer level = 1;
    public Integer getLevel() { return level; }
    public void setLevel(Integer value) { level = value; }
    public Power increaseLevel() { level += 1; return this; }
    public abstract String getName();
    public abstract String getId();
    public String getAdvancementId() {
        return "";
    }
    public Boolean hasConfig() { return true; };
    public String getPreRequisiteId() { return null; }
    public Integer getInfluenceCost() { return 0; }
    public double getManaCost() { return 0; }
    public Integer getMaxLevel() { return 1; }
    protected String getRomanNumeral(Integer number) {
        return switch(number) {
            case 1:
                yield "I";
            case 2:
                yield "II";
            case 3:
                yield "III";
            case 4:
                yield "IV";
            case 5:
                yield "V";
            default:
                yield "";
        };
    }
    public abstract ArrayList<ConfigSymbol> getConfigSymbols();
    public boolean activate(ServerPlayerEntity user) {return true;};
    public void activate2(ServerPlayerEntity user) {}
    public void activate3(ServerPlayerEntity user) {}
    public NbtCompound toNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("level", getLevel());
        return nbt;
    }
    public Power setNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        setLevel(nbt.getInt("level"));
        return this;
    }
    public void onDisconnect(ServerPlayerEntity user) {}
    public int getDefaultConfig() { return 0; }
}
