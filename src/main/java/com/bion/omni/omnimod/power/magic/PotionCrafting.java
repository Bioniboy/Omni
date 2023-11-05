package com.bion.omni.omnimod.power.magic;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.entity.ModEntities;
import com.bion.omni.omnimod.item.ModItems;
import com.bion.omni.omnimod.power.ImpulsePower;
import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.util.ConfigSymbol;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PotionCrafting extends ImpulsePower {
    SimpleInventory items = new SimpleInventory(9);
    BlockPos cauldronPos = null;
    final ArrayList<Pair<List<Pair<Item, Integer>>, String>> recipes = new ArrayList<>(Arrays.asList(
            Pair.of(List.of(
                    Pair.of(Items.NETHER_WART, 1),
                    Pair.of(Items.CRYING_OBSIDIAN, 1)
            ), "recall")
    ));
    public PotionCrafting(int level) {
        super(level);
        for (var bed : List.of(Items.BLACK_BED, Items.BLUE_BED, Items.BROWN_BED, Items.CYAN_BED, Items.GRAY_BED, Items.GREEN_BED, Items.LIGHT_BLUE_BED, Items.LIGHT_GRAY_BED, Items.LIME_BED, Items.MAGENTA_BED, Items.ORANGE_BED, Items.PINK_BED, Items.PURPLE_BED, Items.RED_BED, Items.YELLOW_BED, Items.WHITE_BED)) {
            recipes.add(Pair.of(List.of(
                    Pair.of(Items.NETHER_WART, 1),
                    Pair.of(bed, 1),
                    Pair.of(Items.RESPAWN_ANCHOR, 1),
                    Pair.of(Items.GLOWSTONE, 4)
            ), "mark"));
        }
    }
    public void addItem(ItemStack item, BlockPos cauldronPos, ServerPlayerEntity user) {
        if (!cauldronPos.equals(this.cauldronPos)) {
            reset(user);
            this.cauldronPos = new BlockPos(cauldronPos);
        }
        items.addStack(item);
        user.getServerWorld().playSound(null, cauldronPos.getX(), cauldronPos.getY(), cauldronPos.getZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 0.5f, 1.8f / (user.getServerWorld().getRandom().nextFloat() * 0.4f + 0.8f));
    }
    private void reset(ServerPlayerEntity user) {
        if (cauldronPos != null)
            ItemScatterer.spawn(user.getWorld(), cauldronPos.up(), items);

        items.clear();
    }

    @Override
    public String getAdvancementId() {
        return "root";
    }

    @Override
    public String getName() {
        return "Potion Crafting";
    }

    @Override
    public String getId() {
        return "potionCrafting";
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        if (cauldronPos == null) return false;
        String recipeId = getRecipe();
        if (recipeId == null) {
            user.sendMessageToClient(Text.literal("Hmm, that combination of ingredients didn't seem to do anything...").formatted(Formatting.DARK_PURPLE), false);
            user.getServerWorld().playSound(null, cauldronPos.getX(), cauldronPos.getY(), cauldronPos.getZ(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 1.5f / (user.getServerWorld().getRandom().nextFloat() * 0.4f + 0.8f));
        } else {
            user.getServerWorld().playSound(null, cauldronPos.getX(), cauldronPos.getY(), cauldronPos.getZ(), SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 0.5f, 1.5f / (user.getServerWorld().getRandom().nextFloat() * 0.4f + 0.8f));
            switch (recipeId) {
                case "mark":
                    ItemScatterer.spawn(user.getServerWorld(), cauldronPos.getX(), cauldronPos.getY(), cauldronPos.getZ(), PotionUtil.setPotion(new ItemStack(Items.POTION), ModItems.MARK));
                    break;
                case "recall":
                    ItemScatterer.spawn(user.getServerWorld(), cauldronPos.getX(), cauldronPos.getY(), cauldronPos.getZ(), PotionUtil.setPotion(new ItemStack(Items.POTION), ModItems.RECALL));
                    break;

            }
        }
        items.clear();
        user.getServerWorld().setBlockState(cauldronPos, Blocks.CAULDRON.getDefaultState());
        return true;
    }

    @Override
    public boolean isTicking() {
        return !items.isEmpty() && cauldronPos != null;
    }

    @Override
    public void tick(ServerPlayerEntity user) {
        if (!user.getServerWorld().getBlockState(cauldronPos).isOf(Blocks.WATER_CAULDRON)) {
            reset(user);
        }
    }

    String getRecipe() {
        for (var recipe : recipes) {
            if (isRecipeValid(recipe.getFirst())) {
                return recipe.getSecond();
            }
        }
        return null;
    }
    boolean isRecipeValid(List<Pair<Item, Integer>> recipe) {
        for (var listItem : recipe) {
            if (items.count(listItem.getFirst()) != listItem.getSecond())
                return false;
        }
        return recipe.size() == items.stacks.stream().filter(stack -> !stack.isEmpty()).count();
    }
}
