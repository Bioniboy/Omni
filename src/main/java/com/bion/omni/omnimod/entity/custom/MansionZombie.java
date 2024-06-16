package com.bion.omni.omnimod.entity.custom;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.command.OmniCommand;
import com.bion.omni.omnimod.util.Apprentice;
import com.mojang.datafixers.util.Pair;
import eu.pb4.polymer.core.api.entity.PolymerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class MansionZombie extends ZombieEntity implements PolymerEntity {
    public MansionZombie(EntityType<? extends MansionZombie> entityType, World world) {
        super(entityType, world);

    }
    Vec3d savedPos = null;
    int idleTimer = 0;
    List<BlockPos> tpPositions = List.of(
            new BlockPos(-492, 122, 244),
            new BlockPos(-514, 122, 246),
            new BlockPos(-531, 122, 231),
            new BlockPos(-519, 115, 233),
            new BlockPos(-496, 115, 243),
            new BlockPos(-529, 115, 221)
    );

    @Override
    public EntityType<?> getPolymerEntityType(ServerPlayerEntity player) {
        return EntityType.ZOMBIE;
    }

    @Override
    public List<Pair<EquipmentSlot, ItemStack>> getPolymerVisibleEquipment(List<Pair<EquipmentSlot, ItemStack>> items, ServerPlayerEntity player) {
        items.add(Pair.of(EquipmentSlot.HEAD, Items.CARVED_PUMPKIN.getDefaultStack()));
        return items;
    }

    @Override
    public void onAttacking(Entity target) {
        super.onAttacking(target);
        if (target instanceof Apprentice apprentice && apprentice.omni$getInMansion()) {
            target.requestTeleport(-525, 112, 258);
//            OmniCommand.mansionStop((ServerPlayerEntity) apprentice);
            ((ServerPlayerEntity)apprentice).networkHandler.sendPacket(new TitleS2CPacket(Text.literal("You've been evicted").formatted(Formatting.DARK_RED).formatted(Formatting.BOLD)));

        }
    }

    @Override
    public void tick() {
        setInvulnerable(true);
        super.tick();
        if (!hasStatusEffect(StatusEffects.SLOWNESS)) {
            addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, -1, 1));
        }
        if (getTarget() == null || !(getTarget() instanceof Apprentice apprentice) || !apprentice.omni$getInMansion()) {
            Box entityBox = new Box(getBlockPos()).expand(100);
            List<Entity> entityList = getWorld().getOtherEntities(this, entityBox);
            for (Entity entity : entityList) {
                if (entity instanceof Apprentice apprentice && apprentice.omni$getInMansion()) {
                    setTarget((LivingEntity) entity);
                    return;
                }
            }
            setTarget(null);
        }
        idleTimer++;
        if (savedPos == null || !getPos().isInRange(savedPos, 3)) {
            idleTimer = 0;
            savedPos = getPos();
        }
        if (idleTimer >= 200 && getTarget() != null && !getTarget().getPos().isInRange(getPos(), 5)) {
            BlockPos tpPos;
            do {
                tpPos = tpPositions.get(getRandom().nextInt(tpPositions.size()));
            } while (Math.abs(tpPos.getY() - getTarget().getY()) <= 3 && tpPos.isWithinDistance(getTarget().getBlockPos(), 10));
            requestTeleport(tpPos.getX(), tpPos.getY(), tpPos.getZ());
            idleTimer = 0;
            savedPos = null;
        }
    }
}
