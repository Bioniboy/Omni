package com.bion.omni.omnimod.entity.custom;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import eu.pb4.polymer.core.api.entity.PolymerEntity;
import eu.pb4.polymer.core.api.entity.PolymerEntityUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRemoveS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;

public class PlayerBody extends ArmorStandEntity implements PolymerEntity {

    ServerPlayerEntity player = null;

    public PlayerBody(EntityType<PlayerBody> entityType, World world) {
        super(entityType, world);
    }
    @Override
    public List<Pair<EquipmentSlot, ItemStack>> getPolymerVisibleEquipment(List<Pair<EquipmentSlot, ItemStack> > oldList, ServerPlayerEntity player) {
        List<Pair<EquipmentSlot, ItemStack>> list = Lists.newArrayListWithCapacity(1);
        list.add(Pair.of(EquipmentSlot.MAINHAND, Items.DIAMOND.getDefaultStack()));
        list.add(Pair.of(EquipmentSlot.FEET, Items.DIAMOND_BOOTS.getDefaultStack()));
        return list;
    }

//    @Override
//    public List<Pair<EquipmentSlot, ItemStack>> getPolymerVisibleEquipment(List<Pair<EquipmentSlot, ItemStack>> items, ServerPlayerEntity player) {
//        List<Pair<EquipmentSlot, ItemStack>> list = Lists.newArrayListWithCapacity(6);
//        list.add(Pair.of(EquipmentSlot.FEET, this.player.getEquippedStack(EquipmentSlot.FEET)));
//        list.add(Pair.of(EquipmentSlot.LEGS, this.player.getEquippedStack(EquipmentSlot.LEGS)));
//        list.add(Pair.of(EquipmentSlot.CHEST, this.player.getEquippedStack(EquipmentSlot.CHEST)));
//        list.add(Pair.of(EquipmentSlot.HEAD, this.player.getEquippedStack(EquipmentSlot.HEAD)));
//        list.add(Pair.of(EquipmentSlot.MAINHAND, this.player.getEquippedStack(EquipmentSlot.MAINHAND)));
//        list.add(Pair.of(EquipmentSlot.OFFHAND, this.player.getEquippedStack(EquipmentSlot.OFFHAND)));
//        return list;
//    }

    public void setPlayer(ServerPlayerEntity player) {
        this.player = player;
    }
    @Override
    public EntityType<?> getPolymerEntityType(ServerPlayerEntity player) {
        return EntityType.PLAYER;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return PolymerEntityUtils.createPlayerSpawnPacket(this);
    }

    @Override
    public void onBeforeSpawnPacket(Consumer<Packet<?>> packetConsumer) {
        var packet = PolymerEntityUtils.createMutablePlayerListPacket(EnumSet.of(PlayerListS2CPacket.Action.ADD_PLAYER, PlayerListS2CPacket.Action.UPDATE_LISTED));
        var gameprofile = player.getGameProfile();
        packet.getEntries().add(new PlayerListS2CPacket.Entry(this.getUuid(), gameprofile, false, 0, GameMode.ADVENTURE, null, null));
        packetConsumer.accept(packet);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        player.networkHandler.sendPacket(new PlayerRemoveS2CPacket(List.of(this.getUuid())));
        super.onStartedTrackingBy(player);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (player != null) {
            end();
            discard();
            return player.damage(source, amount);
        }
        return false;
    }
    public void end() {
        if (player != null) {
            player.teleport(getX(), getY(), getZ());
            player.setPitch(getPitch());
            setYaw(getYaw());
            player.changeGameMode(GameMode.SURVIVAL);
            discard();
        }
    }
    @Override
    public boolean isSilent() {
        return true;
    }

}
