package me.oreoezi.harmonyboard;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.oreoezi.harmonyboard.api.AnimationList;
import me.oreoezi.harmonyboard.api.PlaceholderList;
import me.oreoezi.harmonyboard.api.PlayerList;
import me.oreoezi.harmonyboard.command.CommandManager;
import me.oreoezi.harmonyboard.datamanagers.Configs;
import me.oreoezi.harmonyboard.datamanagers.Database;
import me.oreoezi.harmonyboard.utils.ConfigUtils;

public class App extends JavaPlugin {
    private Configs configs;
    private Database database;
    private Events events;
    private ThreadMain threadmain;
    private CommandManager commandmanager;
    private PlayerList playerlist;
    private PlaceholderList placeholderlist;
    private AnimationList animationlist;
    private ConfigUtils configutils;
    @Override
    public void onEnable() {
        init();
    }
    public ConfigUtils getConfigUtils() {
        return configutils;
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
    public void addPlayer(Player player) {
        events.addPlayer(player);
    }
    public AnimationList getAnimationList() {
        return animationlist;
    }
    public void init() {
        if (threadmain != null) threadmain.cancel();
        configs = new Configs(this);
        configutils = new ConfigUtils(configs);
        playerlist = new PlayerList();
        placeholderlist = new PlaceholderList();
        animationlist = new AnimationList(this);
        commandmanager = new CommandManager(this);
        if (configs.getConfig("config").getBoolean("save_scoreboard_preferences")) {
            //For now the database only saves scoreboard toggles
            if (configs.getConfig("config").getBoolean("mysql.enable")) {
                String host = configs.getConfig("config").getString("mysql.host");
				String port = configs.getConfig("config").getString("mysql.port");
				String user = configs.getConfig("config").getString("mysql.username");	
				String pass = configs.getConfig("config").getString("mysql.password");
				String db_name = configs.getConfig("config").getString("mysql.database");
                database = new Database(host,port,user,pass,db_name);
            }
            else database = new Database(this.getDataFolder() + "/database.sql");
            database.runQuery("CREATE TABLE IF NOT EXISTS toggle_off (uuid VARCHAR(256))");
        }
        events = new Events(this);
        getServer().getPluginManager().registerEvents(events, this);

        threadmain = new ThreadMain(this);
        threadmain.runTaskTimerAsynchronously(this, configs.getConfig("config").getInt("scoreboard_update_rate"), configs.getConfig("config").getInt("scoreboard_update_rate"));
        getCommand("harmonyboard").setExecutor(commandmanager);
        getCommand("harmonyboard").setTabCompleter(commandmanager);
    }
}
