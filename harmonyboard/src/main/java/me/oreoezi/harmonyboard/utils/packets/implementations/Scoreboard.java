package me.oreoezi.harmonyboard.utils.packets.implementations;

import java.lang.reflect.Method;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.ClassType;

public class Scoreboard {
    private Object scoreboard;
    public static Class<?> scoreboardClass = NMSUtils.getNMSClass("Scoreboard", ClassType.Scoreboard);
    public ScoreboardObjective registerObjective(String name, Object criteria) {
        try {
            Method register = scoreboardClass.getMethod("registerObjective", String.class, IScoreboardCriteria.scoreboardCriteriaInterface);
            return new ScoreboardObjective(register.invoke(scoreboard, name, criteria));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ScoreboardObjective registerObjective(String name, Object criteria, ChatBaseComponent displayName, Object display) {
        try {
            Method register = scoreboardClass.getMethod("registerObjective", String.class, criteria.getClass(), IChatBaseComponent.chatBaseComponentInterface, display.getClass());
            return new ScoreboardObjective(register.invoke(scoreboard, name, criteria, displayName.getRaw(), display));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Object getRaw() {
        return scoreboard;
    }
    public Scoreboard() {
        try {
            scoreboard = scoreboardClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}