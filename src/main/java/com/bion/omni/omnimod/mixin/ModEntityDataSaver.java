package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.util.Apprentice;
import com.bion.omni.omnimod.util.EntityDataInterface;
import com.bion.omni.omnimod.util.PowerConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
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
            if (player.getMana() > -1) {
                nbt.putDouble("omnimod.mana", player.getMana());
            }
            if (player.getManaMaxLevel() != null) {
                nbt.putInt("omnimod.manaMaxLevel", player.getManaMaxLevel());
            }
            if (player.getManaRegenLevel() != null) {
                nbt.putInt("omnimod.manaRegenLevel", player.getManaRegenLevel());
            }
            if (player.getElement() != null) {
                nbt.putString("omnimod.element", player.getElement().getName());
            }
            if (!player.getAllPowers().isEmpty()) {
                NbtCompound powers = new NbtCompound();
                for (Power power : player.getAllPowers()) {
                    powers.put(power.getId(), power.toNbt());
                }
                nbt.put("omnimod.powers", powers);
            }
            if (!player.getConfig().isEmpty()) {
                StringBuilder config = new StringBuilder();
                for (PowerConfig entry : player.getConfig()) {
                    config.append(entry.getId()).append(":").append(entry.getValue()).append(",");
                }
                nbt.putString("omnimod.powerConfig", config.toString());
            }
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("omnimod.data", 10)) {
            persistentData = nbt.getCompound("omnimod.data");
        }
        if (((Entity)(Object)this) instanceof Apprentice player) {
            if (nbt.contains("omnimod.mana")) {
                player.setMana(nbt.getInt("omnimod.mana"));
            }
            if (nbt.contains("omnimod.manaMaxLevel")) {
                player.setManaMaxLevel(nbt.getInt("omnimod.manaMaxLevel"));
            }
            if (nbt.contains("omnimod.manaRegenLevel")) {
                player.setManaRegenLevel(nbt.getInt("omnimod.manaRegenLevel"));
            }
            if (nbt.contains("omnimod.element")) {
                player.setElement(nbt.getString("omnimod.element"));
            }
            if (nbt.contains("omnimod.powerConfig")) {
                for (String config : nbt.getString("omnimod.powerConfig").split(",")) {
                    String[] configList = config.split(":");
                    player.addConfig(configList[0], Integer.parseInt(configList[1]));
                }
            }
            if (nbt.contains("omnimod.powers", NbtElement.COMPOUND_TYPE)) {
                for (String key : nbt.getCompound("omnimod.powers").getKeys()) {
                    Power playerPower = player.addPower(key);
                    if (playerPower != null) {
                        playerPower.setNbt(nbt.getCompound("omnimod.powers").getCompound(key));
                    }
                }
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
