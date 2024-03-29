package me.oreoezi.harmonyboard.api;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.oreoezi.harmonyboard.ThreadMain;
import me.oreoezi.harmonyboard.command.CommandManager;
import me.oreoezi.harmonyboard.datamanagers.Configs;
import me.oreoezi.harmonyboard.datamanagers.Database;
import me.oreoezi.harmonyboard.events.EventSystem;
import me.oreoezi.harmonyboard.metrics.Tracking;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class HarmonyBoard {
    public static HarmonyBoard instance;
    private JavaPlugin main;
    private Configs configs;
    private Database database;
    private ThreadMain threadmain;
    private CommandManager commandmanager;
    private PlayerList playerlist;
    private PlaceholderList placeholderlist;
    private AnimationList animationlist;

    public HarmonyBoard(JavaPlugin main) {
        instance = this;
        this.main = main;
        init();
        Tracking.init(main);
    }
    public Configs getConfigs() {
        return configs;
    }
    public PlayerList getPlayerList() {
        return playerlist;
    }
    public Database getDatabaseInstance() {
        return database;
    }
    public PlaceholderList getPlaceholderList() {
        return placeholderlist;
    }
    public AnimationList getAnimationList() {
        return animationlist;
    }
    public void init() {
        EventSystem.destroy(main);
        if (threadmain != null) threadmain.cancel();
        animationlist = new AnimationList();
        try {
            configs = new Configs(main);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean hasEvents = configs.getConfig().getBoolean("event_based_scoreboards");
        playerlist = new PlayerList(main.getServer().getPluginManager().getPlugin("Oraxen") != null, 
        configs.getConfig().getBoolean("save_scoreboard_preferences"));
        placeholderlist = new PlaceholderList();
        commandmanager = new CommandManager();
        if (configs.getConfig().getBoolean("save_scoreboard_preferences")) {
            //For now the database only saves scoreboard toggles
            if (configs.getConfig().getBoolean("mysql.enable")) {
                String host = configs.getConfig().getString("mysql.host");
				String port = configs.getConfig().getString("mysql.port");
				String user = configs.getConfig().getString("mysql.username");	
				String pass = configs.getConfig().getString("mysql.password");
				String db_name = configs.getConfig().getString("mysql.database");
                database = new Database(host,port,user,pass,db_name);
            }
            else database = new Database(main.getDataFolder().getAbsolutePath() + "/database.sql");
            database.runQuery("CREATE TABLE IF NOT EXISTS toggle_off (uuid VARCHAR(256))");
        }
        for (Player player : main.getServer().getOnlinePlayers()) {
            HarmonyPlayer hplayer = new HarmonyPlayer(player);
            HarmonyBoard.instance.getPlayerList().addPlayerWithScoreboard(hplayer);
        }   
        EventSystem.init(main, hasEvents);
        boolean hasPAPI = main.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null; 
        boolean updateTitles = configs.getConfig().getBoolean("allow_placeholders_in_title");
        int update_rate = configs.getConfig().getInt("scoreboard_update_rate");
        threadmain = new ThreadMain(hasPAPI, updateTitles, hasEvents, update_rate);
        threadmain.runTaskTimerAsynchronously(main, update_rate, update_rate);
        main.getCommand("harmonyboard").setExecutor(commandmanager);
        main.getCommand("harmonyboard").setTabCompleter(commandmanager);
    }
}
