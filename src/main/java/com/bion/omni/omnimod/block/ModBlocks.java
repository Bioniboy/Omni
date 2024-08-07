package com.bion.omni.omnimod.block;


import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.block.tech.DivineRepoBlock;
import com.bion.omni.omnimod.block.tech.TinkerTableBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.PickaxeItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block BURNT_TORCH = new BurntTorch(AbstractBlock.Settings.create());
    public static final Block BURNT_WALL_TORCH = new WallBurntTorch(AbstractBlock.Settings.create());
    public static final Block DARK_BLOCK = new DarkBlock(AbstractBlock.Settings.create());
    public static final Block SMALL_DARK_BLOCK = new SmallDarkBlock(AbstractBlock.Settings.create());
    public static final Block SOLID_AIR = new SolidAirBlock(AbstractBlock.Settings.create().dropsNothing());
    public static final Block COMMAND_TNT = new CommandTNT(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).requiresTool().strength(-1.0f, 3600000.0f).dropsNothing(), false);
    public static final Block BAD_TRAPDOOR = new BadTrapdoor(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(3.0f).sounds(BlockSoundGroup.WOOD).nonOpaque(), BlockSetType.BIRCH);
    public static final Block TINKER_TABLE = new TinkerTableBlock(AbstractBlock.Settings.create());
    public static final Block DIVINE_REPO   = new DivineRepoBlock(AbstractBlock.Settings.create());
    public static final Block BACKPACK_BLOCK   = new BackpackBlock(AbstractBlock.Settings.create().nonOpaque());

    public static void registerBlocks() {
        Registry.register(Registries.BLOCK, Identifier.of(OmniMod.MOD_ID, "burnt_torch"), BURNT_TORCH);
        Registry.register(Registries.BLOCK, Identifier.of(OmniMod.MOD_ID, "burnt_wall_torch"), BURNT_WALL_TORCH);
        Registry.register(Registries.BLOCK, Identifier.of(OmniMod.MOD_ID, "dark_block"), DARK_BLOCK);
        Registry.register(Registries.BLOCK, Identifier.of(OmniMod.MOD_ID, "small_dark_block"), SMALL_DARK_BLOCK);
        Registry.register(Registries.BLOCK, Identifier.of(OmniMod.MOD_ID, "solid_air"), SOLID_AIR);
        Registry.register(Registries.BLOCK, Identifier.of(OmniMod.MOD_ID, "command_tnt"), COMMAND_TNT);
        Registry.register(Registries.BLOCK, Identifier.of(OmniMod.MOD_ID, "bad_trapdoor"), BAD_TRAPDOOR);
        Registry.register(Registries.BLOCK, Identifier.of(OmniMod.MOD_ID, "tinker_table"), TINKER_TABLE);
        Registry.register(Registries.BLOCK, Identifier.of(OmniMod.MOD_ID, "divine_repository"), DIVINE_REPO);
        Registry.register(Registries.BLOCK, Identifier.of(OmniMod.MOD_ID, "backpack_block"), BACKPACK_BLOCK);
    }
}
