package me.oreoezi.harmonyboard.api;

import java.util.ArrayList;

import me.oreoezi.harmonyboard.datamanagers.ScoreboardTemplate;

public class HarmonyBoardAPI {
    public HarmonyBoardAPI() {
        
    }
    /**
     * @deprecated
     */
    public PlaceholderList getPlaceholderList() {
        return HarmonyBoard.instance.getPlaceholderList();
    }
    /**
     * @deprecated
     */
    public PlayerList getPlayerList() {
        return HarmonyBoard.instance.getPlayerList();
    }
    /**
     * @deprecated
     */
    public ArrayList<ScoreboardTemplate> getScoreboardList() {
        return HarmonyBoard.instance.getConfigs().getScoreboards();
    }
    /**
     * @deprecated
     */
    public AnimationList getAnimationList() {
        return HarmonyBoard.instance.getAnimationList();
    }
    /**
     * @deprecated
     */
    public ScoreboardTemplate getScoreboardTemplate(String name) {
        return HarmonyBoard.instance.getConfigs().getScoreboardTemplate(name);
    }
}
