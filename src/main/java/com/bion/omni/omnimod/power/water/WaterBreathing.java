package com.bion.omni.omnimod.power.water;

import com.bion.omni.omnimod.power.ContinuousPower;
import com.bion.omni.omnimod.util.Apprentice;
import com.bion.omni.omnimod.util.ConfigSymbol;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

public class WaterBreathing extends ContinuousPower {
    public WaterBreathing(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Water Breathing";
    }

    @Override
    public String getId() {
        return "waterBreathing";
    }
    @Override
    public String getAdvancementId() {
        return "root";
    }

    @Override
    public double getManaCost() {
        return 1;
    }
    @Override
    public Boolean hasConfig() { return true; }

    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 10, 0, true, false));
    }
    @Override
    public void stop(ServerPlayerEntity user) {
        user.removeStatusEffect(StatusEffects.WATER_BREATHING);
    }

    @Override
    public boolean isActive(ServerPlayerEntity user) {
        return super.isActive(user) && user.isSubmergedInWater();
    }
//    @Override
//    public ArrayList<ConfigSymbol> getConfigSymbols() {
//        MutableText description = Text.literal("")
//                .append(Text.literal("Water mode\n")
//                        .formatted(Formatting.DARK_AQUA)
//                )
//                .append("In water mode, ")
//                .append(Text.literal(getName())
//                        .formatted(Formatting.DARK_AQUA)
//                )
//                .append(" is only applied while underwater");
//        ArrayList<ConfigSymbol> configSymbols = new ArrayList<>();
//        configSymbols.add(new ConfigSymbol("✔"));
//        configSymbols.add(new ConfigSymbol("✖"));
//        configSymbols.add(new ConfigSymbol("⭐", description, "2"));
//        return configSymbols;
//    }
}
