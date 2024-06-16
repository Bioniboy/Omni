package com.bion.omni.omnimod.entity.effect;

import com.bion.omni.omnimod.OmniMod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {
    public static final RegistryEntry<StatusEffect> MANA_REGENERATION = register("mana_regeneration", new ManaRegeneration());
    public static final RegistryEntry<StatusEffect> MARK = register("mark", new Mark());
    public static final RegistryEntry<StatusEffect> RECALL = register("recall", new Recall());

    public static void registerEffects() {

    }

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.ofVanilla(id), statusEffect);
    }
}
