package me.oreoezi.harmonyboard.datamanagers;
import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.events.EventEnum;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class ScoreboardTemplate {
    private String name;
    private EventEnum[] events;
    private String[] permissions;
    private String[] worlds;
    private String[] preset;
    private String title;
    /**
     * @param name Name of the scoreboard
     * @param title Scoreboard title
     * @param lines Scoreboard lines
     */
    public ScoreboardTemplate(String name, String title, String[] lines) {
        this.name = name;
        this.title = title;
        this.preset = lines;
        this.worlds = new String[0];
        this.permissions = new String[0];
        this.events = new EventEnum[0];
    }
    /**
     * Checks if the player matches with the scoreboard conditions.
     * @param player Player checked for eligibility.
     * @return Whether the player is eligibile.
     */
    public boolean isMatching(HarmonyPlayer player) {
        if (!hasWorld(player.getPlayer()))
            return false;
        if (!hasPermissions(player.getPlayer()))
            return false;
        if (!hasEvent(player))
            return false;
        return true;
    }
    private boolean hasPermissions(Player player) {
        for (int i=0;i<permissions.length;i++) {
            if (!player.hasPermission(permissions[i])) return false;
        }
        return true;
    }
    private boolean hasWorld(Player player) {
        if (worlds.length < 1) return true;
        String worldname = player.getWorld().getName();
        for (int i=0;i<worlds.length;i++)
            if (worlds[i].equals(worldname)) return true;
        return false;
    }
    private boolean hasEvent(HarmonyPlayer hplayer) {
        for (int i=0;i<events.length;i++) {
            if (hplayer.getEvent(events[i]) != null) return true;
        }
        return false; 
    }
    public boolean isDefault() {
        return worlds.length < 1 && permissions.length < 1 && events.length < 1;
    }
    public EventEnum[] getEvents() {
        return events;
    }
    public String getName() {
        return name;
    }
    public String getTitle() {
        return title;
    }
    public String[] getPreset() {
        return preset;
    }
    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }
    public void setWorlds(String[] worlds) {
        this.worlds = worlds;
    }
    public void setEvents(EventEnum[] events) {
        this.events = events;
    }
}
