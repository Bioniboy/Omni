package com.bion.omni.omnimod.gui;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.item.tech.CraftAugmentation;
import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.power.air.AirWalk;
import com.bion.omni.omnimod.power.tech.*;
import com.bion.omni.omnimod.util.Apprentice;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;
import org.spongepowered.asm.mixin.Mutable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TinkerTableGui extends SimpleGui {
    public TinkerTableGui(ServerPlayerEntity player) {
        super(ScreenHandlerType.GENERIC_9X6, player, true);
        Apprentice apprentice = (Apprentice)player;
        apprentice.omni$addPower(new SpeedAugmentation(1));
        apprentice.omni$addPower(new AirWalk(1));
        apprentice.omni$addPower(new Wrench(1));
        apprentice.omni$addPower(new ItemCannonAugmentation(1));
        OmniMod.LOGGER.info("Test" + apprentice.omni$getAllPowers());
        int augmentationCounter = 0;
        for(Power power : apprentice.omni$getAllPowers()){
            if(power instanceof Wrench wrench){
                setSlot(54, new GuiElementBuilder(wrench.ITEM).setCallback((x,y,z)->{
                    clearDisplay();
                    setSlot(0, new GuiElementBuilder(wrench.ITEM).setCallback((a,b,c)->{
                        setSlot(0, new ItemStack(Items.AIR));
                        clearDisplay();
                    }));
                    DivineRepo divineRepo = (DivineRepo)((Apprentice)player).omni$getPowerById("divineRepo");
                    displayCraftingMenu(divineRepo.get(), apprentice.omni$getInfluence());
                }));
            }
            else if(power instanceof Augmentation augmentation){
                setSlot(55 + augmentationCounter, new GuiElementBuilder(augmentation.ITEM).setCallback((x,y,z)->{
                    clearDisplay();
                    setSlot(0, new GuiElementBuilder(augmentation.ITEM).setCallback((a,b,c)->{
                        setSlot(0, new ItemStack(Items.AIR));
                        clearDisplay();
                    }));
                    boolean repair = augmentation.getUpgradeCount() >= 3 && augmentation.getUpgrade(2).CHARACTER == '\ue032';
                    displaySettingsMenu(augmentation, repair);
                }));
                augmentationCounter++;
            }
        }
        setTitle(Text.literal(BACKGROUND).formatted(Formatting.WHITE));
    }


    private final String BACKGROUND = "\uF804\uf803\ue01b\uF80C\uf80a\uf808";
    private final String POWER = "                   \uf801\ue01e\uf80c\uf809\uf808\uf807";
    private final String UPGRADES = "\ue020\uf80a\uf809";
    private String getUpgradeButtonPlacement(int row, int column, int color){
        String upgradeColumn0Front = "               \uf801";
        String upgradeColumn0Back = "\uf80a\uf809\uf808\uf803";
        String upgradeColumn1Front = "                    \uf803";
        String upgradeColumn1Back = "\uf80b\uf808\uf805";
        String upgradeColumn2Front = "                        \uf801";
        String upgradeColumn2Back = "\uf80b\uf809\uf808\uf807";
        String upgradeColumn3Front = "                             \uf803";
        String upgradeColumn3Back = "\uf80b\uf80a\uf809\uf801";
        String upgradeColumn4Front = "                                 \uf801";
        String upgradeColumn4Back = "\uf80c\uf803";
        String upgradeColumn5Front = "                                      \uf803";
        String upgradeColumn5Back = "\uf80c\uf809\uf804\uf801";
        String upgradeGreyRow0 = "\ue024";
        String upgradeWhiteRow0 = "\ue028";
        String upgradeYellowRow0 = "\ue02c";
        String upgradeGreyRow1 = "\ue025";
        String upgradeWhiteRow1 = "\ue029";
        String upgradeYellowRow1 = "\ue02d";
        String upgradeGreyRow2 = "\ue026";
        String upgradeWhiteRow2 = "\ue02a";
        String upgradeYellowRow2 = "\ue02e";
        String upgradeGreyRow3 = "\ue027";
        String upgradeWhiteRow3 = "\ue02b";
        String upgradeYellowRow3 = "\ue02f";
        String negativeSpace = "\uf808\uf801";

        HashMap<Integer, Pair<String, String>> columns = new HashMap<>();
        columns.put(0,new Pair<>(upgradeColumn0Front, upgradeColumn0Back));
        columns.put(1,new Pair<>(upgradeColumn1Front, upgradeColumn1Back));
        columns.put(2,new Pair<>(upgradeColumn2Front, upgradeColumn2Back));
        columns.put(3,new Pair<>(upgradeColumn3Front, upgradeColumn3Back));
        columns.put(4,new Pair<>(upgradeColumn4Front, upgradeColumn4Back));
        columns.put(5,new Pair<>(upgradeColumn5Front, upgradeColumn5Back));

        HashMap<Integer, List<String>> rows = new HashMap<>();
        rows.put(0, List.of(upgradeGreyRow0, upgradeWhiteRow0, upgradeYellowRow0));
        rows.put(1, List.of(upgradeGreyRow1, upgradeWhiteRow1, upgradeYellowRow1));
        rows.put(2, List.of(upgradeGreyRow2, upgradeWhiteRow2, upgradeYellowRow2));
        rows.put(3, List.of(upgradeGreyRow3, upgradeWhiteRow3, upgradeYellowRow3));

        return columns.get(column).getLeft() + rows.get(row).get(color) + negativeSpace + columns.get(column).getRight();
    }
    private String getPowerLevelString(int level, int unlocked){

        String yellowLightning = "\ue023\uf805\uf802";
        String greyLightning = "\ue022\uf805\uf802";
        String blackLightning = "\ue021\uf805\uf802";
        String lightningSlot1Front = "                             \uf802";
        String lightningSlot1Back = "\uf80b\uf80a\uf809\uf802";
        String lightningSlot2Front = "                                  \uf803\uf801";
        String lightningSlot2Back = "\uf80c\uf804";
        String lightningSlot3Front = "                                      \uf802";
        String lightningSlot3Back = "\uf80c\uf809\uf806";

        ArrayList<String> slots = new ArrayList<>(List.of());

        for(int i = 1; i < 4; i++){
            if(i <= level){
                slots.add(yellowLightning);
            } else if (i <= unlocked) {
                slots.add(greyLightning);
            }else{
                slots.add(blackLightning);
            }
        }
        return lightningSlot1Front + slots.get(0) + lightningSlot1Back + lightningSlot2Front + slots.get(1) + lightningSlot2Back + lightningSlot3Front + slots.get(2) + lightningSlot3Back;
    }
    private void setSettingsButtons(Augmentation augmentation){
        setRepairButtons();
        setLightningButtons(augmentation);
        setUpgradeButtons(augmentation);
        }
    private void setRepairButtons(){
        for(int i = 1; i < 4; i++){
            setSlot(i,  new GuiElementBuilder(Items.AIR).setCallback((a,b,c)->{
                player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), Float.valueOf((float)0.3), 1);
            }));
        }
    }
    private void setLightningButtons(Augmentation augmentation){
        for(int i = 1; i <= augmentation.getUpgrade(0).getLevel() + 1; i++){
            if(i <= augmentation.getUpgrade(0).getLevel() + 1){
                final int[] powerSetTo = {i};
                int finalI = i;
                setSlot(i + 5,  new GuiElementBuilder(Items.AIR).setCallback((a, b, c)->{
                    player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), Float.valueOf((float)0.3), 1);
                    if(augmentation.getPowerLevel() == powerSetTo[0]){
                        powerSetTo[0] = finalI -1;
                    }else{
                        powerSetTo[0] = finalI;
                    }
                    augmentation.setPowerLevel(powerSetTo[0]);
                    displaySettingsMenu(augmentation, augmentation.getUpgradeCount() >= 3 && augmentation.getUpgrade(2).CHARACTER == '\ue032');
                }));
            }
        }
    }
    private void setUpgradeButtons(Augmentation augmentation){
        for(int i = 0; i < augmentation.getUpgradeCount();i++){
            int j = augmentation.getUpgrade(i).getLevel();
            if(j < augmentation.getUpgrade(i).MAX_LEVEL){
                int index = 9*(i + 2) + (j + 3);
                int cost = augmentation.getUpgrade(i).INFLUENCE_COSTS.get(j);
                String name = cost + " Influence";
                Formatting formatting;
                if(((Apprentice)player).omni$getInfluence() <= cost){
                    formatting = Formatting.DARK_RED;
                }else{
                    formatting = Formatting.WHITE;
                }
                int finalI = i;
                setSlot(index,  new GuiElementBuilder(Items.STICK).setName(Text.literal(name).formatted(formatting)).setCustomModelData(850014).setCallback((a, b, c)-> {
                    player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), Float.valueOf((float) 0.3), 1);
                    augmentation.getUpgrade(finalI).incrementLevel();
                    clearSlot(index);
                    displaySettingsMenu(augmentation, augmentation.getUpgradeCount() >= 3 && augmentation.getUpgrade(2).CHARACTER == '\ue032');
                }));
            }
        }
    }
    private void clearAllSlots(){
        for(int i = 0; i < 54; i++){
            clearSlot(i);
        }
    }
    private void clearDisplay(){
        setTitle(Text.literal(BACKGROUND).formatted(Formatting.WHITE));
        clearAllSlots();
    }
    private void displaySettingsMenu(Augmentation augmentation,  boolean repair){
        MutableText title = Text.literal(BACKGROUND).formatted(Formatting.WHITE);
        if(repair){
            title.append("       \ue01f\uf80B");
        }
        title.append(Text.literal(this.POWER));
        title.append(getPowerLevelString(augmentation.getPowerLevel(),1+augmentation.getUpgrade(0).getLevel()));
        title.append(UPGRADES);
        for(int i = 0; i<augmentation.getUpgradeCount(); i++){
            Upgrade upgrade = augmentation.getUpgrade(i);
            StringBuilder buttons = new StringBuilder();
            for(int j = 0; j<augmentation.getUpgrade(i).MAX_LEVEL; j++){
                int color;
                if(augmentation.getUpgrade(i).getLevel() > j){
                    color = 2;
                } else if (augmentation.getUpgrade(i).getLevel() == j) {
                    color = 1;
                }
                else{
                    color = 0;
                }
                buttons.append(getUpgradeButtonPlacement(i, j, color));
            }
            title.append(upgrade.CHARACTER + upgrade.NEGATIVE_SPACE + buttons);
        }
        setTitle(title);
        setSettingsButtons(augmentation);
    }
    private String addNegativeSpace(Pair<String, Integer> pair){
        String string = pair.getLeft();
        for(int i = 0; i <= pair.getRight(); i++){
            string += "\uf801";
        }
        return string;
    }
    private void displayCraftingMenu(SimpleInventory inventory, int influence){
        Pair<String, Integer> influenceNumber30 = new Pair<>("                            \uf801\ue034",122);
        Pair<String, Integer> influenceNumber50 = new Pair<>("                            \uf801\ue035", 122);
        Pair<String, Integer> battery1 = new Pair<>("               \uf801\ue037", 106);
        Pair<String, Integer> bionicLegsShowcase = new Pair<>("   \uf802\ue038", 42);
        Pair<String, Integer> bionicLegsTitle = new Pair<>("             \uf802\ue039", 109);
        Pair<String, Integer> copper288 = new Pair<>("               \uf801\ue03a", 116);
        Pair<String, Integer> gunpowder320 = new Pair<>("               \uf801\ue03d", 134);
        Pair<String, Integer> iron576 = new Pair<>("               \uf801\ue03f", 102);
        Pair<String, Integer> itemCannonShowcase = new Pair<>("   \uf801\ue040", 42);
        Pair<String, Integer> itemCannonTitle = new Pair<>("            \ue041", 111);
        Pair<String, Integer> leather192 = new Pair<>("               \uf801\ue043", 120);
        Pair<String, Integer> redstone320 = new Pair<>("               \uf801\ue044", 126);
        Pair<String, Integer> sugar1728 = new Pair<>("               \uf801\ue045", 116);
        Pair<String, Integer> item_centerer = new Pair<>(" \uf802\ue047", 50);
        Pair<String, Integer> text_locator = new Pair<>("\ue048", 123);

        CraftAugmentation itemCannon = new CraftAugmentation(List.of(
                new ItemStack(Items.IRON_INGOT, 576),
                new ItemStack(Items.COPPER_INGOT, 288),
                new ItemStack(Items.REDSTONE, 320),
                new ItemStack(Items.GUNPOWDER, 320)
        ),List.of(
                iron576,
                copper288,
                redstone320,
                gunpowder320
        ), List.of(
                itemCannonShowcase,
                itemCannonTitle,
                battery1
        ), 30, influenceNumber30);
        MutableText title = Text.literal(BACKGROUND).formatted(Formatting.WHITE);
        title.append(itemCannon.getIcons(inventory, influence));


        setTitle(title);
    }








}
