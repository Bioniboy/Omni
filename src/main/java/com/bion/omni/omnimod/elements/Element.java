package com.bion.omni.omnimod.elements;

import com.bion.omni.omnimod.powers.Power;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public abstract class Element {
    protected Power[] powers;
    //public abstract Power[] getPowers();
    public abstract Formatting getColor();
//    public boolean isInDomain(ServerPlayerEntity player) {
//        return false;
//    }
    public abstract String getName();
    public abstract Power getPower(String id, Integer level);
    public abstract Power getPower(String id);
    public abstract String[] getPowerIds();
//    public Optional<Power> getPowerById(String id) {
//        for (Power power : getPowers()) {
//            if (Objects.equals(power.getId(), id)) {
//                return Optional.of(power);
//            }
//        }
//        return Optional.empty();
//    }
}
