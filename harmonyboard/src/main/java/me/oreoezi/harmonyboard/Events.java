package me.oreoezi.harmonyboard;

import java.util.Map;

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
    private boolean hasOraxen = false;
    public Events(App main) {
        this.main = main;
        if (Bukkit.getPluginManager().getPlugin("Oraxen") != null) hasOraxen = true;
        for (Player player : Bukkit.getOnlinePlayers()) {
            addPlayer(player);
        }
    }
    private void initPlayer(ScoreboardTemplate sb_template, Player player) {
        HarmonyPlayer hplayer = new HarmonyPlayer(player); 
        String title = sb_template.getTitle();
        String[] lines = sb_template.getPreset();
        if (hasOraxen) {
            Map<String, io.th0rgal.oraxen.font.Glyph> glyphs = io.th0rgal.oraxen.OraxenPlugin.get().getFontManager().getGlyphByPlaceholderMap();
            for (String key : glyphs.keySet()) {
                title = title.replace("<glyph:" + glyphs.get(key).getName() + ">", glyphs.get(key).getCharacter() + "");
                for (int i=0;i<lines.length;i++) 
                    lines[i] = lines[i].replace("<glyph:" + glyphs.get(key).getName() + ">", glyphs.get(key).getCharacter() + "");
            }
        }
        hplayer.setPreset(title, lines);
        hplayer.getScoreboard().create();
        main.getPlayerList().addPlayer(hplayer);
    }
    public void addPlayer(Player player) {
        if (main.getConfigs().getConfig("config").getBoolean("save_scoreboard_preferences")) 
            if (main.getDatabaseInstance().executeQuery("SELECT * FROM toggle_off WHERE uuid='" + player.getUniqueId().toString() + "' LIMIT 1").size() > 0) return;
        boolean foundsb = false;
        for (int i=0;i<main.getConfigs().scoreboards.size()&&!foundsb;i++) {
            if (main.getConfigs().scoreboards.get(i).isDefault()) continue; //priority for nondefault
            if (!main.getConfigs().scoreboards.get(i).isMatching(player)) continue;
            ScoreboardTemplate sb_template = main.getConfigs().scoreboards.get(i);
            initPlayer(sb_template, player);
            foundsb = true;
        }
        if (!foundsb) { //really messy code but I'll fix it sometime
            for (int i=0;i<main.getConfigs().scoreboards.size()&&!foundsb;i++) {
                if (!main.getConfigs().scoreboards.get(i).isDefault()) continue;
                ScoreboardTemplate sb_template = main.getConfigs().scoreboards.get(i);
                initPlayer(sb_template, player);
                foundsb = true;
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
