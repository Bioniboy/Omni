package com.bion.omni.omnimod.entity.custom;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.util.Apprentice;
import eu.pb4.polymer.core.api.entity.PolymerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;

public class Pet extends WolfEntity implements PolymerEntity {
    private EntityType<? extends LivingEntity> customType = null;
    public Pet(EntityType<? extends WolfEntity> entityType, World world) {
        super(entityType, world);
        setTamed(true, true);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("omnimod.PetType", EntityType.getId(getCustomType()).toString());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("omnimod.PetType", NbtElement.STRING_TYPE))
            setCustomType((EntityType<? extends LivingEntity>) EntityType.get(nbt.getString("omnimod.PetType")).get());
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (hand == Hand.MAIN_HAND)
            return super.interactMob(player, hand);
        return ActionResult.FAIL;
    }

    @Override
    public EntityType<?> getPolymerEntityType(ServerPlayerEntity player) {
        return customType == null ? EntityType.WOLF : customType;
    }
    public void setCustomType(EntityType<? extends LivingEntity> type) {
        customType = type;
    }
    public EntityType<? extends LivingEntity> getCustomType() {
        return customType;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        if (getOwner() != null) {
            getOwner().sendMessage(getName().copy().append(Text.literal(" is resting")));
            ((Apprentice)getOwner()).omni$setPetCooldown(36000);
        }
    }
    public void unRemove() {
        super.unsetRemoved();
    }
}
