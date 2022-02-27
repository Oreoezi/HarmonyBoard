package me.oreoezi.harmonyboard.datamanagers;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import me.oreoezi.harmonyboard.api.HarmonyBoard;
import me.oreoezi.harmonyboard.events.EventEnum;


public class Configs {
    private FileConfiguration config;
    private FileConfiguration text;
    private ArrayList<ScoreboardTemplate> scoreboards;
	private ArrayList<HarmonyAnimation> animations;
    public Configs(Plugin plugin) throws IOException {
        scoreboards = new ArrayList<ScoreboardTemplate>();
        animations = new ArrayList<HarmonyAnimation>();
        String folder = plugin.getDataFolder().getAbsolutePath();
        File sb_folder = new File(folder + "/Scoreboards");
    	File an_folder = new File(folder + "/Animations");
        if (!sb_folder.exists()) {
            sb_folder.mkdir();
            createConfig(plugin, folder + "/Scoreboards/default", "example_scoreboard");
            createConfig(plugin, folder + "/Scoreboards/donator", "donator_scoreboard");
        }
        if (!an_folder.exists()) {
            an_folder.mkdir();
            createConfig(plugin, folder + "/Animations/default", "example_animation");
        }
        config = createConfig(plugin, folder + "/config", "config");
        text = createConfig(plugin, folder + "/language", "language");
        String[] scoreboards = sb_folder.list();
    	for (int i=0;i<scoreboards.length;i++) {
            FileConfiguration scoreboard = loadConfig(folder + "/Scoreboards/" + scoreboards[i]);
            String title = scoreboard.getString("title");
            String[] lines = scoreboard.getList("lines").toArray(new String[0]);
            ScoreboardTemplate template = new ScoreboardTemplate(scoreboards[i].replace(".yml", ""), title, lines);
            if (scoreboard.getList("conditions.permissions") != null) template.setPermissions(scoreboard.getList("conditions.permissions").toArray(new String[0]));
            if (scoreboard.getList("conditions.worlds") != null) template.setWorlds(scoreboard.getList("conditions.worlds").toArray(new String[0]));
            if (scoreboard.getList("conditions.events") != null) {
                    String[] eventstr = scoreboard.getList("conditions.events").toArray(new String[0]);
                    EventEnum[] events = new EventEnum[eventstr.length];
                    for (int j=0;j<eventstr.length;j++) {
                        events[j] = EventEnum.valueOf(eventstr[j]);
                    }
                    template.setEvents(events);
            }
            this.scoreboards.add(template);
    	}
        String[] animations = an_folder.list();
        for (int i=0;i<animations.length;i++) {
            FileConfiguration animation = loadConfig(folder + "/Animations/" + animations[i]);
            HarmonyAnimation anim = new HarmonyAnimation(animations[i].replace(".yml", ""), 
            animation.getInt("delay"), animation.getList("lines").toArray(new String[0]));
            HarmonyBoard.instance.getAnimationList().addAnimation(anim);
    	}
    }
    public ArrayList<ScoreboardTemplate> getScoreboards() {
        return scoreboards;
    }
    public ScoreboardTemplate getScoreboardTemplate(String name) {
        for (int i=0;i<scoreboards.size();i++) 
            if (scoreboards.get(i).getName().equals(name)) return scoreboards.get(i);
        return null;
    }
    public ArrayList<HarmonyAnimation> getAnimations() {
        return animations;
    }
    private FileConfiguration createConfig(Plugin plugin, String path, String config_name) throws IOException {
        File config_file = new File(path + ".yml");
        FileConfiguration file_config = (FileConfiguration)YamlConfiguration.loadConfiguration(config_file);
        Reader config_defaults = new InputStreamReader(plugin.getResource(config_name + ".yml"), "UTF-8");
        file_config.setDefaults((Configuration)YamlConfiguration.loadConfiguration(config_defaults));
        file_config.options().copyDefaults(true);
        file_config.save(config_file);
        return file_config;
    }
    private FileConfiguration loadConfig(String path) {
        File config_file = new File(path);
        FileConfiguration file_config = (FileConfiguration)YamlConfiguration.loadConfiguration(config_file);
        return file_config;
    }
    public FileConfiguration getConfig() {
        return config;
    }
	public String getMessage(String index) {
		if (text.getString("messages." + index) != null)
		    return ChatColor.translateAlternateColorCodes('&', text.getString("prefix") + " " + text.getString("messages." + index));
        return "";
	}
	public String getMessageNoPrefix(String index) {
		if (text.getString("messages." + index) != null)
			return ChatColor.translateAlternateColorCodes('&', text.getString("messages." + index));
		return "";
	}
}
