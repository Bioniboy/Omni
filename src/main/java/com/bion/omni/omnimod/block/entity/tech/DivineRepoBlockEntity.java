package com.bion.omni.omnimod.block.entity.tech;

import com.bion.omni.omnimod.power.tech.DivineRepo;
import com.bion.omni.omnimod.util.Apprentice;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class DivineRepoBlockEntity extends BlockEntity implements Inventory {
    private UUID ownerUUID = null;
    public DivineRepoBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public void setOwnerUUID(UUID uuid) {
        ownerUUID = uuid;
    }
    public DivineRepo getRepo() {
        if (ownerUUID != null && ((ServerWorld)this.getWorld()).getEntity(ownerUUID) instanceof Apprentice apprentice) {
            DivineRepo divineRepo;
            if ((divineRepo = (DivineRepo) apprentice.omni$getPowerById("divineRepo")) != null) {
                return divineRepo;
            }
        }
        return null;
    }
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {

    }
}
