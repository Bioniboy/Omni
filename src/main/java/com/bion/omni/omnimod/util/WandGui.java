package com.bion.omni.omnimod.util;

import eu.pb4.sgui.api.elements.BookElementBuilder;
import eu.pb4.sgui.api.gui.BookGui;
import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.item.tome.Tome;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.util.Arrays;
import java.util.Objects;

public class WandGui extends BookGui {
    private String command;
    public WandGui(ServerPlayerEntity player, String command, Formatting color, MutableText border) {
        super(player, genGui(((EntityDataInterface)player).getPersistentData(), color, border));
        this.command = command;
    }

    private static BookElementBuilder genGui(NbtCompound data, Formatting color, MutableText border) {
        MutableText page = Text.literal("").append(border);
        page.append(Text.literal("      Wand Slot\n     Assignment\n\n\n").formatted(Formatting.BOLD, color));
        page.append(Text.literal("Page 1\n\n").formatted(Formatting.UNDERLINE, color));
        page.append(getDot(data, "1RightClick"));
        page.append(getDot(data, "1LeftClick"));
        page.append(getDot(data, "1ShiftRightClick"));
        page.append(getDot(data, "1ShiftLeftClick"));
        page.append(Text.literal("\n\n\nPage 2\n\n").formatted(Formatting.UNDERLINE, color));
        page.append(getDot(data, "2LeftClick"));
        page.append(getDot(data, "2RightClick"));
        page.append(getDot(data, "2ShiftRightClick"));
        page.append(getDot(data, "2ShiftLeftClick"));
        return new BookElementBuilder().addPage(page);
    }

    public static MutableText getDot(NbtCompound data, String slot) {
        return Text.literal("    ")
                .append(Text.literal("⬤")
                .formatted(!data.contains(slot) ? Formatting.GRAY : Objects.equals(data.getString(slot), "") ? Formatting.BLACK : Formatting.DARK_GREEN)
                .styled(style -> style.withClickEvent(data.contains(slot) ? new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + slot) : new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "0")))
                .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        Text.literal("")
                                .append(Text.literal(slot.substring(1).replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2"))
                                        .formatted(Formatting.UNDERLINE)
                                )
                                .append(!data.contains(slot) ? Text.literal("\nLocked") : Objects.equals(data.getString(slot), "") ? Text.literal("\nUnassigned") : Text.literal("\nCurrently assigned to " + getNameFromKey(data.getString(slot)) ))
                ))));
    }

    public static String getNameFromKey(String key) {
        String[] keyList = key.split("\\.");
        String setting = switch (keyList[1]) {
            case "0":
                yield("Off");
            case "1":
                yield("Toggle");
            case "2":
                yield("Special");
            case "activate":
                if (keyList.length >= 4) {
                    yield(keyList[3].substring(0, 1).toUpperCase() + keyList[3].substring(1));
                } else {
                    yield("Activate");
                }
            default:
                yield(keyList[1].substring(0, 1).toUpperCase() + keyList[1].substring(1));
        };
        return keyList[0].substring(0, 1).toUpperCase() + keyList[0].substring(1).replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2") + " " + setting;
    }

    @Override
    public boolean onCommand(String command) {
        ((EntityDataInterface)player).getPersistentData().putString(command.substring(1), this.command.replace("/", ""));
        if (player.getMainHandStack().getItem() instanceof Tome tome) {
            tome.use(player.getWorld(), player, Hand.MAIN_HAND);
        } else if (player.getOffHandStack().getItem() instanceof Tome tome) {
            tome.use(player.getWorld(), player, Hand.OFF_HAND);
        }
        return true;
    }

    @Override
    public void onTakeBookButton() {
        command = "";
    }

    @Override
    public boolean open() {
        return super.open();
    }
}
