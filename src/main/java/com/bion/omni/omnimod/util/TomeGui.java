package com.bion.omni.omnimod.util;

import com.bion.omni.omnimod.OmniMod;
import eu.pb4.sgui.api.elements.BookElementBuilder;
import eu.pb4.sgui.api.gui.BookGui;
import eu.pb4.sgui.virtual.book.BookScreenHandler;
import com.bion.omni.omnimod.item.tome.Tome;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Objects;

public class TomeGui extends BookGui {
    private boolean forceReopen = false;
    private boolean setWand = false;
    private String wandCommand = "";

    public TomeGui(ServerPlayerEntity player, BookElementBuilder book) {
        super(player, book);
    }

    @Override
    public void onClose() {
        super.onClose();
        Tome playerTome = null;
        if (player.getMainHandStack().getItem() instanceof Tome tome) {
            playerTome = tome;
        } else if (player.getOffHandStack().getItem() instanceof Tome tome) {
            playerTome = tome;
        }
        assert playerTome != null;
        if (forceReopen) {
            if (player.getMainHandStack().getItem() instanceof Tome tome) {
                tome.use(player.getWorld(), player, Hand.MAIN_HAND);
            } else {
                Tome tome = (Tome) player.getOffHandStack().getItem();
                tome.use(player.getWorld(), player, Hand.OFF_HAND);
            }
            this.forceReopen = false;
        }
        if (!Objects.equals(wandCommand, "")) {
            playerTome.openWandGui(player, wandCommand);

            setWand = false;
        }
    }
    @Override
    public void close(boolean screenHandlerIsClosed) {
        this.open = false;
        this.reOpen = false;

        if (!screenHandlerIsClosed && this.player.currentScreenHandler == this.screenHandler) {
            this.player.closeHandledScreen();
        }

        this.onClose();

    }
    @Override
    public void setPage(int page) {
        super.setPage(page);
        ((EntityDataInterface)player).getPersistentData().putInt("page", this.page);
        if (player.currentScreenHandler instanceof BookScreenHandler) {
            this.player.playSound(SoundEvent.of(new Identifier("minecraft:item.book.page_turn")), SoundCategory.PLAYERS, 0.3F, 1.0F);
        }

    }
    @Override
    public boolean onCommand(String command) {
        forceReopen = true;
        String[] components = command.substring(1).split("\\.");
        String[] shortComponents = Arrays.copyOfRange(components, 1, components.length);
        Tome tome;
        if (player.getMainHandStack().getItem() instanceof Tome mainTome) {
            tome = mainTome;
        } else if (player.getOffHandStack().getItem() instanceof Tome offTome) {
            tome = offTome;
        } else {
            tome = null;
        }
        assert tome != null;
        if (Objects.equals(components[0], "power")) {
            if (setWand) {

                wandCommand = String.join(".", shortComponents);
                return true;
            }
            if (Arrays.asList(shortComponents).contains("close") || (Objects.equals(shortComponents[1], "activate") && !Arrays.asList(shortComponents).contains("reopen"))) {
                forceReopen = false;
            }
            tome.powerCommand(shortComponents, player);
        } else if (Objects.equals(components[0], "arrow")) {
            tome.arrowCommand(shortComponents, player);
        } else if (Objects.equals(components[0], "buy")) {
            tome.buyCommand(shortComponents, player);
        } else if (Objects.equals(components[0], "wand")) {
            tome.wandSlotCommand(shortComponents, player);
        } else if (Objects.equals(components[0], "upgradeMax")) {
            tome.upgradeMaxMana(player);
        } else if (Objects.equals(components[0], "upgradeRegen")) {
            tome.upgradeManaRegen(player);
        }

        return true;
    }
    @Override
    public void onTakeBookButton() {
        setWand = true;
    }
}
