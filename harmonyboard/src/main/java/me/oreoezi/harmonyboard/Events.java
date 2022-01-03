package me.oreoezi.harmonyboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.oreoezi.harmonyboard.datamanagers.ScoreboardTemplate;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class Events implements Listener {
    private App main;
    public Events(App main) {
        this.main = main;
        for (Player player : Bukkit.getOnlinePlayers()) {
            addPlayer(player);
        }
    }
    public void addPlayer(Player player) {
        if (main.getConfigs().getConfig("config").getBoolean("save_scoreboard_preferences")) 
            if (main.getDatabaseInstance().executeQuery("SELECT * FROM toggle_off WHERE uuid='" + player.getUniqueId().toString() + "' LIMIT 1").size() > 0) return;
        HarmonyPlayer hplayer = null;
        for (int i=0;i<main.getConfigs().scoreboards.size();i++) {
            if (main.getConfigs().scoreboards.get(i).isDefault()) continue; //priority for nondefault
            if (!main.getConfigs().scoreboards.get(i).isMatching(player)) continue;
            ScoreboardTemplate sb_template = main.getConfigs().scoreboards.get(i);
            hplayer = new HarmonyPlayer(player); 
            hplayer.setPreset(sb_template.getTitle(), sb_template.getPreset());
            hplayer.getScoreboard().create();
            main.getPlayerList().addPlayer(hplayer);
            break;
        }
        if (hplayer == null) { //really messy code but I'll fix it sometime
            for (int i=0;i<main.getConfigs().scoreboards.size();i++) {
                if (!main.getConfigs().scoreboards.get(i).isDefault()) continue;
                ScoreboardTemplate sb_template = main.getConfigs().scoreboards.get(i);
                hplayer = new HarmonyPlayer(player); 
                hplayer.setPreset(sb_template.getTitle(), sb_template.getPreset());
                hplayer.getScoreboard().create();
                main.getPlayerList().addPlayer(hplayer);
                break;
            }
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        addPlayer(event.getPlayer());
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        HarmonyPlayer player = main.getPlayerList().getPlayer(event.getPlayer());
        if (player == null) return;
        player.destroy();
        main.getPlayerList().removePlayer(player);
    }
    @EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event) {
        HarmonyPlayer hplayer = main.getPlayerList().getPlayer(event.getPlayer());
        if (hplayer != null) hplayer.destroy();
        new BukkitRunnable() {
			@Override
			public void run() {
                addPlayer(event.getPlayer());
            }
        }.runTaskLater(main, 4L);
    }
}
