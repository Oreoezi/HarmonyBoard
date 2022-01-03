package me.oreoezi.harmonyboard.api;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class PlayerList {
    private ArrayList<HarmonyPlayer> playerlist;
    public PlayerList() {
        playerlist = new ArrayList<HarmonyPlayer>();
    }
    public void addPlayer(HarmonyPlayer player) {
        playerlist.add(player);
    }
    public HarmonyPlayer getPlayer(Player player) {
        for (int i=0;i<playerlist.size();i++) {
            if (playerlist.get(i).getPlayer().getName().equals(player.getName())) return playerlist.get(i);
        }
        return null;
    }
    public HarmonyPlayer getPlayer(String name) {
        for (int i=0;i<playerlist.size();i++) {
            if (playerlist.get(i).getPlayer().getName().equals(name)) return playerlist.get(i);
        }
        return null;
    }
    public HarmonyPlayer getPlayer(int index) {
        return playerlist.get(index);
    }
    public int size() {
        return playerlist.size();
    }
    /**
     * Do not use this method unless you want the thread to error out.
     * Use HarmonyPlayer#destroy instead.
     * @param player
     */
    public void removePlayer(HarmonyPlayer player) {
        playerlist.remove(player);
    }
}
