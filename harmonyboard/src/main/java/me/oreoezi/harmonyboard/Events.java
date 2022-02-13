package me.oreoezi.harmonyboard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.oreoezi.harmonyboard.api.HarmonyBoard;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class Events implements Listener {
    public Events() {

    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        HarmonyBoard.instance.getPlayerList().addPlayer(event.getPlayer());
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        HarmonyPlayer player = HarmonyBoard.instance.getPlayerList().getPlayer(event.getPlayer());
        if (player == null) return;
        player.destroy();
    }
    @EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event) {
        HarmonyPlayer hplayer = HarmonyBoard.instance.getPlayerList().getPlayer(event.getPlayer());
        if (hplayer != null) hplayer.destroy();
        HarmonyBoard.instance.getPlayerList().addPlayer(event.getPlayer());
    }
}
