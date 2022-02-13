package me.oreoezi.harmonyboard;
import org.bukkit.plugin.java.JavaPlugin;

import me.oreoezi.harmonyboard.api.HarmonyBoard;


public class App extends JavaPlugin {
    @Override
    public void onEnable() {
        new HarmonyBoard(this);
    }
}
