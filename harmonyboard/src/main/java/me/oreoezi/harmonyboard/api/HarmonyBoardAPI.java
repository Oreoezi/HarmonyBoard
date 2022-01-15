package me.oreoezi.harmonyboard.api;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import me.oreoezi.harmonyboard.App;
import me.oreoezi.harmonyboard.datamanagers.ScoreboardTemplate;

public class HarmonyBoardAPI {
    App main;
    public HarmonyBoardAPI() {
        main = (App) Bukkit.getServer().getPluginManager().getPlugin("HarmonyScoreboard");
    }
    public PlaceholderList getPlaceholderList() {
        return main.getPlaceholderList();
    }
    public PlayerList getPlayerList() {
        return main.getPlayerList();
    }
    public ScoreboardTemplate getScoreboardTemplate(String name) {
        for (int i=0;i<main.getConfigs().scoreboards.size();i++) 
            if (main.getConfigs().scoreboards.get(i).getName().equals(name)) return main.getConfigs().scoreboards.get(i);
        return null;
    }
    public ArrayList<ScoreboardTemplate> getScoreboardList() {
        return main.getConfigs().scoreboards;
    }
    public AnimationList getAnimationList() {
        return main.getAnimationList();
    }
}
