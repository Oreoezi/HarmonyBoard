package me.oreoezi.harmonyboard.utils.packets.implementations;

import java.lang.reflect.Method;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.IChatBaseComponent;

public class Scoreboard {
    private Object scoreboard;
    public ScoreboardObjective registerObjective(String name, Object criteria) {
        try {
            Method register = NMSUtils.NMSScoreboard.getMethod("registerObjective", String.class, NMSUtils.NMSIScoreboardCriteria);
            return new ScoreboardObjective(register.invoke(scoreboard, name, criteria));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ScoreboardObjective registerObjective(String name, Object criteria, IChatBaseComponent displayName, Object display) {
        try {
            Method register = NMSUtils.NMSScoreboard.getMethod("registerObjective", String.class, criteria.getClass(), NMSUtils.NMSIChatBaseComponent, display.getClass());
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
            scoreboard = NMSUtils.NMSScoreboard.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}