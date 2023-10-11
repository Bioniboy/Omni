package com.bion.omni.omnimod.power.fire;

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

public class FireResistance extends ContinuousPower {
    public FireResistance(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Fire Resistance";
    }

    @Override
    public String getId() {
        return "fireResistance";
    }
    @Override
    public String getAdvancementId() {
        return "root";
    }

    @Override
    public String getPreRequisiteId() {
        return null;
    }

    @Override
    public double getManaCost() {
        return 2;
    }
    @Override
    public Boolean hasConfig() { return true; }

    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 10, 0, true, false));
    }
    @Override
    public void stop(ServerPlayerEntity user) {
        user.removeStatusEffect(StatusEffects.FIRE_RESISTANCE);
    }

    @Override
    public boolean isActive(ServerPlayerEntity user) {
        return super.isActive(user) && user.isOnFire();
    }
//    @Override
//    public ArrayList<ConfigSymbol> getConfigSymbols() {
//        MutableText description = Text.literal("")
//                .append(Text.literal("Fire mode\n")
//                        .formatted(Formatting.GOLD)
//                )
//                .append("In fire mode, ")
//                .append(Text.literal(getName())
//                        .formatted(Formatting.GOLD)
//                )
//                .append(" is only applied while on fire");
//        ArrayList<ConfigSymbol> configSymbols = new ArrayList<>();
//        configSymbols.add(new ConfigSymbol("✔"));
//        configSymbols.add(new ConfigSymbol("✖"));
//        configSymbols.add(new ConfigSymbol("⭐", description, "2"));
//        return configSymbols;
//    }
}
