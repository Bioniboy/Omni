package com.bion.omni.omnimod.item.tech;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.power.tech.ItemCannonAugmentation;
import com.bion.omni.omnimod.util.Apprentice;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtInt;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ItemCannon extends Item implements PolymerItem {
    public ItemCannon(Settings settings) {
        super(settings.maxDamage(100));
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return Items.WOODEN_SWORD;
    }
    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipContext context, @Nullable ServerPlayerEntity player) {

        ItemStack item = PolymerItemUtils.createItemStack(itemStack, context, player);
        item.setSubNbt("CustomModelData", NbtInt.of(850001));
        ItemCannonAugmentation itemCannonAugmentation = (ItemCannonAugmentation)((Apprentice)player).omni$getPowerById("itemCannonAugmentation");
        if(itemCannonAugmentation != null){
            item.setSubNbt("Damage", NbtInt.of(100 -(itemCannonAugmentation.getDurability()/itemCannonAugmentation.getMaxDurability())*100));
            OmniMod.LOGGER.info("" + (100 - itemCannonAugmentation.getDurability()/itemCannonAugmentation.getMaxDurability()));
        }

        return item;
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, SoundCategory.MASTER, 1, 1);

        ItemCannonAugmentation itemCannonAugmentation = (ItemCannonAugmentation)((Apprentice)user).omni$getPowerById("itemCannonAugmentation");
        if(itemCannonAugmentation == null){
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
        OmniMod.LOGGER.info("Before Item Damage: " + itemCannonAugmentation.getDurability() + "/" + itemCannonAugmentation.getMaxDurability());
        itemCannonAugmentation.decrementDurability();
        OmniMod.LOGGER.info("After Item Damage: " + itemCannonAugmentation.getDurability() + "/" + itemCannonAugmentation.getMaxDurability());


        return super.use(world, user, hand);
    }




}
