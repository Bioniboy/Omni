package com.bion.omni.omnimod.power.tech;

import com.bion.omni.omnimod.item.ModItems;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.List;

public class ItemCannonAugmentation extends Augmentation{
    private int durability = 100;
    public ItemCannonAugmentation(int level) {
        super(level, ModItems.ITEM_CANNON_AUGMENTATION);
        addUpgrade('\ue030', "\uf80a\uf809\uf802", List.of(
                new SimpleInventory(
                        new ItemStack(Items.SUGAR, 1728)
                ),
                new SimpleInventory(
                        new ItemStack(Items.SUGAR, 1728)
                )
        ), List.of(20,30), 2);

        addUpgrade('\ue031', "\uf80a\uf809\uf802", List.of(
                new SimpleInventory(
                        new ItemStack(Items.SUGAR, 1728)
                ),
                new SimpleInventory(
                        new ItemStack(Items.SUGAR, 1728)
                )
        ), List.of(10,20,30,40), 4);

        addUpgrade('\ue032', "\uf80a\uf808\uf807", List.of(
                new SimpleInventory(
                        new ItemStack(Items.SUGAR, 1728)
                ),
                new SimpleInventory(
                        new ItemStack(Items.SUGAR, 1728)
                )
        ), List.of(30,30,30,30), 4);

        addUpgrade('\ue033', "\uf80a\uf809\uf805\uf801", List.of(
                new SimpleInventory(
                        new ItemStack(Items.SUGAR, 1728)
                ),
                new SimpleInventory(
                        new ItemStack(Items.SUGAR, 1728)
                )
        ), List.of(35,35,35,35), 4);
    }
    public int getDurability(){
        return durability;
    }
    public void decrementDurability(){
        durability--;
    }
    public int getMaxDurability(){
        switch(getUpgrade(2).getLevel()){

            case 1:
                return 200;
            case 2:
                return 300;
            case 3:
                return 400;
            case 4:
                return 500;
            default:
                return 100;
        }
    }
    @Override
    public String getName() {
        return "Item Cannon Augmentation";
    }

    @Override
    public String getId() {
        return "itemCannonAugmentation";
    }
}
