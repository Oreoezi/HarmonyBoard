package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.ClassType;

public class ScoreboardScore {
    private Object sbs;
    private Class<?> scoreboardScoreClass = NMSUtils.getNMSClass("ScoreboardScore", ClassType.Scoreboard);
    public Object getRaw() {
        return sbs;
    }
    public ScoreboardScore(Scoreboard scoreboard, ScoreboardObjective objective, String text) {
        try {
            sbs = scoreboardScoreClass.getDeclaredConstructor(Scoreboard.scoreboardClass, ScoreboardObjective.scoreboardObjectiveClass, String.class).
            newInstance(scoreboard.getRaw(), objective.getRaw(), text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setScore(int score) {
        try {
            sbs.getClass().getMethod("setScore", int.class).invoke(sbs, score);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
