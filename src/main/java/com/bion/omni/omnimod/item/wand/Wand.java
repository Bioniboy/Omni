package com.bion.omni.omnimod.item.wand;

import com.bion.omni.omnimod.element.Element;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import com.bion.omni.omnimod.util.Apprentice;
import com.bion.omni.omnimod.util.EntityDataInterface;
import com.bion.omni.omnimod.util.LeftClickItem;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class Wand extends SimplePolymerItem implements LeftClickItem {

    public Wand(Settings settings, Item polymerItem) {
        super(settings, polymerItem);
    }
    public abstract Element getElement();
    public abstract int getItemNumber();
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (((Apprentice)user).omni$getElement() != null && ((Apprentice) user).omni$getElement().getClass() == getElement().getClass()) {
            Integer wandPage = ((Apprentice)user).omni$getWandPage();
            if (!Objects.equals(((EntityDataInterface) user).getPersistentData().getString(wandPage + "ShiftRightClick"), "") && user.isSneaking()) {
                ((Apprentice)user).omni$interpretWandCommand(((EntityDataInterface)user).getPersistentData().getString(wandPage + "ShiftRightClick"));
            } else if (!Objects.equals(((EntityDataInterface) user).getPersistentData().getString(wandPage + "RightClick"), "")) {
                ((Apprentice)user).omni$interpretWandCommand(((EntityDataInterface)user).getPersistentData().getString(wandPage + "RightClick"));
            }
        } else {
            user.sendMessage(Text.literal("The wand won't respond...").formatted(getElement().getColor()));
        }
        return TypedActionResult.consume(user.getStackInHand(hand));

    }
    @Override
    public void click(PlayerEntity user) {
        if (((Apprentice)user).omni$getElement() != null && ((Apprentice) user).omni$getElement().getClass() == getElement().getClass()) {
            Integer wandPage = ((Apprentice) user).omni$getWandPage();
            if (!Objects.equals(((EntityDataInterface) user).getPersistentData().getString(wandPage + "ShiftLeftClick"), "") && user.isSneaking()) {
                ((Apprentice) user).omni$interpretWandCommand(((EntityDataInterface) user).getPersistentData().getString(wandPage + "ShiftLeftClick"));
            } else if (!Objects.equals(((EntityDataInterface) user).getPersistentData().getString(wandPage + "LeftClick"), "")) {
                ((Apprentice) user).omni$interpretWandCommand(((EntityDataInterface) user).getPersistentData().getString(wandPage + "LeftClick"));
            }
        } else {
            user.sendMessage(Text.literal("The wand won't respond...").formatted(getElement().getColor()));
        }
    }
    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipContext context, @Nullable ServerPlayerEntity player) {
        ItemStack out = PolymerItemUtils.createItemStack(itemStack, context, player);
        NbtCompound nbt = out.getNbt();
        assert nbt != null;
        nbt.putInt("CustomModelData", getItemNumber());
        out.setNbt(nbt);
        return out;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return false;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.CONSUME;
    }


}
