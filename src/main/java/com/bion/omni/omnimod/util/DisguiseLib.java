package com.bion.omni.omnimod.util;

import com.bion.omni.omnimod.OmniMod;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;

import static org.apache.logging.log4j.LogManager.getLogger;

public class DisguiseLib {

	/**
	 * Disables collisions with disguised entities.
	 * (Client predictions are horrible sometimes ... )
	 */
	public static final Team DISGUISE_TEAM = new Team(new Scoreboard(), "");

	public static void init() {
		DISGUISE_TEAM.setCollisionRule(AbstractTeam.CollisionRule.PUSH_OTHER_TEAMS);
		OmniMod.LOGGER.info("DisguiseLib loaded.");
		setPlayerClientVisibility(false);
	}

	public static void setPlayerClientVisibility(boolean clientVisibility) {
		DISGUISE_TEAM.setShowFriendlyInvisibles(clientVisibility);
	}
}
