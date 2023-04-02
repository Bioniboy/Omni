package com.bion.omni.omnimod.powers.moon;

import com.bion.omni.omnimod.powers.ContinuousPower;
import com.bion.omni.omnimod.util.Apprentice;
import com.bion.omni.omnimod.util.ConfigSymbol;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

public class Invisibility extends ContinuousPower {
    @Override
    public String getName() {
        return "Invisibility";
    }

    @Override
    public String getId() {
        return "invisibility";
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
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 10, 0, true, false));
    }
    @Override
    public void stop(ServerPlayerEntity user) {
        user.removeStatusEffect(StatusEffects.INVISIBILITY);
    }

    @Override
    public boolean isActive(ServerPlayerEntity user) {
        return super.isActive(user) || ((Apprentice)user).getConfigValue(getId()) == 2 && user.isSneaking();
    }
    @Override
    public ArrayList<ConfigSymbol> getConfigSymbols() {
        MutableText description = Text.literal("")
                .append(Text.literal("Sneak mode\n")
                        .formatted(Formatting.DARK_BLUE)
                )
                .append("In sneak mode, ")
                .append(Text.literal(getName())
                        .formatted(Formatting.DARK_BLUE)
                )
                .append(" is only applied while sneaking");
        ArrayList<ConfigSymbol> configSymbols = new ArrayList<>();
        configSymbols.add(new ConfigSymbol("✔"));
        configSymbols.add(new ConfigSymbol("✖"));
        configSymbols.add(new ConfigSymbol("⭐", description, "2"));
        return configSymbols;
    }
}
