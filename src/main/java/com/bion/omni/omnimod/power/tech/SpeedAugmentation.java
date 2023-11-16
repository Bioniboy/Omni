package com.bion.omni.omnimod.power.tech;

import com.bion.omni.omnimod.item.ModItems;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.List;

public class SpeedAugmentation extends Augmentation{
    public SpeedAugmentation(int level) {
        super(level, ModItems.SPEED_AUGMENTATION);
        addUpgrade('\ue030', "\uf80a\uf809\uf802", List.of(
                new SimpleInventory(
                        new ItemStack(Items.SUGAR, 1728)
                ),
                new SimpleInventory(
                        new ItemStack(Items.SUGAR, 1728)
                )
        ), List.of(20,30), 2);


        addUpgrade('\ue031',"\uf80a\uf809\uf802", List.of(
                new SimpleInventory(
                        new ItemStack(Items.SUGAR, 1728)
                ),
                new SimpleInventory(
                        new ItemStack(Items.SUGAR, 1728)
                ),
                new SimpleInventory(
                        new ItemStack(Items.SUGAR, 1728)
                ),
                new SimpleInventory(
                        new ItemStack(Items.SUGAR, 1728)
                )
        ), List.of(10,20,30,40), 4);
    }

    @Override
    public String getName() {
        return "Speed Augmentation";
    }

    @Override
    public String getId() {
        return "speedAugmentation";
    }
}
