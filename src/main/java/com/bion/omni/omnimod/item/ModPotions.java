package com.bion.omni.omnimod.item;


import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.block.ModBlocks;
import com.bion.omni.omnimod.entity.effect.ModStatusEffects;
import com.bion.omni.omnimod.item.tech.Augmentation;
import com.bion.omni.omnimod.item.tech.ItemCannon;
import com.bion.omni.omnimod.item.tome.*;
import com.bion.omni.omnimod.item.wand.*;
import eu.pb4.polymer.core.api.item.PolymerBlockItem;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import eu.pb4.polymer.core.api.other.SimplePolymerPotion;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModPotions {

    public static final RegistryEntry<Potion> MANA_REGENERATION = register("mana_regeneration", new SimplePolymerPotion(new StatusEffectInstance(ModStatusEffects.MANA_REGENERATION, 300)));
    public static final RegistryEntry<Potion> MARK = register("mark", new SimplePolymerPotion(new StatusEffectInstance(ModStatusEffects.MARK, 1)));
    public static final RegistryEntry<Potion> RECALL = register("mark", new SimplePolymerPotion(new StatusEffectInstance(ModStatusEffects.RECALL, 1)));



    public static void registerItems() {

    }
    private static RegistryEntry<Potion> register(String name, Potion potion) {
        return Registry.registerReference(Registries.POTION, Identifier.of(OmniMod.MOD_ID, name), potion);
    }
    
    
}
