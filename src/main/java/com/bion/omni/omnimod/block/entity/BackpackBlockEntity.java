//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bion.omni.omnimod.block.entity;

import com.bion.omni.omnimod.OmniMod;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.component.Component;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ContainerLock;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class BackpackBlockEntity extends LootableContainerBlockEntity {
    private DefaultedList<ItemStack> inventory;
    private final ViewerCountManager stateManager;
    private DyedColorComponent dyedColor = new DyedColorComponent(DyedColorComponent.DEFAULT_COLOR, true);
    private int level = 1;

    public BackpackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BACKPACK, pos, state);
        OmniMod.LOGGER.info("size: " + (9*level));
        this.inventory = DefaultedList.ofSize(9*level, ItemStack.EMPTY);
        this.stateManager = new ViewerCountManager() {
            protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
                BackpackBlockEntity.this.playSound(state, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER.value());
                BackpackBlockEntity.this.setOpen(state, true);
            }

            protected void onContainerClose(World world, BlockPos pos, BlockState state) {
                BackpackBlockEntity.this.playSound(state, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER.value());
                BackpackBlockEntity.this.setOpen(state, false);
            }

            protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
            }

            protected boolean isPlayerViewing(PlayerEntity player) {
                if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                    Inventory inventory = ((GenericContainerScreenHandler)player.currentScreenHandler).getInventory();
                    return inventory == BackpackBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        if (!this.writeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory, registryLookup);
        }
        nbt.putInt("Color", dyedColor.rgb());
        nbt.putInt("backpack_level", level);
    }

    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        if(nbt.contains("backpack_level")){
            level = nbt.getInt("backpack_level");
        }
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.readLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory, registryLookup);
        }
        if(nbt.contains("Color")){
            dyedColor = new DyedColorComponent(nbt.getInt("Color"), true);
        }
    }

    public int size() {return 9*level;}

    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    protected Text getContainerName() {
        return switch (level){
            case 2 -> Text.translatable("container.small_backpack");
            case 3 -> Text.translatable("container.medium_backpack");
            case 4 -> Text.translatable("container.large_backpack");
            case 5 -> Text.translatable("container.massive_backpack");
            case 6 -> Text.translatable("container.giant_backpack");
            default -> Text.translatable("container.tiny_backpack");
        };
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return switch (level) {
            case 2 -> GenericContainerScreenHandler.createGeneric9x2(syncId, playerInventory);
            case 3 -> GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory);
            case 4 -> GenericContainerScreenHandler.createGeneric9x4(syncId, playerInventory);
            case 5 -> GenericContainerScreenHandler.createGeneric9x5(syncId, playerInventory);
            case 6 -> GenericContainerScreenHandler.createGeneric9x6(syncId, playerInventory);
            default -> GenericContainerScreenHandler.createGeneric9x1(syncId, playerInventory);
        };
    }

    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    public void tick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    void setOpen(BlockState state, boolean open) {
        this.world.setBlockState(this.getPos(), (BlockState)state.with(BarrelBlock.OPEN, open), 3);
    }

    void playSound(BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = ((Direction)state.get(BarrelBlock.FACING)).getVector();
        double d = (double)this.pos.getX() + 0.5 + (double)vec3i.getX() / 2.0;
        double e = (double)this.pos.getY() + 0.5 + (double)vec3i.getY() / 2.0;
        double f = (double)this.pos.getZ() + 0.5 + (double)vec3i.getZ() / 2.0;
        this.world.playSound((PlayerEntity)null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
        super.addComponents(componentMapBuilder);
        componentMapBuilder.add(DataComponentTypes.DYED_COLOR, this.dyedColor);
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putInt("backpack_level", level);
        componentMapBuilder.add(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbtCompound));
    }

    @Override
    protected void readComponents(ComponentsAccess components) {
        super.readComponents(components);
        this.dyedColor = components.getOrDefault(DataComponentTypes.DYED_COLOR, new DyedColorComponent(DyeColor.BROWN.getEntityColor(), true));
        this.level = components.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getInt("backpack_level");
        this.inventory = DefaultedList.ofSize(9*level, ItemStack.EMPTY);
        components.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).copyTo(this.getHeldStacks());
    }
    public DyedColorComponent getDyedColor(){
        return dyedColor;
    }
    public int getLevel(){return level;}

}
