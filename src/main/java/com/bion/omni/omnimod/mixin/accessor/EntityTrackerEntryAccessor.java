package com.bion.omni.omnimod.mixin.accessor;

import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.world.EntityTrackingListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(targets = "net.minecraft.server.world.ThreadedAnvilChunkStorage$EntityTracker")
public interface EntityTrackerEntryAccessor {
    @Accessor("entry")
    EntityTrackerEntry getEntry();
    @Accessor("listeners")
    Set<EntityTrackingListener> getListeners();
}
