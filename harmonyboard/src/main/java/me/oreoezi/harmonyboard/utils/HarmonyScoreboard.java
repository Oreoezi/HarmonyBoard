package me.oreoezi.harmonyboard.utils;

public class HarmonyScoreboard {
    String[] linecache;
    String titlecache;
    HarmonyPlayer player;
    public HarmonyScoreboard(HarmonyPlayer player) {
        this.player = player;
    }
    public boolean setLine(int pos, String text) {
        if (linecache == null || linecache.length < pos) {
            linecache = new String[player.getPreset().length];
            for (int i=0;i<player.getPreset().length;i++) 
            {
                linecache[i] = "";
            } 
            return false;    
        }
        if (text.equals(linecache[pos-1])) return false;
        setLineRaw(pos, text, linecache[pos-1].equals(""));
        linecache[pos-1] = text;
        return true;
    }
    public void setLineRaw(int pos, String text, boolean create) {

    }
    public boolean setTitle(String title) {
        if (titlecache == null) {
            titlecache = "";
            titlecache = player.getTitle();
        }
        if (title.equals(titlecache)) return false;
        titlecache = title;
        setTitleRaw(title);
    	return true;
    }
    public void setTitleRaw(String title) {

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
