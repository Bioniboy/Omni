package com.bion.omni.omnimod.entity;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.entity.custom.ManaBulletEntity;
import com.bion.omni.omnimod.entity.custom.MansionZombie;
import com.bion.omni.omnimod.entity.custom.Pet;
import com.bion.omni.omnimod.entity.custom.PlayerBody;
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
    public static final EntityType<PlayerBody> PLAYER_BODY = FabricEntityTypeBuilder.create(SpawnGroup.MISC, PlayerBody::new).dimensions(new EntityDimensions(0.75f, 1.8f, true)).build();
    public static final EntityType<Pet> PET = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, Pet::new).dimensions(new EntityDimensions(0.75f, 0.75f, true)).build();
    public static final EntityType<ManaBulletEntity> MANA_BULLET = FabricEntityTypeBuilder.create(SpawnGroup.MISC, ManaBulletEntity::new).dimensions(new EntityDimensions(0.5f, 0.5f, true)).build();
    public static final EntityType<MansionZombie> MANSION_ZOMBIE = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MansionZombie::new).dimensions(new EntityDimensions(0.75f, 1.8f, true)).build();
    public static void registerEntities() {
        Registry.register(Registries.ENTITY_TYPE, new Identifier(OmniMod.MOD_ID, "player_body"), PLAYER_BODY);
        FabricDefaultAttributeRegistry.register(PLAYER_BODY, MobEntity.createMobAttributes());

        Registry.register(Registries.ENTITY_TYPE, new Identifier(OmniMod.MOD_ID, "pet"), PET);
        FabricDefaultAttributeRegistry.register(PET, WolfEntity.createWolfAttributes());

        Registry.register(Registries.ENTITY_TYPE, new Identifier(OmniMod.MOD_ID, "mana_bullet"), MANA_BULLET);

        Registry.register(Registries.ENTITY_TYPE, new Identifier(OmniMod.MOD_ID, "mansion_zombie"), MANSION_ZOMBIE);
        FabricDefaultAttributeRegistry.register(MANSION_ZOMBIE, ZombieEntity.createZombieAttributes());

        PolymerEntityUtils.registerType(PLAYER_BODY, PET, MANA_BULLET, MANSION_ZOMBIE);

    }
}
