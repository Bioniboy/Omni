package com.bion.omni.omnimod.item.tech;

import com.bion.omni.omnimod.OmniMod;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;

import java.util.List;

public class CraftAugmentation {
    private List<ItemStack> resources;
    private List<Pair<String, Integer>> icons;
    private  List<Pair<String, Integer>> resourceIcons;
    private int influence;
    private Pair<String, Integer> craftButton = new Pair<>("               \ue03b", 100);
    private Pair<String, Integer> craftUnavailable = new Pair<>("                \uf804\ue03c", 100);
    private Pair<String, Integer> influenceLabel = new Pair<>("               \uf801\ue03e", 109);
    private Pair<String, Integer> itemShowcaseFrame = new Pair<>("\uf801\ue042", 53);
    private Pair<String, Integer> arrowButtons = new Pair<>("           \uf801\ue036", 117);
    private List<Pair<String,Integer>> baseList = List.of(itemShowcaseFrame, arrowButtons);
    private Pair<String,Integer> influenceRequired;

    public CraftAugmentation(List<ItemStack> resources, List<Pair<String, Integer>> resourceIcons, List<Pair<String, Integer>> icons, int influence, Pair<String, Integer> influenceRequired) {
        this.icons = icons;
        this.resourceIcons = resourceIcons;
        this.resources = resources;
        this.influence = influence;
        this.influenceRequired = influenceRequired;
    }

    public List<ItemStack> getResources() {
        return resources;
    }

    public Text getIcons(SimpleInventory inventory, int influence) {

        MutableText text = Text.literal("");
        text.append(Text.literal(getBaseIcons()).formatted(Formatting.WHITE));
        boolean canCraft = true;
        for (int i = 0; i < resources.size(); i++) {
            Formatting formatting = Formatting.WHITE;
            Pair<String,Integer> icon = resourceIcons.get(i);
            if(inventory.count(resources.get(i).getItem()) < resources.get(i).getCount()){
                formatting = Formatting.GRAY;
                canCraft = false;
            }
            OmniMod.LOGGER.info("" + inventory.count(resources.get(i).getItem()) + ", " + resources.get(i).getCount());
            text.append(Text.literal(addNegativeSpace(icon)).formatted(formatting));
        }
        Formatting formatting2 = Formatting.WHITE;
        if(influence < this.influence){
            formatting2 = Formatting.GRAY;
        }
        text.append(Text.literal(addNegativeSpace(influenceLabel) + addNegativeSpace(influenceRequired)).formatted(formatting2));
        if(canCraft){
            text.append(Text.literal(addNegativeSpace(craftButton)).formatted(Formatting.WHITE));
        }else{
            text.append(Text.literal(addNegativeSpace(craftUnavailable)).formatted(Formatting.WHITE));
        }
        for (Pair<String, Integer> pair:icons){
            text.append(Text.literal(addNegativeSpace(pair)).formatted(Formatting.WHITE));
        }
        return text;
    }

    private String addNegativeSpace(Pair<String, Integer> pair) {
        String string = pair.getLeft();
        for (int i = 0; i <= pair.getRight(); i++) {
            string += "\uf801";
        }
        return string;
    }
    private String getBaseIcons(){
        String string = "";
        for (Pair pair : baseList) {
            string += addNegativeSpace(pair);
        }
        return string;
    }
}
