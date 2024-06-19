package com.bion.omni.omnimod.block.entity;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.block.ModBlocks;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<BackpackBlockEntity> BACKPACK = register("backpack",
            FabricBlockEntityTypeBuilder.create(BackpackBlockEntity::new).addBlocks(ModBlocks.BACKPACK_BLOCK));
    public static <T extends BlockEntity> BlockEntityType<T> register(String path, FabricBlockEntityTypeBuilder<T> item) {
        var x = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(OmniMod.MOD_ID, path), item.build());
        PolymerBlockUtils.registerBlockEntity(x);
        return x;
    }
    public static void registerBlockEntities(){

    }
}
