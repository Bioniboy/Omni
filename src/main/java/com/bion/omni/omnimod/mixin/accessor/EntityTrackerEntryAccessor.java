package com.bion.omni.omnimod.mixin.accessor;

import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.world.entity.EntityTrackingStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(targets = "net.minecraft.server.world.ServerChunkLoadingManager$EntityTracker")
public interface EntityTrackerEntryAccessor {
    @Accessor("entry")
    EntityTrackerEntry getEntry();
    @Accessor("listeners")
    Set<EntityTrackingStatus> getListeners();
}
