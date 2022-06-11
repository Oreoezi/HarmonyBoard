package me.oreoezi.harmonyboard.utils.packets.implementations;

import java.util.HashSet;
import java.util.Set;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.ReflectionUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.ClassType;

public class ScoreboardTeam {
    private Object team;
    private Class<?> scoreboardTeamClass = NMSUtils.getNMSClass("ScoreboardTeam", ClassType.Scoreboard);
    public ScoreboardTeam(Scoreboard scoreboard, String name) {
        try {
            team = scoreboardTeamClass.getDeclaredConstructor(scoreboard.getRaw().getClass(), String.class).
            newInstance(scoreboard.getRaw(), name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setDisplayName(String name) {
        try {
            team.getClass().getMethod("setDisplayName", String.class).invoke(team, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setDisplayName(ChatBaseComponent name) {
        try {
            team.getClass().getMethod("setDisplayName", IChatBaseComponent.chatBaseComponentInterface).invoke(team, name.getRaw());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setPrefix(String prefix) {
        try {
            team.getClass().getMethod("setPrefix", String.class).invoke(team, prefix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setSuffix(String prefix) {
        try {
            team.getClass().getMethod("setSuffix", String.class).invoke(team, prefix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setPrefix(ChatBaseComponent name) {
        try {
            team.getClass().getMethod("setPrefix", IChatBaseComponent.chatBaseComponentInterface).invoke(team, name.getRaw());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setSuffix(ChatBaseComponent name) {
        try {
            team.getClass().getMethod("setSuffix", IChatBaseComponent.chatBaseComponentInterface).invoke(team, name.getRaw());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setNameset(String name) {
        Set<String> nameset = new HashSet<String>();
        nameset.add(name);
        team = ReflectionUtils.setValue(team, NMSUtils.versionId < 17 ? "c" : "f", nameset);
    }
    public Object getRaw() {
        return team;
    }
}
