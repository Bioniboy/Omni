package com.bion.omni.omnimod.item;

import com.bion.omni.omnimod.gui.BackpackGui;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BackpackItem extends SimplePolymerItem {
    public BackpackItem(Item.Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }
    SimpleInventory backpackInventory = new SimpleInventory(27);
    private Integer level = 1;
    public Integer getLevel() { return level; }
    public SimpleInventory get(){
        return backpackInventory;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        BackpackGui backpackGui = new BackpackGui((ServerPlayerEntity)user, backpackInventory);
        backpackGui.open();
        return super.use(world, user, hand);
    }

}
