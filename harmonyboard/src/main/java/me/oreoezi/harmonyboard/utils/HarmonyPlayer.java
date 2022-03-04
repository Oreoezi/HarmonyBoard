package me.oreoezi.harmonyboard.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.api.HarmonyBoard;
import me.oreoezi.harmonyboard.events.EventEnum;
import me.oreoezi.harmonyboard.events.EventTimestamp;
import me.oreoezi.harmonyboard.utils.packets.versions.*;

public class HarmonyPlayer {
    private Player player;
    private HarmonyScoreboard scoreboard;
    private String title = "";
    private String[] preset = {};
    private boolean remove = false;
    private ArrayList<EventTimestamp> events;
    public HarmonyPlayer(Player player) {
        this.player = player;
        create();
        this.events = new ArrayList<EventTimestamp>();
    }
    public Player getPlayer() {
        return player;
    }
    /**
     * Use this method to remove a player's scoreboard.
     * Removing them from the player list manually will cause errors.
     */
    public void destroy() {
        remove = true;
        scoreboard.destroy();
    }
    /**
     * Only used in the thread to know whether to remove the player from the list.
     * @return 
     */
    public boolean shouldRemove() {
        return remove;
    }
    /**
     * Sets the player's scoreboard. Should be called before creating the scoreboard.
     * @param preset Each line is an element of the array.
     */
    public void setPreset(String[] preset) {
        this.preset = preset;
    }
    /**
     * Method to set both the title and preset.
     * @param title Scoreboard title.
     * @param preset Scoreboard preset.
     */
    public void setPreset(String title, String[] preset) {
        this.title = title;
        this.preset = preset;
    }
    public String[] getPreset() {
        return preset;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public HarmonyScoreboard getScoreboard() {
        return scoreboard;
    }
    public void registerEvent(EventEnum event) {
        if (!HarmonyBoard.instance.getConfigs().isEventEnabled(event)) return;
        EventTimestamp ets = new EventTimestamp(event);
        if (unregisterEvent(event) == null) {
            getScoreboard().destroy();
            create();
            events.add(ets);
            HarmonyBoard.instance.getPlayerList().addPlayerWithScoreboard(this);
        }
        else events.add(ets);
    }
    public EventTimestamp unregisterEvent(EventEnum event) {
        for (int i=0;i<events.size();i++)
            if (events.get(i).getEvent() == event) return events.remove(i);
        return null;
    }
    public EventTimestamp getEvent(EventEnum event) {
        for (int i=0;i<events.size();i++) {
            if (events.get(i).getEvent() == event) return events.get(i);
        }
        return null;
    }
    public ArrayList<EventTimestamp> getEvents() {
        return events;
    }
    public void create() {
        int version = Integer.valueOf(Bukkit.getServer().getClass().getPackage().getName().split("v1_")[1].split("_")[0]);
        if (version < 17) scoreboard = new ScoreboardLegacy(this);
        else if (version < 18) scoreboard = new ScoreboardUtopic(this);
        else scoreboard = new ScoreboardLmao(this);
    }
}
