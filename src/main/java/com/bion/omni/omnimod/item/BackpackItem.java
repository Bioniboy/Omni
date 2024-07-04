package com.bion.omni.omnimod.item;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.block.BackpackBlock;
import com.bion.omni.omnimod.gui.BackpackGui;
import eu.pb4.polymer.core.api.item.PolymerBlockItem;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.block.Block;
import net.minecraft.component.Component;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.config.builder.api.CompositeFilterComponentBuilder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;

public class BackpackItem extends PolymerBlockItem implements Equipment{
    public BackpackItem(Item.Settings settings, Item polymerItem, Block block) {
        super(block, settings, polymerItem);
    }

    public static final int DEFAULT_COLOR = 0xB5651d;

    public int getColor(ItemStack itemStack) {
        return DyedColorComponent.getColor(itemStack, DEFAULT_COLOR);
    }

    public void openGUI(ServerPlayerEntity player, ItemStack backpackItem){
        int level = backpackItem.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getInt("backpack_level");
        SimpleInventory inventory = new SimpleInventory(9*level);
        backpackItem.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).copyTo(inventory.getHeldStacks());
        BackpackGui GUI = new BackpackGui(player, backpackItem, level, inventory);
        GUI.open();
    }
    public static NbtCompound getCustomData(int level){
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putInt("backpack_level", level);
        return nbtCompound;
    }

    public static Text getBackpackName(int level) {
        return switch (level){
            case 2 -> Text.translatable("container.small_backpack");
            case 3 -> Text.translatable("container.medium_backpack");
            case 4 -> Text.translatable("container.large_backpack");
            case 5 -> Text.translatable("container.massive_backpack");
            case 6 -> Text.translatable("container.giant_backpack");
            default -> Text.translatable("container.tiny_backpack");
        };
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && slot.id == 6){
            openGUI((ServerPlayerEntity) player, stack);
            return true;
        }
//        if(slot.id == 6 && !stack.getComponents().getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).stream().toList().isEmpty()){
//            return true;
//        }
         return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }


    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, RegistryWrapper.WrapperLookup lookup, @Nullable ServerPlayerEntity player) {
        ItemStack out = PolymerItemUtils.createItemStack(itemStack, tooltipType, lookup, player);
        //out.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(DyedColorComponent.DEFAULT_COLOR, true));
        out.set(DataComponentTypes.FIREWORK_EXPLOSION, new FireworkExplosionComponent(FireworkExplosionComponent.Type.CREEPER, IntList.of(itemStack.getOrDefault(DataComponentTypes.DYED_COLOR, new DyedColorComponent(DEFAULT_COLOR, false)).rgb()), IntList.of(), false, false));
        if(itemStack.getComponents().contains(DataComponentTypes.CUSTOM_DATA)){
            int modelData = 850000;
            modelData += (itemStack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT)).copyNbt().getInt("backpack_level");
            out.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(modelData));
        }
        return out;
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.CHEST;
    }

    @Override
    public String getTranslationKey() {
        return super.getOrCreateTranslationKey();
    }
}
