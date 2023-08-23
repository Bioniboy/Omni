package com.bion.omni.omnimod.entity;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.entity.custom.PlayerBody;
import com.bion.omni.omnimod.entity.custom.TestEntity2;
import eu.pb4.polymer.core.api.entity.PolymerEntityUtils;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    //public static final EntityType<TempBlock> TEMP_BLOCK = FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, TempBlock::new).build();
    public static final EntityType<PlayerBody> PLAYER_BODY = FabricEntityTypeBuilder.create(SpawnGroup.MISC, PlayerBody::new).dimensions(new EntityDimensions(0.75f, 1.8f, true)).build();
    public static final EntityType<TestEntity2> ENTITY_2 = FabricEntityTypeBuilder.create(SpawnGroup.MISC, TestEntity2::new).dimensions(new EntityDimensions(0.75f, 1.8f, true)).build();
    public static void registerEntities() {
        Registry.register(Registries.ENTITY_TYPE, new Identifier(OmniMod.MOD_ID, "player_body"), PLAYER_BODY);
        FabricDefaultAttributeRegistry.register(PLAYER_BODY, MobEntity.createMobAttributes());

        Registry.register(Registries.ENTITY_TYPE, new Identifier("test", "entity2"), ENTITY_2);
        FabricDefaultAttributeRegistry.register(ENTITY_2, TestEntity2.createCreeperAttributes());


        PolymerEntityUtils.registerType(PLAYER_BODY, ENTITY_2);
    }
}
