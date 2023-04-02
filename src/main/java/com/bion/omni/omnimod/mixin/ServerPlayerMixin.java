package com.bion.omni.omnimod.mixin;

import com.bion.omni.omnimod.elements.Air;
import com.mojang.authlib.GameProfile;
import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.elements.Element;
import com.bion.omni.omnimod.elements.Moon;
import com.bion.omni.omnimod.elements.Storm;
import com.bion.omni.omnimod.powers.*;
import com.bion.omni.omnimod.util.Apprentice;
import com.bion.omni.omnimod.util.EntityDataInterface;
import com.bion.omni.omnimod.util.LeftClickItem;
import com.bion.omni.omnimod.util.PowerConfig;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;


@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin extends PlayerEntity implements Apprentice {
	int tickCounter20 = 0;
	private Element element = null;
	private double mana = -1;
	private Integer manaMaxLevel = 1;
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
	private final Hashtable<String, Double> costs = new Hashtable<String, Double>();
	private final ArrayList<ContinuousPower> continuousPowers = new ArrayList<>();
	private final ArrayList<ImpulsePower> impulsePowers = new ArrayList<>();
	private final ArrayList<Power> otherPowers = new ArrayList<>();
	private final ArrayList<PowerConfig> config = new ArrayList<>();


	public Hashtable<String, Double> getCosts() {
		return costs;
	}
	public Element getElement() { return element; }
	public void setElement(Element element) {
		this.element = element;
	}
	public void setElement(String elementId) {
		this.element = switch(elementId) {
			case "Moon":
				yield new Moon();
			case "Storm":
				yield new Storm();
			case "Air":
				yield new Air();
			default:
				OmniMod.LOGGER.error("Error: " + elementId + " element not defined" );
				yield null;
		};
	}
	public void addConfig (String id, Integer value) {
		config.add(new PowerConfig(id, value));
	}
	public void setConfig (String id, Integer value) {
		for (PowerConfig config : this.config) {
			if (Objects.equals(config.getId(), id)) {
				config.setValue(value);
			}
		}
	}
	public Integer getConfigValue(String id) {
		for (PowerConfig config : this.config) {
			if (Objects.equals(config.getId(), id)) {
				return config.getValue();
			}
		}
		return -1;
	}
	public ArrayList<PowerConfig> getConfig() {
		return config;
	}
	public void reorderConfig(String id, Integer amount) {
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
	public ArrayList<ContinuousPower> getContinuousPowers() { return continuousPowers; }
	public ArrayList<ImpulsePower> getImpulsePowers() { return impulsePowers; }
	public ArrayList<Power> getOtherPowers() { return otherPowers; }
	public ArrayList<Power> getAllPowers() {
		ArrayList<Power> allPowers = new ArrayList<>();
		allPowers.addAll(continuousPowers);
		allPowers.addAll(impulsePowers);
		allPowers.addAll(otherPowers);
		return allPowers;
	}
	public Power getPowerById(String id) {
		for (Power power : getAllPowers()) {
			if (Objects.equals(power.getId(), id)) {
				return power;
			}
		}
		return null;
	}
	public double getMana() {
		return mana;
	}
	public void setMana(double value) {
		mana = value;
	}
	public void changeMana(double value) {
		mana += value;
	}
	public Integer getManaMax() {
		return manaMaxLevel != null ? manaMaxLevel * 60 : null;
	}
	public Integer getManaMaxLevel() {
		return manaMaxLevel;
	}
	public void upgradeManaMax() {
		manaMaxLevel += 1;
	}
	public void setManaMaxLevel(Integer value) {
		manaMaxLevel = value;
	}
	public double getManaRegen() {
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

	public Integer getManaRegenLevel() {
		return manaRegenLevel;
	}
	public void upgradeManaRegen() {
		manaRegenLevel += 1;
	}
	public void setManaRegenLevel(Integer value) {
		manaRegenLevel = value;
	}

	private void addContinuousPower(ContinuousPower power) {
		continuousPowers.add(power);
	}
	private void addImpulsePower(ImpulsePower power) {
		impulsePowers.add(power);
	}
	private void addOtherPower(Power power) {
		otherPowers.add(power);
	}
	public Power addPower(Power power) {
		Advancement advancement = server.getAdvancementLoader().get(new Identifier(((Apprentice)this).getElement().getName().toLowerCase(), power.getAdvancementId()));
		if (advancement != null) {
			advancementTracker.grantCriterion(advancement, advancement.getCriteria().keySet().iterator().next());
		}
		if (getPowerById(power.getId()) != null) {
			return getPowerById(power.getId()).increaseLevel();
		}
		if (power.hasConfig() && getConfigValue(power.getId()) == -1) {
			addConfig(power.getId(), 0);
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
	public Power addPower(String id) {
		if (Objects.equals(id, "changeWandPage")) {
			return addPower(new ChangeWandPage());
		} else {
			if (element.getPower(id) != null) {
				return addPower(element.getPower(id));
			} else {
				OmniMod.LOGGER.info("Error: " + id + " is not a valid power id");
				return null;
			}
		}
	}

	@Override
	public void buyPower(String id, Integer level) {
		if (getInfluence() >= element.getPower(id, level).getInfluenceCost()) {
			changeInfluence(-element.getPower(id, level).getInfluenceCost());
			addPower(id);
		}
	}

	public void clearPowers() {
		config.clear();
		continuousPowers.clear();
		impulsePowers.clear();
		otherPowers.clear();
	}

	@Override
	public Integer getInfluence() {
		Scoreboard scoreboard = this.getScoreboard();
		ScoreboardPlayerScore score = scoreboard.getPlayerScore(this.getName().getString(), scoreboard.getObjective("Influence"));
		return score.getScore();
	}
	@Override
	public void setInfluence(Integer amount) {
		Scoreboard scoreboard = this.getScoreboard();
		ScoreboardPlayerScore score = scoreboard.getPlayerScore(this.getName().getString(), scoreboard.getObjective("Influence"));
		score.setScore(amount);
	}

	@Override
	public void changeInfluence(Integer amount) {
		Scoreboard scoreboard = this.getScoreboard();
		ScoreboardPlayerScore score = scoreboard.getPlayerScore(this.getName().getString(), scoreboard.getObjective("Influence"));
		score.incrementScore(amount);
	}

	@Override
	public void interpretWandCommand(String[] components) {
		if (components[1].matches("-?\\d+")) {
			if (Integer.parseInt(components[1]) != ((Apprentice)this).getConfigValue(components[0])) {
				((Apprentice)this).setConfig(components[0], Integer.parseInt(components[1]));
			} else {
				((Apprentice)this).setConfig(components[0], 0);
			}
		} else if (components[1].equals("activate")) {
			Power power = getPowerById(components[0]);
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
	public void interpretWandCommand(String command) {
		interpretWandCommand(command.split("\\."));
	}

	@Override
	public Integer getWandPage() {
		return ((EntityDataInterface)this).getPersistentData().getInt("wandPage") == 2 ? 2 : 1;
	}

	@Override
	public void changeWandPage() {
		if (((EntityDataInterface)this).getPersistentData().getInt("wandPage") == 2) {
			((EntityDataInterface)this).getPersistentData().putInt("wandPage", 1);
		} else {
			((EntityDataInterface)this).getPersistentData().putInt("wandPage", 2);
		}
	}


	@Inject(at = @At("HEAD"), method = "tick")
	private void init(CallbackInfo ci) {
		if (this.getMana() > -1) {
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
		for (ContinuousPower power : getContinuousPowers()) {
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
		for (ImpulsePower power : getImpulsePowers()) {
			if (power.isTicking()) {
				power.tick((ServerPlayerEntity)(Object) this);
			}
		}
	}
	@Inject(at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, method = "copyFrom")
	private void copyData(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
		Apprentice oldApprentice = (Apprentice)oldPlayer;
		this.element = oldApprentice.getElement();
		this.continuousPowers.addAll(oldApprentice.getContinuousPowers());
		this.impulsePowers.addAll(oldApprentice.getImpulsePowers());
		this.otherPowers.addAll(oldApprentice.getOtherPowers());
		this.config.addAll(oldApprentice.getConfig());
		this.mana = oldApprentice.getMana();
		this.manaMaxLevel = oldApprentice.getManaMaxLevel();
		this.manaRegenLevel = oldApprentice.getManaRegenLevel();
		this.costs.putAll(oldApprentice.getCosts());
		for (String key : ((EntityDataInterface)oldApprentice).getPersistentData().getKeys()) {
			if (((EntityDataInterface)oldApprentice).getPersistentData().contains(key, NbtElement.STRING_TYPE)) {
				((EntityDataInterface)this).getPersistentData().putString(key, ((EntityDataInterface)oldApprentice).getPersistentData().getString(key));
			}
		}
	}
}
