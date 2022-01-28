package me.oreoezi.harmonyboard;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import me.oreoezi.harmonyboard.api.AnimationList;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class ThreadMain extends BukkitRunnable {
    private App main;
    private boolean hasPAPI = false;
    private boolean updateTitle = false;
    private AnimationList anims;
    private int delay;
    public ThreadMain(App main) {
        this.main = main;
        anims = main.getAnimationList();
        delay = main.getConfigs().getConfig("config").getInt("scoreboard_update_rate");
        updateTitle = main.getConfigs().getConfig("config").getBoolean("allow_placeholders_in_title");
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) hasPAPI = true;
    }
    @Override
	public void run() {
        anims.tick(delay);
        for (int i=0;i<main.getPlayerList().size();i++) {
            HarmonyPlayer player = main.getPlayerList().getPlayer(i);
            if (player.shouldRemove()) {
                main.getPlayerList().removePlayer(player);
                i--;
                continue;
            }
            if (updateTitle) player.getScoreboard().setTitle(parseLine(player.getTitle(), player));
            for (int j=0;j<player.getPreset().length;j++) {
                player.getScoreboard().setLine(player.getPreset().length-j, parseLine(player.getPreset()[j], player));
            }
        }
    }
    private String parseLine(String line, HarmonyPlayer player) {
        if (hasPAPI) line = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player.getPlayer(), line);
        for (int i=0;i<anims.size();i++) {
            if(!line.contains("a%" + anims.get(i).getName() + "%a")) continue;
            line = line.replaceAll("a%" + anims.get(i).getName() + "%a", anims.get(i).getCurrentFrame());
        }
        for (int i=0;i<main.getPlaceholderList().size();i++) {
            if (!line.contains("%"+ main.getPlaceholderList().getPlaceholder(i).getName()+"%")) continue;
            line = line.replaceAll("%"+ main.getPlaceholderList().getPlaceholder(i).getName()+"%", main.getPlaceholderList().getPlaceholder(i).getValue(player));
        }
		return line;
	}
}
