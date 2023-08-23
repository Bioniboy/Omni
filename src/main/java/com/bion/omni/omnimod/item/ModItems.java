package com.bion.omni.omnimod.item;



import com.bion.omni.omnimod.block.ModBlocks;
import com.bion.omni.omnimod.item.tome.*;
import com.bion.omni.omnimod.item.wand.*;
import eu.pb4.polymer.core.api.item.PolymerBlockItem;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import com.bion.omni.omnimod.OmniMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
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
    public static final Item MOON_WAND = new MoonWand(new FabricItemSettings(), Items.STICK);
    public static final Item AIR_WAND = new AirWand(new FabricItemSettings(), Items.STICK);
    public static final Item STORM_WAND = new StormWand(new FabricItemSettings(), Items.STICK);
    public static final Item CLARITY_WAND = new ClarityWand(new FabricItemSettings(), Items.STICK);
    public static final Item BAD_TRAPDOOR = new PolymerBlockItem(ModBlocks.BAD_TRAPDOOR, new FabricItemSettings(), Items.BIRCH_TRAPDOOR);


    public static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "omnium_ingot"), OMNIUM_INGOT);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "noctonomicon"), NOCTONOMICON);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "cyclonomicon"), CYCLONOMICON);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "soleonomicon"), SOLEONOMICON);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "aeronomicon"), AERONOMICON);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "libronomicon"), LIBRONOMICON);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "moon_wand"), MOON_WAND);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "air_wand"), AIR_WAND);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "storm_wand"), STORM_WAND);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "clarity_wand"), CLARITY_WAND);
        Registry.register(Registries.ITEM, new Identifier(OmniMod.MOD_ID, "bad_trapdoor"), BAD_TRAPDOOR);
    }
}
