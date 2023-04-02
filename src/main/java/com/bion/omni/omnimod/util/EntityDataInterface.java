package com.bion.omni.omnimod.util;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.nbt.NbtCompound;

public interface EntityDataInterface {
    NbtCompound getPersistentData();
    TrackedData<Byte> getFlags();
}
