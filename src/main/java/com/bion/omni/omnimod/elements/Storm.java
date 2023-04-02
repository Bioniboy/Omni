package com.bion.omni.omnimod.elements;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.powers.storm.*;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionTypes;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;


public class Storm extends Element {
    @Override
    public Power getPower(String id, Integer level) {
        return switch (id) {
            case "speed":
                yield new Speed();
            case "explosionResistance":
                yield new ExplosionResistance(level);
            case "creeperDisguise":
                yield new CreeperDisguise();
            case "goBoom":
                yield new GoBoom();
            case "kineticResistance":
                yield new KineticResistance(level);
            case "fallDamageResistance":
                yield new FallDamageResistance(level);
            case "selfDestruct":
                yield new SelfDestruct();
            case "makeItRain":
                yield new MakeItRain();
            case "manaRocket":
                yield new ManaRocket();
            case "yeet":
                yield new Yeet(level);
            case "mark":
                yield new Mark();
            case "spinMe":
                yield new SpinMe();
            case "whatWasThat":
                yield new WhatWasThat();
            case "hammerTime":
                yield new HammerTime();
            default:
                yield null;
        };
    }
    @Override
    public String[] getPowerIds() {
        return new String[] {
                "speed",
                "explosionResistance",
                "creeperDisguise",
                "goBoom",
                "selfDestruct",
                "kineticResistance",
                "fallDamageResistance",
                "makeItRain",
                "manaRocket",
                "yeet",
                "mark",
                "spinMe",
                "whatWasThat",
                "hammerTime"
        };
    }
    @Override
    public Power getPower(String id) {
        return getPower(id, 1);
    }
    @Override
    public String getName() {
        return "Storm";
    }

    @Override
    public Formatting getColor() {
        return Formatting.DARK_GRAY;
    }

//    @Override
//    public boolean isInDomain(ServerPlayerEntity player) {
//        Identifier id = new Identifier("omni", "is_above_150");
//        LootCondition isHigh = player.getServer().getPredicateManager().get(id);
//        OmniMod.LOGGER.info(LootContextTypes.);
////        if (isHigh != null) {
////            if (isHigh.test(new LootContext())) {
////                OmniMod.LOGGER.info("High!");
////            } else {
////                OmniMod.LOGGER.info("Not high :(");
////            }
////        }
//        return true;
//    }
}
