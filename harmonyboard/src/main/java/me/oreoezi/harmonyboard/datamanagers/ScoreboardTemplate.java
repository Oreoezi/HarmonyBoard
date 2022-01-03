package me.oreoezi.harmonyboard.datamanagers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ScoreboardTemplate {
    private String name;
    private String permission;
    private String[] worlds;
    private String[] preset;
    private String title;
    public ScoreboardTemplate(String name, FileConfiguration config) {
        this.name = name;
        this.title = config.getString("title");
        this.preset = config.getList("lines").toArray(new String[0]);
        if (config.getList("conditions.worlds") != null)
            worlds = config.getList("conditions.worlds").toArray(new String[0]);
        else worlds = new String[0];
        permission = config.getString("conditions.permission");
    }
    public String getName() {
        return this.name;
    }
    public String[] getPreset() {
        return preset;
    }
    public String getTitle() {
        return title;
    }
    /**
     * Checks if the player matches with the scoreboard conditions.
     * @param player Player checked for eligibility.
     * @return Whether the player is eligibile.
     */
    public boolean isMatching(Player player) {
        if (!hasWorld(player))
            return false;
        if (permission != null && !player.hasPermission(permission))
            return false;
        return true;
    }
    private boolean hasWorld(Player player) {
        if (worlds.length < 1) return true;
        String worldname = player.getWorld().getName();
        for (int i=0;i<worlds.length;i++)
            if (worlds[i].equals(worldname)) return true;
        return false;
    }
    public boolean isDefault() {
        return worlds.length < 1 && permission == null;
    }
}
