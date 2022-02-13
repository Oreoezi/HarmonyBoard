package me.oreoezi.harmonyboard.datamanagers;
import org.bukkit.entity.Player;

public class ScoreboardTemplate {
    private String name;
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
    }
    /**
     * Checks if the player matches with the scoreboard conditions.
     * @param player Player checked for eligibility.
     * @return Whether the player is eligibile.
     */
    public boolean isMatching(Player player) {
        if (!hasWorld(player))
            return false;
        if (!hasPermissions(player))
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
    public boolean isDefault() {
        return worlds.length < 1 && permissions.length < 1;
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
}
