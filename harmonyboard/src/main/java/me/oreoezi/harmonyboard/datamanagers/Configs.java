package me.oreoezi.harmonyboard.datamanagers;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


public class Configs {
    private Plugin plugin;
    private HashMap<String, FileConfiguration> configs = new HashMap<String, FileConfiguration>();
    public ArrayList<ScoreboardTemplate> scoreboards = new ArrayList<ScoreboardTemplate>();
	public HashMap<String, FileConfiguration> animations = new HashMap<String, FileConfiguration>();

    public Configs(Plugin plugin) {
        this.plugin = plugin;
        try {
            createSubdirectories();
            createDefaults();
            getScoreboards();
            getAnimations();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void createSubdirectories() throws IOException {
        String folder = plugin.getDataFolder().getAbsolutePath();
    	Path sbpath = Paths.get(folder + "/Scoreboards");
    	Path anpath = Paths.get(folder + "/Animations");
        if (!Files.exists(sbpath)) Files.createDirectories(sbpath);
        if (!Files.exists(anpath)) Files.createDirectories(anpath);
    }
    private void createDefaults() throws IOException {
        String folder = plugin.getDataFolder().getAbsolutePath();
    	Path scoreboard_path = Paths.get(folder + "/Scoreboards/default.yml");
        Path scoreboard_don_path = Paths.get(folder + "/Scoreboards/donator.yml");
    	Path animation_path = Paths.get(folder + "/Animations/default.yml");
        if (!Files.exists(scoreboard_path)) {
            Files.createFile(scoreboard_path);
            File config_file = new File(folder + "/Scoreboards/default.yml");
            FileConfiguration file_config = (FileConfiguration)YamlConfiguration.loadConfiguration(config_file);
            Reader config_defaults = new InputStreamReader(plugin.getResource("example_scoreboard.yml"), "UTF-8");
            file_config.setDefaults((Configuration)YamlConfiguration.loadConfiguration(config_defaults));
            file_config.options().copyDefaults(true);
            file_config.save(config_file);
        }
        if (!Files.exists(scoreboard_don_path)) {
            Files.createFile(scoreboard_don_path);
            File config_file = new File(folder + "/Scoreboards/donator.yml");
            FileConfiguration file_config = (FileConfiguration)YamlConfiguration.loadConfiguration(config_file);
            Reader config_defaults = new InputStreamReader(plugin.getResource("donator_scoreboard.yml"), "UTF-8");
            file_config.setDefaults((Configuration)YamlConfiguration.loadConfiguration(config_defaults));
            file_config.options().copyDefaults(true);
            file_config.save(config_file);
        }
        if (!Files.exists(animation_path)) {
            Files.createFile(animation_path);
            File config_file = new File(folder + "/Animations/default.yml");
            FileConfiguration file_config = (FileConfiguration)YamlConfiguration.loadConfiguration(config_file);
            Reader config_defaults = new InputStreamReader(plugin.getResource("example_animation.yml"), "UTF-8");
            file_config.setDefaults((Configuration)YamlConfiguration.loadConfiguration(config_defaults));
            file_config.options().copyDefaults(true);
            file_config.save(config_file);
        }
        String[] configs = new String[] {"config", "language"}; //Just in case I'll have more in the future
        for (int i=0;i<configs.length; i++) {
            File config_file = new File(plugin.getDataFolder() + "/" + configs[i] + ".yml");
            FileConfiguration file_config = (FileConfiguration)YamlConfiguration.loadConfiguration(config_file);
            if (!config_file.exists()) {
                Reader config_defaults = new InputStreamReader(plugin.getResource(String.valueOf(configs[i]) + ".yml"), "UTF-8");
                file_config.setDefaults((Configuration)YamlConfiguration.loadConfiguration(config_defaults));
                file_config.options().copyDefaults(true);
                file_config.save(config_file);
            }
            this.configs.put(configs[i], file_config);
        }
    }
    private void getScoreboards() {
    	File sb_folder = new File(plugin.getDataFolder()+"/Scoreboards");
    	String[] scoreboards = sb_folder.list();
    	for (int i=0;i<scoreboards.length;i++) {
            File config_file = new File(plugin.getDataFolder() + "/Scoreboards/" + scoreboards[i]);
            FileConfiguration file_config = (FileConfiguration)YamlConfiguration.loadConfiguration(config_file);
            this.scoreboards.add(new ScoreboardTemplate(scoreboards[i].replace(".yml", ""), file_config));
    	}
    }
    private void getAnimations() {
    	File an_folder = new File(plugin.getDataFolder()+"/Animations");
    	String[] animations = an_folder.list();
    	for (int i=0;i<animations.length;i++) {
            File config_file = new File(plugin.getDataFolder() + "/Animations/" + animations[i]);
            FileConfiguration file_config = (FileConfiguration)YamlConfiguration.loadConfiguration(config_file);
            this.animations.put(animations[i].replace(".yml", ""), file_config);
    	}
    }
    public FileConfiguration getConfig(String config_name) {
        return this.configs.get(config_name);
    }
    public FileConfiguration getAnimation(String animation_name) {
   	 return this.animations.get(animation_name);
    }
    public String getMessage(String path) {
       return ChatColor.translateAlternateColorCodes('&', getConfig("language").getString(path));
    }
}
