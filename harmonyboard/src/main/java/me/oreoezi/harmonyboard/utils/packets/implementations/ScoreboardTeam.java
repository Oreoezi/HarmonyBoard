package me.oreoezi.harmonyboard.utils.packets.implementations;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.ReflectionUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.IChatBaseComponent;

public class ScoreboardTeam {
    private Object team;
    public ScoreboardTeam(Scoreboard scoreboard, String name) {
        try {
            Constructor<?> constructor = NMSUtils.ScoreboardTeam.getDeclaredConstructor(scoreboard.getRaw().getClass(), String.class);
            team = constructor.newInstance(scoreboard.getRaw(), name);
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
    public void setDisplayName(IChatBaseComponent name) {
        try {
            team.getClass().getMethod("setDisplayName", NMSUtils.NMSIChatBaseComponent).invoke(team, name.getRaw());
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
    public void setPrefix(IChatBaseComponent name) {
        try {
            team.getClass().getMethod("setPrefix", NMSUtils.NMSIChatBaseComponent).invoke(team, name.getRaw());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setSuffix(IChatBaseComponent name) {
        try {
            team.getClass().getMethod("setSuffix", NMSUtils.NMSIChatBaseComponent).invoke(team, name.getRaw());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setNameset(String name) {
        Set<String> nameset = new HashSet<String>();
        nameset.add(name);
        team = ReflectionUtils.setValue(team, "c", nameset);
    }
    public Object getRaw() {
        return team;
    }
}
