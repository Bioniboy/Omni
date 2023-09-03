package com.bion.omni.omnimod.powers.life;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.powers.ContinuousPower;
import com.bion.omni.omnimod.util.EntityDisguise;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class TransformSheep extends TransformPower {

    private int bedCooldown = 0;
    @Override
    public String getName() {
        return "Transform Sheep";
    }

    @Override
    public String getId() {
        return "transformSheep";
    }

    @Override
    public String getAdvancementId() {
        return "sheep";
    }

    @Override
    public Integer getInfluenceCost() {
        return 30;
    }

    @Override
    public double getManaCost() {
        return 2;
    }

    @Override
    public void use(ServerPlayerEntity user) {
        super.use(user);
        if (bedCooldown <= 0 && !user.getInventory().containsAny((ItemStack item) -> item.getItem() instanceof BedItem)) {
            user.giveItemStack(new ItemStack(Items.WHITE_BED));
            bedCooldown = 100;
        } else if (bedCooldown > 0) {
            bedCooldown -= 1;
        }
    }


    @Override
    public EntityType<? extends LivingEntity> getDisguiseType() {
        return EntityType.SHEEP;
    }
}
