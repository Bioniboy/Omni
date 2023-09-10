package com.bion.omni.omnimod.entity.custom;

import com.bion.omni.omnimod.mixin.accessor.PlayerEntityAccessor;
import com.bion.omni.omnimod.util.Apprentice;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import eu.pb4.polymer.core.api.entity.PolymerEntity;
import eu.pb4.polymer.core.api.entity.PolymerEntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRemoveS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
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

//    @Override
//    protected void initGoals() {
//
//    }

    @Override
    public List<Pair<EquipmentSlot, ItemStack>> getPolymerVisibleEquipment(List<Pair<EquipmentSlot, ItemStack>> items, ServerPlayerEntity player) {
        List<Pair<EquipmentSlot, ItemStack>> list = Lists.newArrayListWithCapacity(6);
        list.add(Pair.of(EquipmentSlot.FEET, this.player.getEquippedStack(EquipmentSlot.FEET)));
        list.add(Pair.of(EquipmentSlot.LEGS, this.player.getEquippedStack(EquipmentSlot.LEGS)));
        list.add(Pair.of(EquipmentSlot.CHEST, this.player.getEquippedStack(EquipmentSlot.CHEST)));
        list.add(Pair.of(EquipmentSlot.HEAD, this.player.getEquippedStack(EquipmentSlot.HEAD)));
        list.add(Pair.of(EquipmentSlot.MAINHAND, this.player.getEquippedStack(EquipmentSlot.MAINHAND)));
        list.add(Pair.of(EquipmentSlot.OFFHAND, this.player.getEquippedStack(EquipmentSlot.OFFHAND)));
        return list;
    }

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
    public void modifyRawTrackedData(List<DataTracker.SerializedEntry<?>> data, ServerPlayerEntity player, boolean initial) {
        data.add(new DataTracker.Entry<>(PlayerEntityAccessor.getPLAYER_MODEL_PARTS(), this.player.getDataTracker().get(PlayerEntityAccessor.getPLAYER_MODEL_PARTS())).toSerialized());

    }
    @Override
    public void onBeforeSpawnPacket(ServerPlayerEntity player, Consumer<Packet<?>> packetConsumer) {
        if (this.player == null) {
            this.player = player;
        }
        var packet = PolymerEntityUtils.createMutablePlayerListPacket(EnumSet.of(PlayerListS2CPacket.Action.ADD_PLAYER, PlayerListS2CPacket.Action.UPDATE_LISTED));
        var gameprofile = this.player.getGameProfile();
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
        if (player != null && !isRemoved()) {
            ((Apprentice)player).omni$setConfig("astralProject", 0);
            player.requestTeleport(getX(), getY(), getZ());
            player.setPitch(getPitch());
            setYaw(getYaw());
            Box entityBox = new Box(getBlockPos()).expand(32);
            for (Entity entity : getWorld().getOtherEntities(this, entityBox)) {
                if (entity instanceof HostileEntity mob && mob.getTarget() == this) {
                    mob.setTarget(player);
                }
            }
            player.changeGameMode(GameMode.SURVIVAL);
            discard();
        }
    }
    @Override
    public boolean isSilent() {
        return true;
    }

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        return ActionResult.SUCCESS;
    }
}
