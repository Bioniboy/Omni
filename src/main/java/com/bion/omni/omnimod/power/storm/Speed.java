package com.bion.omni.omnimod.power.storm;

import com.bion.omni.omnimod.power.ContinuousPower;
import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.util.Apprentice;
import com.bion.omni.omnimod.util.ConfigSymbol;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

public class Speed extends ContinuousPower {
    private int speedLevel = 0;

    public Speed(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Speed";
    }

    @Override
    public String getId() {
        return "speed";
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        if (speedLevel < 4) {
            speedLevel += 1;
        } else {
            speedLevel = 0;
        }
        return true;
    }

    @Override
    public double getManaCost() {
        return speedLevel + 1;
    }

    @Override
    public boolean isActive(ServerPlayerEntity user) {
        return super.isActive(user) || ((Apprentice)user).omni$getConfigValue(getId()) == 2 && user.isSprinting();
    }
    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, speedLevel, true, false));
    }

    @Override
    public void stop(ServerPlayerEntity user) {
        user.removeStatusEffect(StatusEffects.SPEED);
    }

    @Override
    public ArrayList<ConfigSymbol> getConfigSymbols() {
        ArrayList<ConfigSymbol> configSymbols = super.getConfigSymbols();
        MutableText description = Text.literal("")
                .append(Text.literal("Sprint mode\n")
                        .formatted(Formatting.DARK_GRAY)
                )
                .append("In sprint mode, ")
                .append(Text.literal(getName())
                        .formatted(Formatting.DARK_GRAY)
                )
                .append(" is only applied while sprinting");
        configSymbols.add(new ConfigSymbol("⭐", description, "2"));
        configSymbols.add(new ConfigSymbol("" + (char)(9312 + speedLevel), Text.literal("Change speed level"), "activate.1.reopen"));
        return configSymbols;
    }

    @Override
    public NbtCompound toNbt() {
        NbtCompound nbt = super.toNbt();
        nbt.putInt("speedLevel", speedLevel);
        return nbt;
    }

    @Override
    public Power setNbt(NbtCompound nbt) {
        super.setNbt(nbt);
        speedLevel = nbt.getInt("speedLevel");
        return this;
    }

    @Override
    public String getAdvancementId() {
        return "root";
    }
}
