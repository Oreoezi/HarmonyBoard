package me.oreoezi.harmonyboard.api;

import org.bukkit.Bukkit;

import me.oreoezi.harmonyboard.App;

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
}
