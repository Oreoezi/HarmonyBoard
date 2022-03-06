package me.oreoezi.harmonyboard.events.implementations;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.oreoezi.harmonyboard.api.HarmonyBoard;
import me.oreoezi.harmonyboard.events.EventEnum;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;
public class Events implements Listener {
    private boolean hasEvents;
    public Events(boolean hasEvents) {
        this.hasEvents = hasEvents;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        HarmonyPlayer hplayer = new HarmonyPlayer(event.getPlayer());
        if (hasEvents) {
            hplayer.getScoreboard().create();
            if (event.getPlayer().hasPlayedBefore()) hplayer.registerEvent(EventEnum.JOINED);
            else hplayer.registerEvent(EventEnum.FIRST_JOINED);
        }
        else HarmonyBoard.instance.getPlayerList().addPlayerWithScoreboard(hplayer);
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
        if (hplayer == null) return;
        if (hasEvents && event.getPlayer().hasPlayedBefore()) {
            hplayer.registerEvent(EventEnum.CHANGED_WORLD);
        }
        else {
            hplayer.getScoreboard().destroy();
            HarmonyBoard.instance.getPlayerList().addPlayerWithScoreboard(hplayer);
        }
    }
}
