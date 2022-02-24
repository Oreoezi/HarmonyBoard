package me.oreoezi.harmonyboard.metrics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.oreoezi.harmonyboard.api.HarmonyBoard;

public class Tracking {
    public static void init(JavaPlugin plugin) {
        Metrics metrics = new Metrics(plugin, 11249);
        metrics.addCustomChart(new Metrics.SimplePie("database_type", () -> {
            FileConfiguration config = HarmonyBoard.instance.getConfigs().getConfig();
            if (config.getBoolean("save_scoreboard_preferences")) {
                if (config.getBoolean("mysql.enable"))
                    return "mysql";
                return "sqlite";
            }
            return "none";
        }));
    }
}
