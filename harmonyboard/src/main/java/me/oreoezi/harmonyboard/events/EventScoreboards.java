package me.oreoezi.harmonyboard.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.oreoezi.harmonyboard.api.HarmonyBoard;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class EventScoreboards implements Listener {
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        HarmonyPlayer hplayer = HarmonyBoard.instance.getPlayerList().getPlayer((Player) event.getDamager());
        if (hplayer == null) return;
        hplayer.destroy();
        if (event.getEntity() instanceof Player) hplayer.registerEvent(EventEnum.COMBAT);
        else hplayer.registerEvent(EventEnum.ATTACK);
    }
}
