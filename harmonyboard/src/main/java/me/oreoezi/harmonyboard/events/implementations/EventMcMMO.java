package me.oreoezi.harmonyboard.events.implementations;

import com.gmail.nossr50.events.experience.McMMOPlayerLevelDownEvent;
import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.oreoezi.harmonyboard.api.HarmonyBoard;
import me.oreoezi.harmonyboard.events.EventEnum;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class EventMcMMO implements Listener {
    @EventHandler
    public void onPlayerLevelUp(McMMOPlayerLevelUpEvent event) {
        HarmonyPlayer player = HarmonyBoard.instance.getPlayerList().getPlayer(event.getPlayer());
        player.registerEvent(EventEnum.MCMMO_LEVELUP);
    }
    @EventHandler
    public void onPlayerLevelDown(McMMOPlayerLevelDownEvent event) {
        HarmonyPlayer player = HarmonyBoard.instance.getPlayerList().getPlayer(event.getPlayer());
        player.registerEvent(EventEnum.MCMMO_LEVELDOWN);
    }
}
