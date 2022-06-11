package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.ClassType;

public class ScoreboardObjective {
    private Object objective;
    public static Class<?> scoreboardObjectiveClass = NMSUtils.getNMSClass("ScoreboardObjective", ClassType.Scoreboard);
    public Object getRaw() {
        return objective;
    }
    public void setDisplayName(String name) {
        try {
            objective.getClass().getMethod("setDisplayName", String.class).invoke(objective, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setDisplayName(ChatBaseComponent name) {
        try {
            objective.getClass().getMethod("setDisplayName", IChatBaseComponent.chatBaseComponentInterface).invoke(objective, name.getRaw());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ScoreboardObjective(Object objective) {
        this.objective = objective;
    }
}
