package com.bion.omni.omnimod.power.air;

import com.bion.omni.omnimod.power.ContinuousPower;
import com.bion.omni.omnimod.util.ConfigSymbol;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

public class Pacifism extends ContinuousPower {
    private int tickCounter = 0;
    private int effectId = 0;

    public Pacifism(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Pacifism " + getRomanNumeral(getLevel());
    }

    @Override
    public String getId() {
        return "pacifism";
    }
    @Override
    public String getAdvancementId() {
        return "pacifism" + (getLevel() == 1 ? "" : "_" + getLevel());
    }

    @Override
    public void use(ServerPlayerEntity user) {
        tickCounter++;
        if (effectId != 0) {
            StatusEffectInstance effect = switch(effectId) {
                case 1:
                    yield new StatusEffectInstance(StatusEffects.SPEED, 10, 0, false, false);
                case 2:
                    yield new StatusEffectInstance(StatusEffects.RESISTANCE, 10, 0, false, false);
                case 3:
                    yield new StatusEffectInstance(StatusEffects.LUCK, 10, 0, false, false);
                case 4:
                    yield new StatusEffectInstance(StatusEffects.HASTE, 10, 0, false, false);
                case 100:
                    yield new StatusEffectInstance(StatusEffects.REGENERATION, 10, 0, false, false);
                case 101:
                    yield new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 10, 0, false, false);
                case 102:
                    yield new StatusEffectInstance(StatusEffects.HASTE, 10, 1, false, false);
                default:
                    yield null;
            };
            user.addStatusEffect(effect);
        }
        if (tickCounter > 144000) {
            tickCounter = 120000;
            if (getLevel() == 1 || user.getRandom().nextDouble() < .9) {
                effectId = user.getRandom().nextBetween(1, 4);
            } else {
                effectId = user.getRandom().nextBetween(100, 102);
            }
        }
    }
    public void resetTimer() {
        tickCounter = 0;
    }

    @Override
    public Integer getInfluenceCost() {
        return switch (getLevel()) {
            case 1:
                yield 70;
            case 2:
                yield 50;
            default:
                yield 0;
        };
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        int secondsLeft = Math.floorDiv(144000 - tickCounter, 20);
        int seconds = secondsLeft % 60;
        int minutes = Math.floorDiv(secondsLeft, 60);
        user.sendMessage(Text.literal(minutes + " minutes and " + seconds + " seconds until your next power").formatted(Formatting.AQUA));
        return true;
    }

    @Override
    public void activate2(ServerPlayerEntity user) {
        effectId = 0;
        tickCounter = 143900;
    }

    @Override
    public ArrayList<ConfigSymbol> getConfigSymbols() {
        MutableText description1 = Text.literal("")
                .append(Text.literal("Time Left")
                        .formatted(Formatting.AQUA)
                );
        MutableText description2 = Text.literal("")
                .append(Text.literal("Clear Power")
                        .formatted(Formatting.AQUA)
                );
        ArrayList<ConfigSymbol> configSymbols = super.getConfigSymbols();
        configSymbols.add(new ConfigSymbol("⬤", description1, "activate"));
        configSymbols.add(new ConfigSymbol("⦻", description2, "activate.2"));
        return configSymbols;
    }
}
