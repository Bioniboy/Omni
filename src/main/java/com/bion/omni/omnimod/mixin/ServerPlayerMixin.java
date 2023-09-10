package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.elements.*;
import com.bion.omni.omnimod.util.*;
import com.mojang.authlib.GameProfile;
import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.powers.*;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtElement;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;


@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin extends PlayerEntity implements Apprentice, AfkUtil {

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

	@Unique
	int activeTicks = 0;
	@Unique
	int prevActiveDay = -1;
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
		Advancement advancement = server.getAdvancementLoader().get(new Identifier(((Apprentice)this).omni$getElement().getName().toLowerCase(), power.getAdvancementId()));
		if (advancement != null) {
			advancementTracker.grantCriterion(advancement, advancement.getCriteria().keySet().iterator().next());
		}
		if (omni$getPowerById(power.getId()) != null) {
			return omni$getPowerById(power.getId()).increaseLevel();
		}
		if (power.hasConfig() && omni$getConfigValue(power.getId()) == -1) {
			omni$addConfig(power.getId(), 0);
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
			return omni$addPower(new ChangeWandPage());
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
		Scoreboard scoreboard = this.getScoreboard();
		ScoreboardPlayerScore score = scoreboard.getPlayerScore(this.getName().getString(), scoreboard.getObjective("Influence"));
		return score.getScore();
	}
	@Override
	public void omni$setInfluence(Integer amount) {
		Scoreboard scoreboard = this.getScoreboard();
		ScoreboardPlayerScore score = scoreboard.getPlayerScore(this.getName().getString(), scoreboard.getObjective("Influence"));
		score.setScore(amount);
	}

	@Override
	public void omni$changeInfluence(Integer amount) {
		Scoreboard scoreboard = this.getScoreboard();
		ScoreboardPlayerScore score = scoreboard.getPlayerScore(this.getName().getString(), scoreboard.getObjective("Influence"));
		score.incrementScore(amount);
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


	@Inject(at = @At("HEAD"), method = "tick")
	private void init(CallbackInfo ci) {
		if (this.omni$getMana() > -1) {
			if (tickCounter20 < 20) {
				tickCounter20++;
			} else {
				tickCounter20 = 0;
				Mana.manaUpdate(this);
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

		((AfkUtil)this).omni$setActiveTicks(((AfkUtil)oldPlayer).omni$getActiveTicks());
		((AfkUtil)this).omni$setPrevActiveDay(((AfkUtil)oldPlayer).omni$getPrevActiveDay());
	}
}
