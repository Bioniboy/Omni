package com.bion.omni.omnimod.entity;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.entity.custom.*;
import com.bion.omni.omnimod.power.earth.Accretion;
import eu.pb4.polymer.core.api.entity.PolymerEntityUtils;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    //public static final EntityType<TempBlock> TEMP_BLOCK = FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, TempBlock::new).build();
    public static final EntityType<PlayerBody> PLAYER_BODY = EntityType.Builder.create(PlayerBody::new, SpawnGroup.MISC).dimensions(0.75f, 1.8f).build();
    public static final EntityType<Pet> PET = EntityType.Builder.create(Pet::new, SpawnGroup.CREATURE).dimensions(0.75f, 0.75f).build();
    public static final EntityType<ManaBulletEntity> MANA_BULLET = EntityType.Builder.create(ManaBulletEntity::new, SpawnGroup.MISC).dimensions(0.5f, 0.5f).build();
    public static final EntityType<AccRockEntity> ACC_ROCK = EntityType.Builder.create(AccRockEntity::new, SpawnGroup.MISC).dimensions(5f, 5f).build();
    public static final EntityType<MansionZombie> MANSION_ZOMBIE = EntityType.Builder.create(MansionZombie::new, SpawnGroup.MONSTER).dimensions(0.75f, 1.8f).build();
    public static void registerEntities() {
        Registry.register(Registries.ENTITY_TYPE, Identifier.of(OmniMod.MOD_ID, "player_body"), PLAYER_BODY);
        FabricDefaultAttributeRegistry.register(PLAYER_BODY, MobEntity.createMobAttributes());

        Registry.register(Registries.ENTITY_TYPE, Identifier.of(OmniMod.MOD_ID, "pet"), PET);
        FabricDefaultAttributeRegistry.register(PET, WolfEntity.createWolfAttributes());

        Registry.register(Registries.ENTITY_TYPE, Identifier.of(OmniMod.MOD_ID, "mana_bullet"), MANA_BULLET);

        Registry.register(Registries.ENTITY_TYPE, Identifier.of(OmniMod.MOD_ID, "acc_rock"), ACC_ROCK);

        Registry.register(Registries.ENTITY_TYPE, Identifier.of(OmniMod.MOD_ID, "mansion_zombie"), MANSION_ZOMBIE);
        FabricDefaultAttributeRegistry.register(MANSION_ZOMBIE, ZombieEntity.createZombieAttributes());

        PolymerEntityUtils.registerType(PLAYER_BODY, PET, MANA_BULLET, MANSION_ZOMBIE, ACC_ROCK);

    }
}
