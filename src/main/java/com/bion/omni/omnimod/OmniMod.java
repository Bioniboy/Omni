package com.bion.omni.omnimod;

import com.bion.omni.omnimod.block.ModBlocks;
import com.bion.omni.omnimod.block.entity.ModBlockEntities;
import com.bion.omni.omnimod.command.ModCommands;
import com.bion.omni.omnimod.entity.ModEntities;
import com.bion.omni.omnimod.entity.effect.ModStatusEffects;
import com.bion.omni.omnimod.item.ModItems;
import com.bion.omni.omnimod.item.ModPotions;
import com.bion.omni.omnimod.util.DisguiseLib;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OmniMod implements ModInitializer {
    public static final String MOD_ID = "omnimod";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.registerItems();
        ModCommands.registerCommands();
        ModEntities.registerEntities();
        ModStatusEffects.registerEffects();
        ModBlocks.registerBlocks();
        ModBlockEntities.registerBlockEntities();
        DisguiseLib.init();
        PolymerResourcePackUtils.addModAssets(MOD_ID);
        ModPotions.registerItems();
    }
}

