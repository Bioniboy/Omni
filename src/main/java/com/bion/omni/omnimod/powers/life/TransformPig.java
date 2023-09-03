package com.bion.omni.omnimod.powers.life;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;

public class TransformPig extends TransformPower {
    @Override
    public String getName() {
        return "Transform Pig";
    }

    @Override
    public String getId() {
        return "transformPig";
    }

    @Override
    public String getAdvancementId() {
        return "pig";
    }

    @Override
    public Integer getInfluenceCost() {
        return 30;
    }

    @Override
    public double getManaCost() {
        return 1;
    }


    @Override
    public EntityType<? extends LivingEntity> getDisguiseType() {
        return EntityType.PIG;
    }
}
