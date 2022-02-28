package me.oreoezi.harmonyboard;
import java.util.ArrayList;

import org.bukkit.scheduler.BukkitRunnable;
import me.oreoezi.harmonyboard.api.AnimationList;
import me.oreoezi.harmonyboard.api.HarmonyBoard;
import me.oreoezi.harmonyboard.api.PlaceholderList;
import me.oreoezi.harmonyboard.events.EventTimestamp;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class ThreadMain extends BukkitRunnable {
    private boolean hasPAPI;
    private boolean updateTitle;
    private boolean hasEvents;
    private AnimationList anims;
    private int delay;
    public ThreadMain(boolean hasPAPI, boolean updateTitle, boolean hasEvents, int delay) {
        anims = HarmonyBoard.instance.getAnimationList();
        this.delay = delay;
        this.updateTitle = updateTitle;
        this.hasPAPI = hasPAPI;
        this.hasEvents = hasEvents;
    }
    @Override
	public void run() {
        anims.tick(delay);
        for (int i=0;i<HarmonyBoard.instance.getPlayerList().size();i++) {
            HarmonyPlayer player = HarmonyBoard.instance.getPlayerList().getPlayer(i);
            if (player.shouldRemove()) {
                HarmonyBoard.instance.getPlayerList().removePlayer(player);
                i--;
                continue;
            }
            if (hasEvents) {
                ArrayList<EventTimestamp> events = player.getEvents();
                boolean shouldChange = false;
                for (int j=0;j<events.size()&&!shouldChange;j++) {
                    if (events.get(j).getDate() > System.currentTimeMillis() - HarmonyBoard.instance.getConfigs().
                    getEventTime(events.get(j).getEvent())) 
                        continue;
                    player.getScoreboard().destroy();
                    player.getEvents().remove(j);
                    player.create();
                    HarmonyBoard.instance.getPlayerList().addPlayerWithScoreboard(player);
                    shouldChange = true;
                }
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
        PlaceholderList list = HarmonyBoard.instance.getPlaceholderList();
        for (int i=0;i<list.size();i++) {
            if (!line.contains("%"+ list.getPlaceholder(i).getName()+"%")) continue;
            line = line.replaceAll("%"+ list.getPlaceholder(i).getName()+"%", list.getPlaceholder(i).getValue(player));
        }
		return line;
	}
}
