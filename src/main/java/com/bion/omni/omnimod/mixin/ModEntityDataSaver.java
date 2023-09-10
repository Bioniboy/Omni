package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.util.AfkUtil;
import com.bion.omni.omnimod.util.Apprentice;
import com.bion.omni.omnimod.util.EntityDataInterface;
import com.bion.omni.omnimod.util.PowerConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class ModEntityDataSaver implements EntityDataInterface {
    private NbtCompound persistentData;
    @Final
    @Shadow protected static TrackedData<Byte> FLAGS;

    @Override
    public TrackedData<Byte> getFlags () {
        return FLAGS;
    }


    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> info) {
        if (persistentData != null) {
            nbt.put("omnimod.data", persistentData);
        }
        if (((Entity)(Object)this) instanceof Apprentice player) {
            if (player.omni$getMana() > -1) {
                nbt.putDouble("omnimod.mana", player.omni$getMana());
            }
            if (player.omni$getManaMaxLevel() != null) {
                nbt.putInt("omnimod.manaMaxLevel", player.omni$getManaMaxLevel());
            }
            if (player.omni$getManaRegenLevel() != null) {
                nbt.putInt("omnimod.manaRegenLevel", player.omni$getManaRegenLevel());
            }
            if (player.omni$getElement() != null) {
                nbt.putString("omnimod.element", player.omni$getElement().getName());
            }
            if (!player.omni$getAllPowers().isEmpty()) {
                NbtCompound powers = new NbtCompound();
                for (Power power : player.omni$getAllPowers()) {
                    powers.put(power.getId(), power.toNbt());
                }
                nbt.put("omnimod.powers", powers);
            }
            if (!player.omni$getConfig().isEmpty()) {
                StringBuilder config = new StringBuilder();
                for (PowerConfig entry : player.omni$getConfig()) {
                    config.append(entry.getId()).append(":").append(entry.getValue()).append(",");
                }
                nbt.putString("omnimod.powerConfig", config.toString());
            }
            nbt.putInt("omnimod.activeTicks", ((AfkUtil)this).omni$getActiveTicks());
            nbt.putInt("omnimod.prevActiveDay", ((AfkUtil)this).omni$getPrevActiveDay());
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("omnimod.data", 10)) {
            persistentData = nbt.getCompound("omnimod.data");
        }
        if (((Entity)(Object)this) instanceof Apprentice player) {
            if (nbt.contains("omnimod.mana")) {
                player.omni$setMana(nbt.getInt("omnimod.mana"));
            }
            if (nbt.contains("omnimod.manaMaxLevel")) {
                player.omni$setManaMaxLevel(nbt.getInt("omnimod.manaMaxLevel"));
            }
            if (nbt.contains("omnimod.manaRegenLevel")) {
                player.omni$setManaRegenLevel(nbt.getInt("omnimod.manaRegenLevel"));
            }
            if (nbt.contains("omnimod.element")) {
                player.omni$setElement(nbt.getString("omnimod.element"));
            }
            if (nbt.contains("omnimod.powerConfig")) {
                for (String config : nbt.getString("omnimod.powerConfig").split(",")) {
                    String[] configList = config.split(":");
                    player.omni$addConfig(configList[0], Integer.parseInt(configList[1]));
                }
            }
            if (nbt.contains("omnimod.powers", NbtElement.COMPOUND_TYPE)) {
                for (String key : nbt.getCompound("omnimod.powers").getKeys()) {
                    Power playerPower = player.omni$addPower(key);
                    if (playerPower != null) {
                        playerPower.setNbt(nbt.getCompound("omnimod.powers").getCompound(key));
                    }
                }
            }
            if (nbt.contains("omnimod.activeTicks")) {
                ((AfkUtil)this).omni$setActiveTicks(nbt.getInt("omnimod.activeTicks"));
            }
            if (nbt.contains("omnimod.prevActiveDay")) {
                ((AfkUtil)this).omni$setPrevActiveDay(nbt.getInt("omnimod.prevActiveDay"));
            }
        }
    }

    @Override
    public NbtCompound getPersistentData() {
        if (persistentData == null) {
            persistentData = new NbtCompound();
        }
        return this.persistentData;
    }
}
