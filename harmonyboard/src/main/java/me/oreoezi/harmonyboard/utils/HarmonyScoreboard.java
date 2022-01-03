package me.oreoezi.harmonyboard.utils;

import java.util.HashMap;

public class HarmonyScoreboard {
    private HashMap<Integer, String> lines;
    public HarmonyScoreboard(HarmonyPlayer player) {
        lines = new HashMap<Integer, String>();
    }
    public void setLine(int pos, String text) {
        setLineRaw(pos, text, lines.get(pos) == null);
        lines.put(pos, text);
    }
    public void setLineRaw(int pos, String text, boolean create) {

    }
    public void setTitle(String title) {
    	
    }
    /**
     * This method should be run before the player is added to the player list.
     * Not doing so might cause the thread to update a nonexistent scoreboard, causing errors.
     */
    public void create() {

    }
    /**
     * Use HarmonyPlayer#destroy 
     * instead so the player gets removed from the player list by the thread.
     */
    public void destroy() {

    }
}
