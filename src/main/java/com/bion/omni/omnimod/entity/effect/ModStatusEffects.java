package com.bion.omni.omnimod.entity.effect;

import com.bion.omni.omnimod.OmniMod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {
    public static final StatusEffect MANA_REGENERATION = new ManaRegeneration();
    public static final StatusEffect MARK = new Mark();
    public static final StatusEffect RECALL = new Recall();

    public static void registerEffects() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(OmniMod.MOD_ID, "mana_regeneration"), MANA_REGENERATION);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(OmniMod.MOD_ID, "mark"), MARK);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(OmniMod.MOD_ID, "recall"), RECALL);
    }
}
