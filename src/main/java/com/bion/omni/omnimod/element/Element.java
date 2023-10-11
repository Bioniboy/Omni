package com.bion.omni.omnimod.element;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.power.Power;
import net.minecraft.loot.LootDataType;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public abstract class Element {
    protected ArrayList<Power> powers;
    //public abstract Power[] getPowers();
    public abstract Formatting getColor();
    public abstract boolean isInDomain(ServerPlayerEntity player);
    protected boolean testPredicate(ServerPlayerEntity player, String predicate) {
        Identifier identifier =  new Identifier("omni", predicate);
        LootManager lootManager = player.getServer().getLootManager();
        LootCondition condition = lootManager.getElement(LootDataType.PREDICATES, identifier);


        ServerWorld serverWorld = player.getServerWorld();
        LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder(serverWorld).add(LootContextParameters.ORIGIN, player.getPos()).addOptional(LootContextParameters.THIS_ENTITY, player).build(LootContextTypes.COMMAND);
        LootContext lootContext = new LootContext.Builder(lootContextParameterSet).build(null);
        lootContext.markActive(LootContext.predicate(condition));
        return condition.test(lootContext);
    }
    public abstract String getName();
    public Power getPower(String id, Integer level) {

        for (Power power : powers) {
            if (power.getId().equals(id)) {
                try {
                    return power.getClass().getConstructor(int.class).newInstance(level);
                } catch (Exception e) {
                    OmniMod.LOGGER.error("Failed to create power: " + id);
                    return null;
                }
            }
        }
        return null;
    }
    public Power getPower(String id) {
        return getPower(id, 1);
    }

    public ArrayList<String> getPowerIds() {
        ArrayList<String> ids = new ArrayList<>();
        for (Power power : powers) {
            ids.add(power.getId());
        }
        return ids;
    }

}
