package com.bion.omni.omnimod.power.life;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;

public class TransformSheep extends TransformPower {

    private int bedCooldown = 0;

    public TransformSheep(int level) {
        super(level);
    }

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
