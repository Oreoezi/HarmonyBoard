package me.oreoezi.harmonyboard.events;

import org.bukkit.plugin.Plugin;

import me.oreoezi.harmonyboard.events.implementations.EventMcMMO;
import me.oreoezi.harmonyboard.events.implementations.EventScoreboards;
import me.oreoezi.harmonyboard.events.implementations.Events;

public class EventSystem {
    private static Events events;
    private static EventScoreboards eventScoreboards;
    private static EventMcMMO eventMcMMO;
    
    public EventSystem(Plugin main, boolean hasEvents) {
        if (events != null) return;
        events = new Events();
        main.getServer().getPluginManager().registerEvents(events, main);
        if (!hasEvents) return;
        eventScoreboards = new EventScoreboards();
        main.getServer().getPluginManager().registerEvents(eventScoreboards, main);
        if (main.getServer().getPluginManager().getPlugin("mcMMO") != null) {
            eventMcMMO = new EventMcMMO();
            main.getServer().getPluginManager().registerEvents(eventMcMMO, main);
        }
    }
}
