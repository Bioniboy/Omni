package com.bion.omni.omnimod.power.moon;

import com.bion.omni.omnimod.power.ImpulsePower;
import com.bion.omni.omnimod.power.Mana;
import com.bion.omni.omnimod.power.Power;
import com.bion.omni.omnimod.util.Apprentice;
import com.bion.omni.omnimod.util.ConfigSymbol;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class ShadowPortal extends ImpulsePower {
    public ShadowPortal(int level) {
        super(level);
    }

    @Override
    public String getAdvancementId() {
        return "shadow_portal" + (getLevel() == 1 ? "" : "_" + getLevel());
    }
    Vec3d markPosition = null;

    @Override
    public NbtCompound toNbt() {
        NbtCompound nbt = super.toNbt();
        if (markPosition != null) {
            var pos = new ArrayList<Long>();
            pos.add((long) markPosition.getX());
            pos.add((long) markPosition.getY());
            pos.add((long) markPosition.getZ());
            nbt.putLongArray("markPos", pos);
        }
        return nbt;
    }
    @Override
    public Power setNbt(NbtCompound nbt) {
        super.setNbt(nbt);
        if (nbt.contains("markPos", NbtElement.LONG_ARRAY_TYPE)) {
            var pos = nbt.getLongArray("markPos");
            markPosition = new Vec3d(pos[0], pos[1], pos[2]);
        }
        return this;
    }
    @Override
    public String getName() {
        return "Shadow Portal " + getRomanNumeral(this.getLevel());
    }

    @Override
    public String getId() {
        return "shadowPortal";
    }

    @Override
    public String getPreRequisiteId() {
        return "nightVision";
    }

    @Override
    public Boolean hasConfig() {
        return true;
    }

    @Override
    public Integer getMaxLevel() {
        return 3;
    }

    @Override
    public Integer getInfluenceCost() {
        return switch (this.getLevel()) {
            case 1:
                yield 50;
            case 2:
                yield 60;
            case 3:
                yield 80;
            default:
                yield 0;
        };
    }

    @Override
    public ArrayList<ConfigSymbol> getConfigSymbols() {
        MutableText markDescription = Text.literal("Place a ")
                .append(Text.literal("Shadow Mark")
                        .formatted(Formatting.DARK_BLUE)
                );
        MutableText recallDescription = Text.literal("Teleport to your ")
                .append(Text.literal("Shadow Mark")
                        .formatted(Formatting.DARK_BLUE)
                );
        ArrayList<ConfigSymbol> symbols = new ArrayList<>();
        symbols.add(new ConfigSymbol("⬤", markDescription, "activate.1.mark"));
        symbols.add(new ConfigSymbol("«", recallDescription, "activate.2.recall"));
        return symbols;
    }

    @Override
    public boolean activate(ServerPlayerEntity user) {
        if (user.getWorld().getBaseLightLevel(user.getBlockPos(), user.getWorld().getAmbientDarkness()) <= 4) {
            markPosition = user.getPos();
            user.sendMessage(Text.literal("Mark set").formatted(Formatting.DARK_BLUE));
            return true;
        } else {
            user.sendMessage(Text.literal("Not dark enough. Light level: " + user.getWorld().getBaseLightLevel(user.getBlockPos(), user.getWorld().getAmbientDarkness())).formatted(Formatting.DARK_BLUE));
            return false;
        }

    }

    @Override
    public void activate2(ServerPlayerEntity user) {
        if (markPosition != null) {
            int range = 0;
            float volume = 0;
            switch (getLevel()) {
                case 1:
                    range = 100;
                    volume = 1.0F;
                    break;
                case 2:
                    range = 200;
                    volume = 0.5F;
                    break;
                case 3:
                    range = 400;
                    volume = 0.2F;
            }
            int distance = (int)(( user.getPos().distanceTo(markPosition)));
            int manaCost = (distance / 10 + 1) * 3;
            if (((Apprentice)user).omni$getMana() >= manaCost) {
                if (distance <= range) {
                    user.requestTeleport(markPosition.getX(), markPosition.getY(), markPosition.getZ());
                    user.getWorld().playSound((Entity) null, user.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, volume, 1.0F);
                    user.getWorld().playSound((Entity) null, BlockPos.ofFloored(markPosition), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, volume, 1.0F);
                    markPosition = null;
                    ((Apprentice)user).omni$changeMana(-manaCost);
                    Mana.manaShow(user);
                } else {
                    user.sendMessage(Text.literal("Out of range: " + distance + " > " + range).formatted(Formatting.DARK_BLUE));
                }

            } else {
                user.sendMessage(Text.literal("Not enough mana").formatted(Formatting.DARK_BLUE));
            }
        } else {
            user.sendMessage(Text.literal("Mark not set").formatted(Formatting.DARK_BLUE));
        }
    }

    @Override
    public boolean isTicking() {
        return markPosition != null;
    }

    @Override
    public void tick(ServerPlayerEntity user) {
        user.networkHandler.sendPacket(new ParticleS2CPacket(ParticleTypes.SMOKE, true, markPosition.x, markPosition.y, markPosition.z, 0.2F, 0, 0.2F, 0, 1));
        if (user.getWorld().getBaseLightLevel(BlockPos.ofFloored(markPosition), user.getWorld().getAmbientDarkness()) > 4) {
            markPosition = null;
            user.sendMessage(Text.literal("Mark destroyed").formatted(Formatting.DARK_BLUE));
        }
    }
}
