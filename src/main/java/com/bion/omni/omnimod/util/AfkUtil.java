package com.bion.omni.omnimod.util;

public interface AfkUtil {
    int omni$getActiveTicks();
    void omni$setActiveTicks(int ticks);
    void omni$changeActiveTicks(int ticks);
    int omni$getPrevActiveDay();
    void omni$setPrevActiveDay(int day);
    void omni$setGotReward(boolean gotReward);
    boolean omni$getGotReward();
    void omni$setStreak(int streak);
    int omni$getStreak();
    void omni$setNextStreakDay(int day);
    int omni$getNextStreakDay();
    void omni$setNextStreakYear(int year);
    int omni$getNextStreakYear();

    CustomChar getChar(CustomCharDict.CharName p_name);
}
