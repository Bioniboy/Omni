package com.bion.omni.omnimod.util;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class ActionBarManager {
    public ActionBarManager(ServerPlayerEntity p_player){
        player = p_player;
        //addIcon(DisplayIcon.iconName.GRID, 0, player);
//        addIcon(DisplayIcon.iconName.MANA_BAR_BASE,0,player);
//        addIcon(DisplayIcon.iconName.MANA_BAR, 0, player);
//        addIcon(DisplayIcon.iconName.MANA_VIGNETTE, 0, player);
//        addIcon(DisplayIcon.iconName.MANA_VALUE, 0, player);




    }
    public static void addIcon(DisplayIcon.iconName p_iconName, int p_fadeTime, ServerPlayerEntity player){
        icons.put(p_iconName, new DisplayIcon(p_iconName,p_fadeTime, player));
        iconNames.add(p_iconName);
    }
    private static void removeIcon(DisplayIcon.iconName p_iconName){
        icons.remove(p_iconName);
        iconNames.remove(p_iconName);
    }
    public static void displayActionBar(ServerPlayerEntity player){
        updateActionBar();
        Function<Text, Packet<?>> constructor = OverlayMessageS2CPacket::new;
        player.networkHandler.sendPacket(constructor.apply(actionBar));
    }
    private static void updateActionBar(){
        actionBar = Text.literal("");
        handleManaBar();
        ArrayList<DisplayIcon.iconName> killList = new ArrayList<>();

        if(iconNames.size() != 0){
            for (DisplayIcon.iconName icon : iconNames){
                DisplayIcon displayIcon = icons.get(icon);
                displayIcon.updateIcon();

                actionBar.append(displayIcon.getIconText());
                if(displayIcon.canDie() && displayIcon.hasFaded()){
                    killList.add(icon);
                }
            }
            for (DisplayIcon.iconName icon : killList){
                removeIcon(icon);
            }
        }
    }
    private static void handleManaBar(){
        if(((Apprentice)player).omni$getMana() >= ((Apprentice)player).omni$getManaMax() && (iconNames.contains(DisplayIcon.iconName.MANA_BAR))){
            removeIcon(DisplayIcon.iconName.MANA_BAR_BASE);
            removeIcon(DisplayIcon.iconName.MANA_BAR);
            removeIcon(DisplayIcon.iconName.MANA_VIGNETTE);
            removeIcon(DisplayIcon.iconName.MANA_VALUE);
            //removeIcon(DisplayIcon.iconName.GRID);

            addIcon(DisplayIcon.iconName.MANA_BAR_BASE, 5, player);
            addIcon(DisplayIcon.iconName.MANA_VIGNETTE, 5, player);
            addIcon(DisplayIcon.iconName.MANA_VALUE, 5, player);
        } else if (((Apprentice)player).omni$getMana() < ((Apprentice)player).omni$getManaMax() && !(iconNames.contains(DisplayIcon.iconName.MANA_BAR))) {
            addIcon(DisplayIcon.iconName.MANA_BAR_BASE, 0, player);
            addIcon(DisplayIcon.iconName.MANA_BAR, 0, player);
            addIcon(DisplayIcon.iconName.MANA_VIGNETTE, 0, player);
            addIcon(DisplayIcon.iconName.MANA_VALUE, 0, player);
            //addIcon(DisplayIcon.iconName.GRID, 0, player);
        }
    }
    private static  ServerPlayerEntity player;
    private static HashMap<DisplayIcon.iconName, DisplayIcon> icons = new HashMap<>();
    private static ArrayList<DisplayIcon.iconName>  iconNames = new ArrayList<>();
    private static MutableText actionBar = Text.literal("");


}
