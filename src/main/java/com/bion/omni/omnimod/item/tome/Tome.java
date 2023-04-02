package com.bion.omni.omnimod.item.tome;

import com.bion.omni.omnimod.OmniMod;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import eu.pb4.sgui.api.elements.BookElementBuilder;
import com.bion.omni.omnimod.elements.Element;
import com.bion.omni.omnimod.powers.ChangeWandPage;
import com.bion.omni.omnimod.powers.ContinuousPower;
import com.bion.omni.omnimod.powers.ImpulsePower;
import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.util.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Objects;

import static com.bion.omni.omnimod.util.WandGui.getNameFromKey;

public abstract class Tome extends SimplePolymerItem {
    public abstract Element getElement();
    public abstract String getTitleIndent();
    public abstract String getTitle();
    public abstract char getBorder1();
    public abstract char getBorder2();
    public Tome(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (((Apprentice) user).getElement() != null && Objects.equals(((Apprentice) user).getElement().getName(), getElement().getName())) {
            TomeGui gui = new TomeGui((ServerPlayerEntity) user, genTome(user));
            if (((EntityDataInterface) user).getPersistentData().contains("page")) {
                gui.setPage(((EntityDataInterface) user).getPersistentData().getInt("page"));
            }
            gui.open();
        } else {
            user.sendMessage(Text.literal("The tome won't open...").formatted(getElement().getColor()));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    public BookElementBuilder genTome(PlayerEntity user) {
        BookElementBuilder book = new BookElementBuilder();
        book.addPage(
            getTitlePage()
        );
        MutableText configPage = Text.literal("").append(getBorder());
        configPage.append(Text.literal("    Power Usage").formatted(Formatting.BOLD, getElement().getColor()));
        int powerCount = 1;
        for (PowerConfig entry : ((Apprentice)user).getConfig()) {
            Power power = ((Apprentice)user).getPowerById(entry.getId());
            configPage.append(getPowerText(power, (ServerPlayerEntity) user));
            if (powerCount < 4) {
                powerCount++;
            } else {
                powerCount = 1;
                book.addPage(configPage);
                configPage = Text.literal("").append(getBorder()).append(Text.literal("    Power Usage").formatted(Formatting.BOLD, getElement().getColor()));
            }
        }
        if (powerCount > 1) {
            book.addPage(configPage);
        }
        MutableText buyPowersPage = Text.literal("").append(getBorder());
        buyPowersPage.append(Text.literal("    Buy Powers\n\n").formatted(Formatting.BOLD, getElement().getColor()));
        for (String id : getElement().getPowerIds()) {
            Power power = getElement().getPower(id);
            if ((((Apprentice)user).getPowerById(id) == null && (Objects.equals(power.getPreRequisiteId(), null) || ((Apprentice)user).getPowerById(power.getPreRequisiteId()) != null))
            || ((Apprentice)user).getPowerById(id) != null && power.getMaxLevel() > ((Apprentice)user).getPowerById(id).getLevel()) {
                if (((Apprentice)user).getPowerById(id) != null) {
                    power.setLevel(((Apprentice)user).getPowerById(id).getLevel() + 1);
                }
                buyPowersPage.append(getPowerBuyDot(power, (Apprentice)user));
                buyPowersPage.append(Text.literal(power.getName() + "\n").formatted(getElement().getColor()));
            }
        }
        book.addPage(buyPowersPage);
        MutableText WandSlotPage = Text.literal("").append(getBorder())
                .append(Text.literal("     Wand Slots\n\n").formatted(Formatting.BOLD, getElement().getColor()))
                .append(Text.literal("Page 1\n\n").formatted(Formatting.UNDERLINE, getElement().getColor()))
                .append(getWandSlotDot(user, "1RightClick"))
                .append(getWandSlotDot(user, "1LeftClick"))
                .append(getWandSlotDot(user, "1ShiftRightClick"))
                .append(getWandSlotDot(user, "1ShiftLeftClick"))
                .append(Text.literal("\n\n\nPage 2\n\n").formatted(Formatting.UNDERLINE, getElement().getColor()))
                .append(getWandSlotDot(user, "2RightClick"))
                .append(getWandSlotDot(user, "2LeftClick"))
                .append(getWandSlotDot(user, "2ShiftRightClick"))
                .append(getWandSlotDot(user, "2ShiftLeftClick"));
        book.addPage(WandSlotPage);
        String regenText = switch(((Apprentice)user).getManaRegenLevel()) {
            case 1:
                yield "1 per 5 seconds";
            case 2:
                yield "1 per 4 seconds";
            case 3:
                yield "1 per 2 seconds";
            case 4:
                yield "1 per second";
            case 5:
                yield "2 per second";
            case 6:
                yield "3 per second";
            default:
                yield "";
        };
        MutableText manaPage = Text.literal("").append(getBorder())
                .append(Text.literal("       Mana\n\n").formatted(Formatting.BOLD, getElement().getColor()))
                .append(Text.literal("Mana Max\n").formatted(getElement().getColor()))
                .append(((Apprentice) user).getManaMax() + "\n")
                .append(getManaMaxDot((Apprentice)user, 1))
                .append(getManaMaxDot((Apprentice)user, 2))
                .append(getManaMaxDot((Apprentice)user, 3))
                .append(getManaMaxDot((Apprentice)user, 4))
                .append(getManaMaxDot((Apprentice)user, 5))
                .append(Text.literal("\n\n\nMana Regen\n").formatted(getElement().getColor()))
                .append(regenText + "\n")
                .append(getManaRegenDot((Apprentice)user, 1))
                .append(getManaRegenDot((Apprentice)user, 2))
                .append(getManaRegenDot((Apprentice)user, 3))
                .append(getManaRegenDot((Apprentice)user, 4))
                .append(getManaRegenDot((Apprentice)user, 5));
        book.addPage(manaPage);

        return book;
    }
    private MutableText getBorder() {
        return Text.literal(getBorder1() + "\uF80C\uF809\uF803" + getBorder2() + "\uF801").formatted(Formatting.WHITE);
    }
    private MutableText getTitlePage() {
        return Text.literal("").append(getBorder())
                .append(Text.literal("     Welcome to the\n"))
                .append(Text.literal(getTitleIndent() + getTitle() + "\n\n").formatted(Formatting.BOLD, getElement().getColor()))
                .append(Text.literal("" +
                        "This book will be your " +
                        "tool to control your " +
                        "powers as a " + getElement().getName() + " " +
                        "apprentice as well as " +
                        "facilitate the growth " +
                        "of your mana as a " +
                        "whole. As you get " +
                        "stronger, the book " +
                        "will grow with you."));
    }
    private MutableText getPowerText(Power power, ServerPlayerEntity user) {
        Integer config = ((Apprentice) user).getConfigValue(power.getId());
        MutableText text = (Text.literal("\n\n" + power.getName() + "\n").formatted(getElement().getColor()));
        if (power instanceof ContinuousPower) {
            int configCounter = 0;
            for (ConfigSymbol symbol : power.getConfigSymbols()) {
                switch (symbol.getSymbol()) {
                    case "✔" -> text.append(getCheck(power, config == 1));
                    case "✖" -> text.append(getX(power, config == 0));
                    default -> text.append(getSpecialSymbol(power, symbol, config == configCounter));
                }
                text.append(" ");
                configCounter += 1;
            }
            text.append(getArrows(power.getId()));

        } else if (power instanceof ImpulsePower) {
            for (ConfigSymbol symbol : power.getConfigSymbols()) {
                if (Objects.equals(symbol.getSymbol(), "⬤")) {
                        if (symbol.getDescription() == null) {
                            text.append(getActivate(power));
                        } else {
                            text.append(getSpecialSymbol(power, symbol, false));
                        }
                } else {
                    text.append(getSpecialSymbol(power, symbol, false));
                }
                text.append(" ");
            }
            text.append(getArrows(power.getId()));
        }
        return text;
    }
    public void powerCommand(String[] components, ServerPlayerEntity user) {
        ((Apprentice)user).interpretWandCommand(components);
        user.playSound(SoundEvent.of(new Identifier("minecraft:ui.button.click")), SoundCategory.PLAYERS, 0.3F, 1.0F);
    }
    public void arrowCommand(String[] components, ServerPlayerEntity user) {
        if (Objects.equals(components[1], "up")) {
            ((Apprentice)user).reorderConfig(components[0], -1);
        } else {
            ((Apprentice)user).reorderConfig(components[0], 1);
        }
        user.playSound(SoundEvent.of(new Identifier("minecraft:ui.button.click")), SoundCategory.PLAYERS, 0.3F, 1.0F);
    }
    public void buyCommand(String[] components, ServerPlayerEntity user) {
        ((Apprentice)user).buyPower(components[0], Integer.parseInt(components[1]));
    }
    public void wandSlotCommand(String[] components, ServerPlayerEntity user) {
        if (components[0].startsWith("2") && ((Apprentice)user).getPowerById("changeWandPage") == null) {
            ((Apprentice)user).addPower(new ChangeWandPage());
        }
        ((Apprentice)user).changeInfluence(-getWandSlotCost(((EntityDataInterface)user).getPersistentData()));
        ((EntityDataInterface)user).getPersistentData().putString(components[0], "");
    }
    public void upgradeMaxMana(ServerPlayerEntity player) {
        ((Apprentice)player).changeInfluence(-((Apprentice) player).getManaMaxLevel() * 30);
        ((Apprentice)player).upgradeManaMax();
    }

    public void upgradeManaRegen(ServerPlayerEntity player) {
        ((Apprentice)player).changeInfluence(-20 - ((Apprentice) player).getManaRegenLevel() * 40);
        ((Apprentice)player).upgradeManaRegen();
    }

    private MutableText getArrows(String key) {
        return (Text.literal(""))
                .append(Text.literal("⬆")
                        .formatted(Formatting.BLACK)
                        .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/arrow." + key + ".up")))
                )
                .append(Text.literal(" "))
                .append(Text.literal("⬇")
                        .formatted(Formatting.BLACK)
                        .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/arrow." + key + ".down")))
                );
    }

    private MutableText getCheck(Power power, Boolean underlined) {
        return Text.literal("✔")
                .formatted(Formatting.DARK_GREEN, (underlined ? Formatting.UNDERLINE : Formatting.DARK_GREEN))
                .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/power." + power.getId() + ".1")))
                .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        Text.literal("Turns ")
                                .append(Text.literal("on ")
                                        .formatted(Formatting.DARK_GREEN)
                                )
                                .append(Text.literal(power.getName())
                                        .formatted(getElement().getColor())
                                )
                )));
    }

    private MutableText getX(Power power, Boolean underlined) {
        return Text.literal("✖")
                .formatted(Formatting.DARK_RED, (underlined ? Formatting.UNDERLINE : Formatting.DARK_RED))
                .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/power." + power.getId() + ".0")))
                .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        Text.literal("Turns ")
                                .append(Text.literal("off ")
                                        .formatted(Formatting.DARK_RED)
                                )
                                .append(Text.literal(power.getName())
                                        .formatted(getElement().getColor())
                                )
                )));
    }

    private MutableText getSpecialSymbol(Power power, ConfigSymbol symbol, Boolean underlined) {
        return Text.literal(symbol.getSymbol())
                .formatted(getElement().getColor(), (underlined ? Formatting.UNDERLINE : getElement().getColor()))
                .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/power." + power.getId() + "." + symbol.getCommand())))
                .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        symbol.getDescription()
                ))
        );
    }

    private MutableText getActivate(Power power) {
        return Text.literal("⬤")
                .formatted(getElement().getColor())
                .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/power." + power.getId() + ".activate")))
                .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        Text.literal("Activate ")
                                .append(Text.literal(power.getName())
                                        .formatted(getElement().getColor())
                                )
                )));
    }
    private MutableText getPowerBuyDot(Power power, Apprentice player) {
        MutableText dot = Text.literal("⬤ ")
                .formatted(getElement().getColor())
                .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        Text.literal("")
                                .append((player.getInfluence() >= power.getInfluenceCost() ? Text.literal("Buy ").append(Text.literal(power.getName() + "\n").formatted(getElement().getColor())) : Text.literal("")))
                                .append(power.getInfluenceCost() + " Influence")
                                .formatted(player.getInfluence() >= power.getInfluenceCost() ? Formatting.WHITE : Formatting.DARK_RED)
                )));
        if (player.getInfluence() >= power.getInfluenceCost()) {
            dot.styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/buy." + power.getId() + "." + power.getLevel())));
        }
        return dot;
    }
    private Integer getWandSlotCost(NbtCompound data) {
        int slotCount = 0;
        if (data.contains("1LeftClick")) {
            slotCount += 1;
        }
        if (data.contains("1ShiftRightClick")) {
            slotCount += 1;
        }
        if (data.contains("1ShiftLeftClick")) {
            slotCount += 1;
        }
        if (data.contains("2RightClick")) {
            slotCount += 1;
        }
        if (data.contains("2LeftClick")) {
            slotCount += 1;
        }
        if (data.contains("2ShiftRightClick")) {
            slotCount += 1;
        }
        if (data.contains("2ShiftLeftClick")) {
            slotCount += 1;
        }
        return 30 + slotCount * 10;
    }
    private MutableText getWandSlotDot(PlayerEntity player, String slot) {
        NbtCompound data = ((EntityDataInterface)player).getPersistentData();
        Integer influence = ((Apprentice)player).getInfluence();
        return Text.literal("    ")
                .append(Text.literal("⬤")
                        .formatted(!data.contains(slot) ? Formatting.GRAY : Objects.equals(data.getString(slot), "") ? Formatting.BLACK : Formatting.DARK_GREEN)
                        .styled(style -> style.withClickEvent(!data.contains(slot) && influence >= getWandSlotCost(data) ? new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wand." + slot) : new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "0")))
                        .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                (data.contains(slot) ?
                                (
                                (Text.literal("")
                                        .append(Text.literal(slot.substring(1).replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2"))
                                                .formatted(Formatting.UNDERLINE)
                                        )
                                        .append(!data.contains(slot) ? Text.literal("\nLocked") : Objects.equals(data.getString(slot), "") ? Text.literal("\nUnassigned") : Text.literal("\nCurrently assigned to " + getNameFromKey(data.getString(slot)) ))

                                )) :
                                        (Text.literal("")
                                                .append(Text.literal("Buy " + slot.substring(1).replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2") + " Slot\n")
                                                        .formatted(Formatting.UNDERLINE)
                                                )
                                                .append(Text.literal(getWandSlotCost(data) + " Influence")
                                                        .formatted(influence >= getWandSlotCost(data) ? Formatting.WHITE : Formatting.DARK_RED)
                                                )
                                        )
                                )))));
    }
    private MutableText getManaMaxDot(Apprentice player, int level) {
        MutableText dot = Text.literal("")
                .append(Text.literal("⬤")
                        .formatted(level < player.getManaMaxLevel() ? Formatting.DARK_GREEN : level > player.getManaMaxLevel() ? Formatting.GRAY : Formatting.BLACK)
                        .styled(style -> style.withClickEvent(player.getManaMaxLevel() == level && player.getInfluence() >= level * 30 ? new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/upgradeMax") : new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "0")))
                );
        if (player.getManaMaxLevel() <= level) {
            dot.styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    level > player.getManaMaxLevel() ?
                            Text.literal((level + 1) * 60 + " Max ").formatted(Formatting.GRAY)
                                    .append(Text.literal("Mana\n")
                                            .formatted(getElement().getColor())
                                    )
                                    .append(level * 30 + " Influence")
                            :
                            Text.literal("Buy Upgrade: ")
                                    .append((level + 1) * 60 + " Max ")
                                    .append(Text.literal("Mana\n")
                                            .formatted(getElement().getColor())
                                    )
                                    .append(Text.literal(level * 30 + " Influence")
                                            .formatted(player.getInfluence() >= level * 30 ? Formatting.WHITE : Formatting.DARK_RED)
                                    )
                    )
            ));
        }
        dot.append(" ");
        return dot;
    }

    private MutableText getManaRegenDot(Apprentice player, int level) {
        int regen = (level + 1) <= 4 ? 1 : (level + 1) - 3;
        int regenDelay = (level + 1) <= 3 ? switch (level) {
            case 0:
                yield 5;
            case 1:
                yield 4;
            case 2:
                yield 2;
            default:
                yield 0;
        } : 1;
        MutableText dot = Text.literal("")
                .append(Text.literal("⬤")
                        .formatted(level < player.getManaRegenLevel() ? Formatting.DARK_GREEN : level > player.getManaRegenLevel() ? Formatting.GRAY : Formatting.BLACK)
                        .styled(style -> style.withClickEvent(player.getManaRegenLevel() == level && player.getInfluence() >= 20 + level * 40 ? new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/upgradeRegen") : new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "0")))
                );
        if (player.getManaRegenLevel() <= level) {
            dot.styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            level > player.getManaRegenLevel() ?
                                    Text.literal(regen + " ").formatted(Formatting.GRAY)
                                            .append(Text.literal("Mana")
                                                    .formatted(getElement().getColor())
                                            )
                                            .append(Text.literal(" per " + (regenDelay > 1 ? regenDelay + " seconds" : "second")))
                                            .append("\n" + (20 + level * 40) + " Influence")
                                    :
                                    Text.literal("Buy Upgrade: ")
                                            .append(regen + " ")
                                            .append(Text.literal("Mana")
                                                    .formatted(getElement().getColor())
                                            )
                                            .append(Text.literal(" per " + (regenDelay > 1 ? regenDelay + " seconds" : "second") + "\n"))
                                            .append(Text.literal((20 + level * 40) + " Influence")
                                                    .formatted(player.getInfluence() >= (20 + level * 40) ? Formatting.WHITE : Formatting.DARK_RED)
                                            )
                    )
            ));
        }
        dot.append(" ");
        return dot;
    }

    public void openWandGui(ServerPlayerEntity player, String wandCommand) {
        WandGui gui = new WandGui(player, wandCommand, getElement().getColor(), getBorder());
        gui.open();
    }
}

