package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.block.entity.BackpackBlockEntity;
import com.bion.omni.omnimod.element.*;
import com.bion.omni.omnimod.entity.ModEntities;
import com.bion.omni.omnimod.entity.custom.Backpacks.ArmorEntityHolder;
import com.bion.omni.omnimod.entity.custom.Pet;
import com.bion.omni.omnimod.item.BackpackItem;
import com.bion.omni.omnimod.item.ModPotions;
import com.bion.omni.omnimod.util.*;
import com.mojang.authlib.GameProfile;
import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.power.*;
import eu.pb4.polymer.virtualentity.api.VirtualEntityUtils;
import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.component.type.FireworkExplosionComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerRecipeBook;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.joml.Matrix4x3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;

import javax.naming.Context;
import javax.swing.text.AbstractDocument;
import java.util.*;
import java.util.function.Predicate;


@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin extends PlayerEntity implements Apprentice, AfkUtil {


	@Unique
	boolean inMansion = false;

	@Override
	public void omni$setInMansion(boolean inMansion) {
		this.inMansion = inMansion;
	}

	@Override
	public boolean omni$getInMansion() {
		return inMansion;
	}

	@Unique
	boolean inOpMode = false;

	@Override
	public void omni$setInOpMode(boolean inOpMode) {
		this.inOpMode = inOpMode;
	}

	@Override
	public boolean omni$inOpMode() {
		return inOpMode;
	}

	@Override
	public void omni$setOpModeOtherPos(Vec3d pos, World world) {
		opModeOtherPos = pos;
		opModeOtherWorld = world;
	}

	@Override
	public Vec3d omni$getOpModeOtherPos() {
		return opModeOtherPos;
	}

	@Override
	public World omni$getOpModeOtherWorld() {
		return opModeOtherWorld;
	}

	@Unique
	Vec3d opModeOtherPos = null;

	@Unique
	World opModeOtherWorld = null;

	@Unique
	HashMap<String, PlayerInventory> savedInventories = new HashMap<>();
	@Unique
	Vec3d home = null;

	@Unique
	World homeWorld = null;

	@Override
	public void omni$addSavedInventory(String id, PlayerInventory inventory) {
		savedInventories.put(id, inventory);
	}

	@Override
	public PlayerInventory omni$removeSavedInventory(String id) {
		return savedInventories.remove(id);
	}

	@Override
	public PlayerInventory omni$getSavedInventory(String id) {
		return savedInventories.get(id);
	}

	@Override
	public Set<String> omni$getInventoryKeys() {
		return savedInventories.keySet();
	}

	@Override
	public void omni$setHome(Vec3d pos, World world) {
		home = pos;
		homeWorld = world;
	}

	@Override
	public Vec3d omni$getHomePos() {
		return home;
	}

	@Override
	public World omni$getHomeWorld() {
		return homeWorld;
	}

	@Override
	public int omni$getActiveTicks() {
		return activeTicks;
	}

	@Override
	public void omni$setActiveTicks(int ticks) {
		activeTicks = ticks;
	}

	@Override
	public void omni$changeActiveTicks(int ticks) {
		activeTicks += ticks;
	}

	@Override
	public int omni$getPrevActiveDay() {
		return prevActiveDay;
	}

	@Override
	public void omni$setPrevActiveDay(int day) {
		prevActiveDay = day;
	}
	@Override
	public void omni$setGotReward(boolean gotReward) {
		this.gotReward = gotReward;
	}
	@Override
	public boolean omni$getGotReward() {
		return gotReward;
	}

	@Override
	public void omni$setStreak(int streak) {
		this.streak = streak;
	}

	@Override
	public int omni$getStreak() {
		return streak;
	}

	@Override
	public void omni$setNextStreakDay(int day) {
		nextStreakDay = day;
	}

	@Override
	public int omni$getNextStreakDay() {
		return nextStreakDay;
	}

	@Override
	public void omni$setNextStreakYear(int year) {
		nextStreakYear = year;
	}

	@Override
	public int omni$getNextStreakYear() {
		return nextStreakYear;
	}

	@Unique
	int activeTicks = 0;
	@Unique
	int prevActiveDay = -1;
	@Unique
	boolean gotReward = false;
	@Unique
	int streak = 0;
	@Unique
	int nextStreakDay = 1;
	@Unique
	int nextStreakYear = 0;
	@Unique
	int tickCounter20 = 0;
	@Unique
	private Element element = null;
	@Unique
	private double mana = -1;
	@Unique
	private Integer manaMaxLevel = 1;
	@Unique
	private Integer manaRegenLevel = 1;

	@Final
	@Shadow
	public MinecraftServer server;

	@Shadow
	public ServerPlayNetworkHandler networkHandler;
	@Final
	@Shadow
	private PlayerAdvancementTracker advancementTracker;


	@Shadow public abstract ServerWorld getServerWorld();

	@Shadow public abstract void sendAbilitiesUpdate();

	@Shadow public abstract Entity getCameraEntity();

	@Shadow public abstract void requestTeleport(double destX, double destY, double destZ);

	public ServerPlayerMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
		super(world, pos, yaw, gameProfile);
	}
	@Unique
	private final Hashtable<String, Double> costs = new Hashtable<>();
	@Unique
	private final ArrayList<ContinuousPower> continuousPowers = new ArrayList<>();
	@Unique
	private final ArrayList<ImpulsePower> impulsePowers = new ArrayList<>();
	@Unique
	private final ArrayList<Power> otherPowers = new ArrayList<>();
	@Unique
	private final ArrayList<PowerConfig> config = new ArrayList<>();


	public Hashtable<String, Double> omni$getCosts() {
		return costs;
	}
	public Element omni$getElement() { return element; }
	public void omni$setElement(Element element) {
		this.element = element;
	}
	public void omni$setElement(String elementId) {
		this.element = switch(elementId) {
			case "Moon":
				yield new Moon();
			case "Storm":
				yield new Storm();
			case "Air":
				yield new Air();
			case "Clarity":
				yield new Clarity();
			case "Life":
				yield new Life();
			case "Fire":
				yield new Fire();
			case "Water":
				yield new Water();
			case "Magic":
				yield new Magic();
			case "Tech":
				yield new Tech();
			case "Earth":
				yield new Earth();
			case "Sun":
				yield new Sun();
			default:
				OmniMod.LOGGER.error("Error: " + elementId + " element not defined" );
				yield null;
		};
	}
	public void omni$addConfig(String id, Integer value) {
		config.add(new PowerConfig(id, value));
	}
	public void omni$setConfig(String id, Integer value) {
		for (PowerConfig config : this.config) {
			if (Objects.equals(config.getId(), id)) {
				config.setValue(value);
			}
		}
	}
	public Integer omni$getConfigValue(String id) {
		for (PowerConfig config : this.config) {
			if (Objects.equals(config.getId(), id)) {
				return config.getValue();
			}
		}
		return -1;
	}
	public ArrayList<PowerConfig> omni$getConfig() {
		return config;
	}
	public void omni$reorderConfig(String id, Integer amount) {
		int index = 0;
		for (int i = 0; i < config.size(); i++) {
			if (Objects.equals(config.get(i).getId(), id)) {
				index = i;
				break;
			}
		}
		if (index + amount >= 0 && index + amount < config.size()) {
			PowerConfig temp = config.get(index);
			config.set(index, config.get(index + amount));
			config.set(index + amount, temp);
		}
	}
	public ArrayList<ContinuousPower> omni$getContinuousPowers() { return continuousPowers; }
	public ArrayList<ImpulsePower> omni$getImpulsePowers() { return impulsePowers; }
	public ArrayList<Power> omni$getOtherPowers() { return otherPowers; }
	public ArrayList<Power> omni$getAllPowers() {
		ArrayList<Power> allPowers = new ArrayList<>();
		allPowers.addAll(continuousPowers);
		allPowers.addAll(impulsePowers);
		allPowers.addAll(otherPowers);
		return allPowers;
	}
	public Power omni$getPowerById(String id) {
		for (Power power : omni$getAllPowers()) {
			if (Objects.equals(power.getId(), id)) {
				return power;
			}
		}
		return null;
	}
	public double omni$getMana() {
		return mana;
	}
	public void omni$setMana(double value) {
		mana = value;
	}
	public void omni$changeMana(double value) {
		mana += value;
	}
	public Integer omni$getManaMax() {
		return manaMaxLevel != null ? manaMaxLevel * 60 : null;
	}
	public Integer omni$getManaMaxLevel() {
		return manaMaxLevel;
	}
	public void omni$upgradeManaMax() {
		manaMaxLevel += 1;
	}
	public void omni$setManaMaxLevel(Integer value) {
		manaMaxLevel = value;
	}
	public double omni$getManaRegen() {
		return switch(manaRegenLevel) {
			case 1:
				yield 0.2;
			case 2:
				yield 0.25;
			case 3:
				yield 0.5;
			case 4:
				yield 1;
			case 5:
				yield 2;
			case 6:
				yield 3;
			default:
				yield 0;
		};
	}

	public Integer omni$getManaRegenLevel() {
		return manaRegenLevel;
	}
	public void omni$upgradeManaRegen() {
		manaRegenLevel += 1;
	}
	public void omni$setManaRegenLevel(Integer value) {
		manaRegenLevel = value;
	}

	@Unique
	private void addContinuousPower(ContinuousPower power) {
		continuousPowers.add(power);
	}
	@Unique
	private void addImpulsePower(ImpulsePower power) {
		impulsePowers.add(power);
	}
	@Unique
	private void addOtherPower(Power power) {
		otherPowers.add(power);
	}
	public Power omni$addPower(Power power) {
		if (omni$getPowerById(power.getId()) != null) {
			power.setLevel(omni$getPowerById(power.getId()).getLevel() + 1);
		}
		AdvancementEntry advancement = server.getAdvancementLoader().get(Identifier.of(((Apprentice)this).omni$getElement().getName().toLowerCase(), power.getAdvancementId()));
		if (advancement != null) {
			advancementTracker.grantCriterion(advancement, advancement.id().toString());
		}
		if (power.hasConfig() && omni$getConfigValue(power.getId()) == -1) {
			omni$addConfig(power.getId(), power.getDefaultConfig());
		}
		if (omni$getPowerById(power.getId()) != null) {
			return omni$getPowerById(power.getId()).increaseLevel();
		}
		if (power instanceof ContinuousPower continuousPower) {
			addContinuousPower(continuousPower);
		} else if (power instanceof ImpulsePower impulsePower) {
			addImpulsePower(impulsePower);
		} else {
			addOtherPower(power);
		}
		if (networkHandler != null) {
			server.getCommandManager().sendCommandTree((ServerPlayerEntity) (Object) this);
		}
		return power;
	}
	public Power omni$addPower(String id) {
		if (Objects.equals(id, "changeWandPage")) {
			return omni$addPower(new ChangeWandPage(1));
		} else {
			if (element.getPower(id) != null) {
				return omni$addPower(element.getPower(id));
			} else {
				OmniMod.LOGGER.info("Error: " + id + " is not a valid power id");
				return null;
			}
		}
	}

	@Override
	public void omni$buyPower(String id, Integer level) {
		if (omni$getInfluence() >= element.getPower(id, level).getInfluenceCost()) {
			omni$changeInfluence(-element.getPower(id, level).getInfluenceCost());
			omni$addPower(id);
		}
	}

	public void omni$clearPowers() {
		config.clear();
		continuousPowers.clear();
		impulsePowers.clear();
		otherPowers.clear();
	}

	@Override
	public Integer omni$getInfluence() {

		return getScoreboard().getOrCreateScore(this, getScoreboard().getNullableObjective("Influence")).getScore();
	}
	@Override
	public void omni$setInfluence(Integer amount) {
		getScoreboard().getOrCreateScore(this, getScoreboard().getNullableObjective("Influence")).setScore(amount);
	}

	@Override
	public void omni$changeInfluence(Integer amount) {
		getScoreboard().getOrCreateScore(this, getScoreboard().getNullableObjective("Influence")).incrementScore(amount);
	}

	@Override
	public void omni$interpretWandCommand(String[] components) {
		if (components[1].matches("-?\\d+")) {
			if (Integer.parseInt(components[1]) != ((Apprentice)this).omni$getConfigValue(components[0])) {
				((Apprentice)this).omni$setConfig(components[0], Integer.parseInt(components[1]));
			} else {
				((Apprentice)this).omni$setConfig(components[0], 0);
			}
		} else if (components[1].equals("activate")) {
			Power power = omni$getPowerById(components[0]);
			if (components.length > 2) {
				switch (components[2]) {
					case "1" -> power.activate((ServerPlayerEntity) (Object) this);
					case "2" -> power.activate2((ServerPlayerEntity) (Object) this);
					case "3" -> power.activate3((ServerPlayerEntity) (Object) this);
				}
			} else {
				power.activate((ServerPlayerEntity)(Object) this);
			}

		}
	}
	@Override
	public void omni$interpretWandCommand(String command) {
		omni$interpretWandCommand(command.split("\\."));
	}

	@Override
	public Integer omni$getWandPage() {
		return ((EntityDataInterface)this).getPersistentData().getInt("wandPage") == 2 ? 2 : 1;
	}

	@Override
	public void omni$changeWandPage() {
		if (((EntityDataInterface)this).getPersistentData().getInt("wandPage") == 2) {
			((EntityDataInterface)this).getPersistentData().putInt("wandPage", 1);
		} else {
			((EntityDataInterface)this).getPersistentData().putInt("wandPage", 2);
		}
	}




	@Unique
	boolean firstTick = true;
	@Unique
	Vec3d from = null;
	@Unique
	Float prevYaw = null;
	@Unique
	Float backpackYaw = null;
	@Unique
	public void turnBody(float bodyRotation, float yaw) {
		float f = MathHelper.wrapDegrees(bodyRotation - backpackYaw);
		backpackYaw += (f * 0.3F);
		float g = MathHelper.wrapDegrees(yaw - backpackYaw);
		if (g < -75.0F) {
			g = -75.0F;
		}
		if (g >= 75.0F) {
			g = 75.0F;
		}
		backpackYaw = yaw - g;
		if (g * g > 2500.0F) {
			backpackYaw += g * 0.2F;
		}
	}
	@Inject(at = @At("HEAD"), method = "tick")
	private void tick(CallbackInfo ci) {
		if(this.getInventory().armor.get(2).getItem() instanceof BackpackItem){
			item.updateLastSyncedPos();
			Vec3d to = this.getPos();
			if(backpackYaw == null){
				backpackYaw = bodyYaw;
			}
			if (from == null) {
				from = to;
			}
			if (prevYaw == null || bodyYaw != prevYaw || !to.equals(from)) {
				float yaw = this.getYaw();
				double deltaX = to.getX() - from.getX();
				double deltaZ = to.getZ() - from.getZ();
				float distanceSquared = (float) (deltaX * deltaX + deltaZ * deltaZ);
				float bodyTargetYaw = backpackYaw;
				if (distanceSquared > 0.0025000002F) {
					// Using internal Mojang math utils here
					float targetYaw = (float) MathHelper.atan2(deltaZ, deltaX) * 57.295776F - 90.0F;

					float m = MathHelper.abs(MathHelper.wrapDegrees(yaw) - targetYaw);

					if (95.0F < m && m < 265.0F) {
						bodyTargetYaw = targetYaw - 180.0F;
					} else {
						bodyTargetYaw = targetYaw;
					}
				}
				this.turnBody(bodyTargetYaw, yaw);
				Matrix4x3f matrix = new Matrix4x3f();
				matrix.rotateY((MathHelper.wrapDegrees(backpackYaw + 180) * MathHelper.RADIANS_PER_DEGREE) * -1);
				matrix.translate(0, -1.42f, 0);
				item.setTransformation(matrix);
				if (item.getDataTracker().isDirty()) {
					item.startInterpolation();
				}
			}
			prevYaw = bodyYaw;
			from = to;
		}else if(!elementHolder.getElements().isEmpty()){
			firstTick = true;
			elementHolder.removeElement(elementHolder.getElements().getFirst());
		}


		Box entityBox = new Box(getBlockPos()).expand(32);
		for (Entity entity : getWorld().getOtherEntities(this, entityBox, entity -> entity instanceof ServerPlayerEntity)) {
//			elementHolder.startWatching((ServerPlayerEntity) entity);
			((ServerPlayerEntity) entity).networkHandler.sendPacket(VirtualEntityUtils.createRidePacket(this.getId(), item.getEntityIds()));
			OmniMod.LOGGER.info("Found player " + elementHolder.getWatchingPlayers());
		}

		if (firstTick && this.getInventory().armor.get(2).getItem() instanceof BackpackItem){
			ItemStack backpackItem = this.getInventory().armor.get(2);
			ItemStack backpack = Items.FIREWORK_STAR.getDefaultStack();
			OmniMod.LOGGER.info("" + (backpackItem.getComponents().getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getInt("backpack_level")));
			backpack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(850006 + backpackItem.getComponents().getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getInt("backpack_level")));
			backpack.set(DataComponentTypes.FIREWORK_EXPLOSION, new FireworkExplosionComponent(FireworkExplosionComponent.Type.CREEPER, IntList.of(backpackItem.getOrDefault(DataComponentTypes.DYED_COLOR, new DyedColorComponent(BackpackItem.DEFAULT_COLOR, false)).rgb()), IntList.of(), false, false));
			item.setItem(backpack);
			item.ignorePositionUpdates();
			elementHolder.addElement(item);
			EntityAttachment.ofTicking(elementHolder, (ServerPlayerEntity)(Object)this);
			//elementHolder.startWatching((ServerPlayerEntity)(Object)this);
			networkHandler.sendPacket(VirtualEntityUtils.createRidePacket(this.getId(), item.getEntityIds()));
			firstTick = false;
			item.setInterpolationDuration(1);
		}
//		if(this.omni$getElement() != null){
//			ActionBarManager.displayActionBar(((ServerPlayerEntity)(Object)this));
//
//		}
		if (omni$getElement() != null) {
			if (this.omni$getMana() > -1) {
				if (tickCounter20 < 20) {
					tickCounter20++;
				} else {
					tickCounter20 = 0;
					Mana.manaUpdate(this);
				}
			}
		}
		if (this.handSwingTicks == 1) {
			if (this.getMainHandStack().getItem() instanceof LeftClickItem clickItem) {
				clickItem.click(this);
			}
		}
		for (ContinuousPower power : omni$getContinuousPowers()) {
			if (power.isActive((ServerPlayerEntity)(Object) this) && power.getManaCost() <= mana) {
				if (!power.isActive) {
					power.start((ServerPlayerEntity)(Object) this);
				}
				power.use((ServerPlayerEntity)(Object) this);
				power.isActive = true;
			} else if (power.isActive) {
				power.stop((ServerPlayerEntity)(Object) this);
				power.isActive = false;
			}
		}
		for (ImpulsePower power : omni$getImpulsePowers()) {
			if (power.isTicking()) {
				power.tick((ServerPlayerEntity)(Object) this);
			}
		}

		if (petCooldown > 0) {
			petCooldown -= 1;
		}

		if (isHolding((ItemStack stack) -> stack.get(DataComponentTypes.POTION_CONTENTS) != null && (stack.get(DataComponentTypes.POTION_CONTENTS).matches(ModPotions.MARK) || stack.get(DataComponentTypes.POTION_CONTENTS).matches(ModPotions.RECALL))) && home != null && homeWorld == getWorld() && home.squaredDistanceTo(getPos()) < 64) {
			networkHandler.sendPacket(new ParticleS2CPacket(ParticleTypes.WITCH, true, home.x, home.y, home.z, 0.2F, 0, 0.2F, 0, 1));
		}
		if(backpackCooldown > 0){
			backpackCooldown -= 1;
		}

//		Optional<EnchantmentEffectContext> optional = EnchantmentHelper.chooseEquipmentWith(EnchantmentEffectComponentTypes.REPAIR_WITH_XP, this, ItemStack::isDamaged);
//		if (optional.isPresent()){
//			ItemStack itemStack = optional.get().stack();
//			itemStack.setDamage(itemStack.getDamage() - 1);
//		}
	}

	@Inject(at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, method = "copyFrom")
	private void copyData(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
		Apprentice oldApprentice = (Apprentice)oldPlayer;
		this.element = oldApprentice.omni$getElement();
		this.continuousPowers.addAll(oldApprentice.omni$getContinuousPowers());
		this.impulsePowers.addAll(oldApprentice.omni$getImpulsePowers());
		this.otherPowers.addAll(oldApprentice.omni$getOtherPowers());
		this.config.addAll(oldApprentice.omni$getConfig());
		this.mana = oldApprentice.omni$getMana();
		this.manaMaxLevel = oldApprentice.omni$getManaMaxLevel();
		this.manaRegenLevel = oldApprentice.omni$getManaRegenLevel();
		this.costs.putAll(oldApprentice.omni$getCosts());
		for (String key : ((EntityDataInterface)oldApprentice).getPersistentData().getKeys()) {
			if (((EntityDataInterface)oldApprentice).getPersistentData().contains(key, NbtElement.STRING_TYPE)) {
				((EntityDataInterface)this).getPersistentData().putString(key, ((EntityDataInterface)oldApprentice).getPersistentData().getString(key));
			}
			if (((EntityDataInterface)oldApprentice).getPersistentData().contains(key, NbtElement.INT_TYPE)) {
				((EntityDataInterface)this).getPersistentData().putInt(key, ((EntityDataInterface)oldApprentice).getPersistentData().getInt(key));
			}
		}

		omni$setActiveTicks(((AfkUtil)oldPlayer).omni$getActiveTicks());
		omni$setPrevActiveDay(((AfkUtil)oldPlayer).omni$getPrevActiveDay());
		omni$setGotReward(((AfkUtil)oldPlayer).omni$getGotReward());

		this.streak = ((AfkUtil) oldApprentice).omni$getStreak();
		this.nextStreakYear = ((AfkUtil) oldApprentice).omni$getNextStreakYear();
		this.nextStreakDay = ((AfkUtil) oldApprentice).omni$getNextStreakDay();

		pet = ((Apprentice) oldPlayer).omni$getPet();
		petCooldown = ((Apprentice) oldPlayer).omni$getPetCooldown();

		home = ((Apprentice) oldPlayer).omni$getHomePos();
		homeWorld = ((Apprentice) oldPlayer).omni$getHomeWorld();

		for (String key : ((Apprentice) oldPlayer).omni$getInventoryKeys())
			savedInventories.put(key, ((Apprentice) oldPlayer).omni$getSavedInventory(key));

		opModeOtherPos = oldApprentice.omni$getOpModeOtherPos();
		opModeOtherWorld = oldApprentice.omni$getOpModeOtherWorld();

		inOpMode = oldApprentice.omni$inOpMode();
		inMansion = oldApprentice.omni$getInMansion();

	}

	@Inject(method="readCustomDataFromNbt", at=@At("TAIL"))
	protected void injectReadMethod(NbtCompound nbt, CallbackInfo info) {
		if (nbt.contains("omnimod.mana")) {
			omni$setMana(nbt.getInt("omnimod.mana"));
		}
		if (nbt.contains("omnimod.manaMaxLevel")) {
			omni$setManaMaxLevel(nbt.getInt("omnimod.manaMaxLevel"));
		}
		if (nbt.contains("omnimod.manaRegenLevel")) {
			omni$setManaRegenLevel(nbt.getInt("omnimod.manaRegenLevel"));
		}
		if (nbt.contains("omnimod.element")) {
			omni$setElement(nbt.getString("omnimod.element"));
		}
		if (nbt.contains("omnimod.powerConfig")) {
			for (String config : nbt.getString("omnimod.powerConfig").split(",")) {
				String[] configList = config.split(":");
				omni$addConfig(configList[0], Integer.parseInt(configList[1]));
			}
		}
		if (nbt.contains("omnimod.powers", NbtElement.COMPOUND_TYPE)) {
			for (String key : nbt.getCompound("omnimod.powers").getKeys()) {
				Power playerPower = omni$addPower(key);
				if (playerPower != null) {
					playerPower.setNbt(nbt.getCompound("omnimod.powers").getCompound(key), getRegistryManager());
				}
			}
		}
		if (nbt.contains("omnimod.activeTicks")) {
			((AfkUtil)this).omni$setActiveTicks(nbt.getInt("omnimod.activeTicks"));
		}
		if (nbt.contains("omnimod.prevActiveDay")) {
			((AfkUtil)this).omni$setPrevActiveDay(nbt.getInt("omnimod.prevActiveDay"));
		}
		if (nbt.contains("omnimod.gotReward")) {
			((AfkUtil) this).omni$setGotReward(nbt.getBoolean("omnimod.gotReward"));
		}
		if (nbt.contains("omnimod.Streak")) {
			streak = nbt.getInt("omnimod.Streak");
		}
		if (nbt.contains("omnimod.NextStreakDay")) {
			nextStreakDay = nbt.getInt("omnimod.NextStreakDay");
		}
		if (nbt.contains("omnimod.NextStreakYear")) {
			nextStreakYear = nbt.getInt("omnimod.NextStreakYear");
		}
		if (nbt.contains("omnimod.pet")) {
			pet = ModEntities.PET.create(getWorld());
			pet.readNbt(nbt.getCompound("omnimod.pet"));
		}
		if (nbt.contains("omnimod.PetCooldown")) {
			petCooldown = nbt.getInt("omnimod.PetCooldown");
		}
		if (nbt.contains("omnimod.HomePos")) {
			NbtList homePos = nbt.getList("omnimod.HomePos", NbtElement.DOUBLE_TYPE);
			home = new Vec3d(homePos.getDouble(0), homePos.getDouble(1), homePos.getDouble(2));
		}
		if (nbt.contains("omnimod.HomeWorld")) {
			for (var key : getServer().getWorldRegistryKeys()) {
				String registryKeyString = nbt.getString("omnimod.HomeWorld");
				if (Objects.equals(registryKeyString, key.toString())) {
					homeWorld = getServer().getWorld(key);
					break;
				}
			}
		}
		if (nbt.contains("omnimod.SavedInventories")) {
			NbtCompound inventories = nbt.getCompound("omnimod.SavedInventories");
			for (String key : inventories.getKeys()) {
				PlayerInventory inventory = new PlayerInventory(this);
				inventory.readNbt(inventories.getList(key, NbtElement.COMPOUND_TYPE));
				omni$addSavedInventory(key, inventory);
			}
		}
		if (nbt.contains("omnimod.OpModePos")) {
			NbtList opModePos = nbt.getList("omnimod.OpModePos", NbtElement.DOUBLE_TYPE);
			opModeOtherPos = new Vec3d(opModePos.getDouble(0), opModePos.getDouble(1), opModePos.getDouble(2));
		}
		if (nbt.contains("omnimod.OpModeWorld")) {
			for (var key : getServer().getWorldRegistryKeys()) {
				String registryKeyString = nbt.getString("omnimod.OpModeWorld");
				if (Objects.equals(registryKeyString, key.toString())) {
					opModeOtherWorld = getServer().getWorld(key);
					break;
				}
			}
		}
		if (nbt.contains("omnimod.InOpMode")) {
			inOpMode = nbt.getBoolean("omnimod.InOpMode");
		}
		if(nbt.contains("omnimod.RecipeBookStatus")){
			isRecipeBookOpen = nbt.getBoolean("omnimod.RecipeBookStatus");
		}
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	protected void injectWriteMethod(NbtCompound nbt, CallbackInfo info) {
		if (omni$getMana() > -1) {
			nbt.putDouble("omnimod.mana", omni$getMana());
		}
		if (omni$getManaMaxLevel() != null) {
			nbt.putInt("omnimod.manaMaxLevel", omni$getManaMaxLevel());
		}
		if (omni$getManaRegenLevel() != null) {
			nbt.putInt("omnimod.manaRegenLevel", omni$getManaRegenLevel());
		}
		if (omni$getElement() != null) {
			nbt.putString("omnimod.element", omni$getElement().getName());
		}
		if (!omni$getAllPowers().isEmpty()) {
			NbtCompound powers = new NbtCompound();
			for (Power power : omni$getAllPowers()) {
				powers.put(power.getId(), power.toNbt(getRegistryManager()));
			}
			nbt.put("omnimod.powers", powers);
		}
		if (!omni$getConfig().isEmpty()) {
			StringBuilder config = new StringBuilder();
			for (PowerConfig entry : omni$getConfig()) {
				config.append(entry.getId()).append(":").append(entry.getValue()).append(",");
			}
			nbt.putString("omnimod.powerConfig", config.toString());
		}
		nbt.putInt("omnimod.activeTicks", ((AfkUtil)this).omni$getActiveTicks());
		nbt.putInt("omnimod.prevActiveDay", ((AfkUtil)this).omni$getPrevActiveDay());
		nbt.putBoolean("omnimod.gotReward", ((AfkUtil)this).omni$getGotReward());
		nbt.putInt("omnimod.Streak", streak);
		nbt.putInt("omnimod.NextStreakDay", nextStreakDay);
		nbt.putInt("omnimod.NextStreakYear", nextStreakYear);
		if (omni$getPet() != null) {
			NbtCompound petNbt = omni$getPet().writeNbt(new NbtCompound());
			nbt.put("omnimod.pet", petNbt);
		}
		if (petCooldown != 0) {
			nbt.putInt("omnimod.PetCooldown", petCooldown);
		}
		if (home != null) {
			nbt.put("omnimod.HomePos", toNbtList(home.getX(), home.getY(), home.getZ()));
		}
		if (homeWorld != null) {
			nbt.putString("omnimod.HomeWorld", homeWorld.getRegistryKey().toString());
		}
		if (!savedInventories.isEmpty()) {
			NbtCompound inventories = new NbtCompound();
			for (String key : omni$getInventoryKeys()) {
				NbtList inventoryNbt = new NbtList();
				inventories.put(key, omni$getSavedInventory(key).writeNbt(inventoryNbt));
			}
			nbt.put("omnimod.SavedInventories", inventories);
		}
		if (opModeOtherPos != null) {
			nbt.put("omnimod.OpModePos", toNbtList(opModeOtherPos.getX(), opModeOtherPos.getY(), opModeOtherPos.getZ()));
		}
		if (opModeOtherWorld != null) {
			nbt.putString("omnimod.OpModeWorld", opModeOtherWorld.getRegistryKey().toString());
		}
		if (inOpMode)
			nbt.putBoolean("omnimod.InOpMode", inOpMode);
		nbt.putBoolean("omnimod.RecipeBookStatus", isRecipeBookOpen);
	}

	@Inject(method = "onDeath", at = @At("HEAD"))
	private void placeBackpack(DamageSource damageSource, CallbackInfo ci){
		if(this.getInventory().armor.get(2).getItem() instanceof BackpackItem backpackItem && !this.getInventory().armor.get(2).get(DataComponentTypes.CONTAINER).streamNonEmpty().toList().isEmpty()){
			BlockPos pos = this.getBlockPos();
			World world = this.getWorld();
			if(!world.getBlockState(pos).isSolidBlock(world, pos)){
				OmniMod.LOGGER.info("Placing");
				world.setBlockState(pos, backpackItem.getBlock().getDefaultState());
				BlockEntity blockEntity = world.getBlockEntity(pos);
				if (blockEntity != null) {
					blockEntity.readComponents(this.getInventory().armor.get(2));
					blockEntity.markDirty();
				}
				this.getInventory().armor.get(2).decrement(1);
//				BlockHitResult result = new BlockHitResult(new Vec3d(this.getPos().x, this.getPos().y - 1, this.getPos().z), Direction.DOWN, pos, false);
//				ItemUsageContext context = new ItemPlacementContext(this, Hand.MAIN_HAND, this.getInventory().armor.get(2), result);
//				ItemPlacementContext placementContext = new ItemPlacementContext(context);
//				OmniMod.LOGGER.info("Can place: " + placementContext.canPlace());
//				BlockState blockState = backpackItem.getBlock().getPlacementState(placementContext);
//				this.getInventory().armor.get(2).useOnBlock(context);
			}else{
				OmniMod.LOGGER.info("Scattering");
				this.getInventory().armor.get(2).get(DataComponentTypes.CONTAINER).iterateNonEmpty().forEach(itemStack -> ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), itemStack));
			}
		}
	}

	@Unique
	Pet pet = null;

	@Override
	public void omni$setPet(Pet entity) {
		pet = entity;
	}

	@Override
	public Pet omni$getPet() {
		return pet;
	}
	@Unique
	int petCooldown = 0;

	@Override
	public void omni$setPetCooldown(int cooldown) {
		petCooldown = cooldown;
	}

	@Override
	public int omni$getPetCooldown() {
		return petCooldown;
	}

	@Unique
	CustomCharDict charDict = new CustomCharDict();
	@Override
	public CustomChar omni$getChar(CustomCharDict.CharName p_name) {return charDict.getChar(p_name);}

//	@Unique
//	ActionBarManager actionBarManager = new ActionBarManager(((ServerPlayerEntity)(Object)this));

	@Unique
	ArmorEntityHolder elementHolder = new ArmorEntityHolder();

	@Unique
	ItemDisplayElement item = new ItemDisplayElement();
	@Unique
	int backpackCooldown = 0;
	@Override
	public int omni$getBackpackCooldown() {return backpackCooldown;}

	@Override
	public void omni$setBackpackCooldown(int cooldown) {
		backpackCooldown = cooldown;
	}

	@Unique
	boolean isRecipeBookOpen = false;

	@Override
	public boolean omni$getIsRecipeBookOpen(){return isRecipeBookOpen;}

	@Override
	public void omni$setIsRecipeBookOpen(boolean bool){isRecipeBookOpen = bool;}


}
