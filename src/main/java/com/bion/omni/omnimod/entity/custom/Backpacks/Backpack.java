package com.bion.omni.omnimod.entity.custom.Backpacks;

import eu.pb4.polymer.virtualentity.api.elements.EntityElement;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.server.world.ServerWorld;

public class Backpack extends EntityElement<ParrotEntity> {
    public Backpack(ParrotEntity entity, ServerWorld world) {
        super(EntityType.PARROT, world);
    }

}
