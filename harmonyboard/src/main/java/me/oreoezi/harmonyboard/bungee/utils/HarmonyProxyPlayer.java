package me.oreoezi.harmonyboard.bungee.utils;

import me.oreoezi.harmonyboard.utils.packets.versions.ScoreboardBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class HarmonyProxyPlayer {
    private ProxiedPlayer player;
    private ScoreboardBungee scoreboard;
    public HarmonyProxyPlayer(ProxiedPlayer player) {
        this.player = player;
        create();
    }
    public ScoreboardBungee getScoreboard() {
        return scoreboard;
    }
    public ProxiedPlayer getPlayer() {
        return player;
    }
    public void create() {
        this.scoreboard = new ScoreboardBungee(this);
    }
}
