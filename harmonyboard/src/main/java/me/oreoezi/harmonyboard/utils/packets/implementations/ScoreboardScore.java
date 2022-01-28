package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;

public class ScoreboardScore {
    private Object sbs;
    public Object getRaw() {
        return sbs;
    }
    public ScoreboardScore(Scoreboard scoreboard, ScoreboardObjective objective, String text) {
        try {
            Class<?> sb = NMSUtils.NMSScoreboard;
            Class<?> sb_obj = NMSUtils.NMSScoreboardObjective;
            sbs = NMSUtils.NMSScoreboardScore.getDeclaredConstructor(sb, sb_obj, String.class).newInstance(scoreboard.getRaw(), objective.getRaw(), text);
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
