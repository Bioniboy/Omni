package com.bion.omni.omnimod.element;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.power.Power;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Element {
    protected ArrayList<Power> powers;
    //public abstract Power[] getPowers();
    public abstract Formatting getColor();
    public abstract boolean isInDomain(ServerPlayerEntity player);
    protected boolean testPredicate(ServerPlayerEntity player, String predicate) {
        Identifier identifier =  Identifier.of("omni", predicate);
        RegistryKey<LootCondition> registryKey = RegistryKey.of(RegistryKeys.PREDICATE, identifier);

        ServerWorld serverWorld = player.getServerWorld();
        Optional<LootCondition> optional = serverWorld.getServer().getReloadableRegistries().createRegistryLookup().getOptionalEntry(RegistryKeys.PREDICATE, registryKey).map(RegistryEntry::value);
        if (optional.isEmpty()) {
            return false;
        }
        LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder(serverWorld).add(LootContextParameters.THIS_ENTITY, player).add(LootContextParameters.ORIGIN, player.getPos()).build(LootContextTypes.SELECTOR);
        LootContext lootContext = new LootContext.Builder(lootContextParameterSet).build(Optional.empty());
        lootContext.markActive(LootContext.predicate(optional.get()));
        return optional.get().test(lootContext);
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
