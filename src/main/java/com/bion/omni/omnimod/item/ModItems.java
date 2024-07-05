package com.bion.omni.omnimod.item;



import com.bion.omni.omnimod.block.ModBlocks;
import com.bion.omni.omnimod.item.Food.SweetBerryPie;
import com.bion.omni.omnimod.item.tech.Augmentation;
import com.bion.omni.omnimod.item.tech.ItemCannon;
import com.bion.omni.omnimod.item.tome.*;
import com.bion.omni.omnimod.item.wand.*;
import eu.pb4.polymer.core.api.item.PolymerBlockItem;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import com.bion.omni.omnimod.OmniMod;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {


    public static final SimplePolymerItem OMNIUM_INGOT = new SimplePolymerItem(new Item.Settings(), Items.IRON_INGOT);
    public static final SimplePolymerItem NOCTONOMICON = new Noctonomicon(new Item.Settings(), Items.BOOK);
    public static final SimplePolymerItem CYCLONOMICON = new Cyclonomicon(new Item.Settings(), Items.BOOK);
    public static final SimplePolymerItem SOLEONOMICON = new Soleonomicon(new Item.Settings(), Items.BOOK);
    public static final SimplePolymerItem AERONOMICON = new Aeronomicon(new Item.Settings(), Items.BOOK);
    public static final SimplePolymerItem LIBRONOMICON = new Libronomicon(new Item.Settings(), Items.BOOK);
    public static final SimplePolymerItem VITANOMICON = new Vitanomicon(new Item.Settings(), Items.BOOK);
    public static final SimplePolymerItem PYRONOMICON = new Pyronomicon(new Item.Settings(), Items.BOOK);
    public static final SimplePolymerItem HYDRONOMICON = new Hydronomicon(new Item.Settings(), Items.BOOK);
    public static final SimplePolymerItem THAUMONOMICON = new Thaumonomicon(new Item.Settings(), Items.BOOK);
    public static final Item MOON_WAND = new MoonWand(new Item.Settings(), Items.STICK);
    public static final Item AIR_WAND = new AirWand(new Item.Settings(), Items.STICK);
    public static final Item STORM_WAND = new StormWand(new Item.Settings(), Items.STICK);
    public static final Item CLARITY_WAND = new ClarityWand(new Item.Settings(), Items.STICK);
    public static final Item LIFE_WAND = new LifeWand(new Item.Settings(), Items.STICK);
    public static final Item FIRE_WAND = new FireWand(new Item.Settings(), Items.STICK);
    public static final Item WATER_WAND = new WaterWand(new Item.Settings(), Items.STICK);
    public static final Item MAGIC_WAND = new MagicWand(new Item.Settings(), Items.STICK);
    public static final Item BAD_TRAPDOOR = new PolymerBlockItem(ModBlocks.BAD_TRAPDOOR, new Item.Settings(), Items.BIRCH_TRAPDOOR);
    public static final Item INFLUENCE_TOKEN = new InfluenceToken(new Item.Settings());
    public static final Item SPEED_AUGMENTATION = new Augmentation(new Item.Settings(), 850013);
    public static final Item ITEM_CANNON_AUGMENTATION = new Augmentation(new Item.Settings(), 850015);
    public static final Item ITEM_CANNON = new ItemCannon(new Item.Settings());
    public static final Item WRENCH = new Augmentation(new Item.Settings(), 850016);
    public static final Item BACKPACK_ITEM = new BackpackItem(new Item.Settings().component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).maxCount(1), Items.FIREWORK_STAR, ModBlocks.BACKPACK_BLOCK);
    public static final Item TINY_BACKPACK_ITEM = new BackpackItem(new Item.Settings().component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).maxCount(1).component(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(BackpackItem.getCustomData(1))), Items.FIREWORK_STAR, ModBlocks.BACKPACK_BLOCK);
    public static final Item SMALL_BACKPACK_ITEM = new BackpackItem(new Item.Settings().component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).maxCount(1).component(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(BackpackItem.getCustomData(2))), Items.FIREWORK_STAR, ModBlocks.BACKPACK_BLOCK);
    public static final Item MEDIUM_BACKPACK_ITEM = new BackpackItem(new Item.Settings().component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).maxCount(1).component(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(BackpackItem.getCustomData(3))), Items.FIREWORK_STAR, ModBlocks.BACKPACK_BLOCK);
    public static final Item LARGE_BACKPACK_ITEM = new BackpackItem(new Item.Settings().component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).maxCount(1).component(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(BackpackItem.getCustomData(4))), Items.FIREWORK_STAR, ModBlocks.BACKPACK_BLOCK);
    public static final Item MASSIVE_BACKPACK_ITEM = new BackpackItem(new Item.Settings().component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).maxCount(1).component(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(BackpackItem.getCustomData(5))), Items.FIREWORK_STAR, ModBlocks.BACKPACK_BLOCK);
    public static final Item GIANT_BACKPACK_ITEM = new BackpackItem(new Item.Settings().component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).maxCount(1).component(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(BackpackItem.getCustomData(6))), Items.FIREWORK_STAR, ModBlocks.BACKPACK_BLOCK);
    public static final Item SWEET_BERRY_PIE = new SweetBerryPie(new Item.Settings().component(DataComponentTypes.FOOD, new FoodComponent.Builder().nutrition(7).saturationModifier(0.3f).build()));



    public static void registerItems() {
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "omnium_ingot"), OMNIUM_INGOT);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "noctonomicon"), NOCTONOMICON);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "cyclonomicon"), CYCLONOMICON);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "soleonomicon"), SOLEONOMICON);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "aeronomicon"), AERONOMICON);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "libronomicon"), LIBRONOMICON);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "vitanomicon"), VITANOMICON);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "pyronomicon"), PYRONOMICON);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "hydronomicon"), HYDRONOMICON);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "thaumonomicon"), THAUMONOMICON);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "moon_wand"), MOON_WAND);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "air_wand"), AIR_WAND);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "storm_wand"), STORM_WAND);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "clarity_wand"), CLARITY_WAND);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "life_wand"), LIFE_WAND);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "fire_wand"), FIRE_WAND);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "water_wand"), WATER_WAND);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "magic_wand"), MAGIC_WAND);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "bad_trapdoor"), BAD_TRAPDOOR);
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, "influence_token"), INFLUENCE_TOKEN);
        registerItem("bionic_legs", SPEED_AUGMENTATION);
        registerItem("item_cannon_augmentation", ITEM_CANNON_AUGMENTATION);
        registerItem("item_cannon", ITEM_CANNON);
        registerItem("wrench", WRENCH);
        registerItem("backpack_item", BACKPACK_ITEM);
        registerItem("tiny_backpack_item", TINY_BACKPACK_ITEM);
        registerItem("small_backpack_item", SMALL_BACKPACK_ITEM);
        registerItem("medium_backpack_item", MEDIUM_BACKPACK_ITEM);
        registerItem("large_backpack_item", LARGE_BACKPACK_ITEM);
        registerItem("massive_backpack_item", MASSIVE_BACKPACK_ITEM);
        registerItem("giant_backpack_item", GIANT_BACKPACK_ITEM);
        registerItem("sweet_berry_pie", SWEET_BERRY_PIE);
    }
    private static void registerItem(String path, Item item){
        Registry.register(Registries.ITEM, Identifier.of(OmniMod.MOD_ID, path), item);
    }
    
    
}
