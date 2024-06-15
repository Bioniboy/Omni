package com.bion.omni.omnimod.item;



import com.bion.omni.omnimod.block.ModBlocks;
import com.bion.omni.omnimod.entity.effect.ManaRegeneration;
import com.bion.omni.omnimod.entity.effect.ModStatusEffects;
import com.bion.omni.omnimod.item.tech.Augmentation;
import com.bion.omni.omnimod.item.tech.ItemCannon;
import com.bion.omni.omnimod.item.tome.*;
import com.bion.omni.omnimod.item.wand.*;
import com.bion.omni.omnimod.power.tech.Wrench;
import eu.pb4.polymer.core.api.item.PolymerBlockItem;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import com.bion.omni.omnimod.OmniMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {


    public static final SimplePolymerItem OMNIUM_INGOT = new SimplePolymerItem(new FabricItemSettings(), Items.IRON_INGOT);
    public static final SimplePolymerItem NOCTONOMICON = new Noctonomicon(new FabricItemSettings(), Items.BOOK);
    public static final SimplePolymerItem CYCLONOMICON = new Cyclonomicon(new FabricItemSettings(), Items.BOOK);
    public static final SimplePolymerItem SOLEONOMICON = new Soleonomicon(new FabricItemSettings(), Items.BOOK);
    public static final SimplePolymerItem AERONOMICON = new Aeronomicon(new FabricItemSettings(), Items.BOOK);
    public static final SimplePolymerItem LIBRONOMICON = new Libronomicon(new FabricItemSettings(), Items.BOOK);
    public static final SimplePolymerItem VITANOMICON = new Vitanomicon(new FabricItemSettings(), Items.BOOK);
    public static final SimplePolymerItem PYRONOMICON = new Pyronomicon(new FabricItemSettings(), Items.BOOK);
    public static final SimplePolymerItem HYDRONOMICON = new Hydronomicon(new FabricItemSettings(), Items.BOOK);
    public static final SimplePolymerItem THAUMONOMICON = new Thaumonomicon(new FabricItemSettings(), Items.BOOK);
    public static final Item MOON_WAND = new MoonWand(new FabricItemSettings(), Items.STICK);
    public static final Item AIR_WAND = new AirWand(new FabricItemSettings(), Items.STICK);
    public static final Item STORM_WAND = new StormWand(new FabricItemSettings(), Items.STICK);
    public static final Item CLARITY_WAND = new ClarityWand(new FabricItemSettings(), Items.STICK);
    public static final Item LIFE_WAND = new LifeWand(new FabricItemSettings(), Items.STICK);
    public static final Item FIRE_WAND = new FireWand(new FabricItemSettings(), Items.STICK);
    public static final Item WATER_WAND = new WaterWand(new FabricItemSettings(), Items.STICK);
    public static final Item MAGIC_WAND = new MagicWand(new FabricItemSettings(), Items.STICK);
    public static final Item BAD_TRAPDOOR = new PolymerBlockItem(ModBlocks.BAD_TRAPDOOR, new FabricItemSettings(), Items.BIRCH_TRAPDOOR);
    public static final Potion MANA_REGENERATION = new Potion(new StatusEffectInstance(ModStatusEffects.MANA_REGENERATION, 300));
    public static final Potion MARK = new Potion(new StatusEffectInstance(ModStatusEffects.MARK, 1));
    public static final Potion RECALL = new Potion(new StatusEffectInstance(ModStatusEffects.RECALL, 1));
    public static final Item INFLUENCE_TOKEN = new InfluenceToken(new FabricItemSettings());
    public static final Item SPEED_AUGMENTATION = new Augmentation(new FabricItemSettings(), 850013);
    public static final Item ITEM_CANNON_AUGMENTATION = new Augmentation(new FabricItemSettings(), 850015);
    public static final Item ITEM_CANNON = new ItemCannon(new FabricItemSettings());
    public static final Item WRENCH = new Augmentation(new FabricItemSettings(), 850016);
    public static final Item BACKPACK = new BackpackItem(new FabricItemSettings(), Items.LEATHER_CHESTPLATE);



    public static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "omnium_ingot"), OMNIUM_INGOT);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "noctonomicon"), NOCTONOMICON);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "cyclonomicon"), CYCLONOMICON);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "soleonomicon"), SOLEONOMICON);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "aeronomicon"), AERONOMICON);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "libronomicon"), LIBRONOMICON);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "vitanomicon"), VITANOMICON);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "pyronomicon"), PYRONOMICON);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "hydronomicon"), HYDRONOMICON);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "thaumonomicon"), THAUMONOMICON);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "moon_wand"), MOON_WAND);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "air_wand"), AIR_WAND);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "storm_wand"), STORM_WAND);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "clarity_wand"), CLARITY_WAND);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "life_wand"), LIFE_WAND);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "fire_wand"), FIRE_WAND);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "water_wand"), WATER_WAND);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "magic_wand"), MAGIC_WAND);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "bad_trapdoor"), BAD_TRAPDOOR);
        Registry.register(Registries.POTION, new Identifier(OmniMod.MOD_ID, "mana_regeneration"), MANA_REGENERATION);
        Registry.register(Registries.POTION, new Identifier(OmniMod.MOD_ID, "mark"), MARK);
        Registry.register(Registries.POTION, new Identifier(OmniMod.MOD_ID, "recall"), RECALL);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "influence_token"), INFLUENCE_TOKEN);
        registerItem("bionic_legs", SPEED_AUGMENTATION);
        registerItem("item_cannon_augmentation", ITEM_CANNON_AUGMENTATION);
        registerItem("item_cannon", ITEM_CANNON);
        registerItem("wrench", WRENCH);
        registerItem("backpack", BACKPACK);

    }
    private static void registerItem(String path, Item item){
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, path), item);
    }
    private static void registerPotion(String path, Potion potion){
        Registry.register(Registries.POTION, new Identifier(OmniMod.MOD_ID, path), potion);
    }
}
