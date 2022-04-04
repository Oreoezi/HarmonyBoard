package me.oreoezi.harmonyboard.datamanagers;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import me.oreoezi.harmonyboard.api.HarmonyBoard;
import me.oreoezi.harmonyboard.events.EventEnum;

public class Configs {
    private FileConfiguration config;
    private HashMap<String, FileConfiguration> text;
    private FileConfiguration events;
    private ScoreboardTemplate[] scoreboards;
    private String default_lang;
    private String prefix;
    public Configs(Plugin plugin) throws IOException {
        String folder = plugin.getDataFolder().getAbsolutePath();
        text = new HashMap<String, FileConfiguration>();
        File sb_folder = new File(folder + "/Scoreboards");
    	File an_folder = new File(folder + "/Animations");
        File lang_folder = new File(folder + "/Language");
        if (!sb_folder.exists()) {
            String[] scoreboards = {"attack_dono", "attack", "default", "donator", "end_dono", "end"};
            for (int i=0;i<scoreboards.length;i++)
                createConfig(plugin, folder + "/Scoreboards/" + scoreboards[i], "scoreboards/" + scoreboards[i]);
        }
        if (!an_folder.exists()) {
            an_folder.mkdir();
            createConfig(plugin, folder + "/Animations/default", "example_animation");
        }
        if (!lang_folder.exists()) {
            lang_folder.mkdir();
            String[] locales = {"en_us", "ja_jp", "ro_ro", "de_de", "zh_tw"};
            for (int i=0;i<locales.length;i++)
                createConfig(plugin, folder + "/Language/" + locales[i], "language/" + locales[i]);
        }
        config = createConfig(plugin, folder + "/config", "config");
        FileConfiguration lg_file = createConfig(plugin, folder + "/language", "language");
        prefix = lg_file.getString("prefix");
        default_lang = lg_file.getString("default");
        events = createEventsConfig(plugin, folder + "/events");
        String[] scoreboards = sb_folder.list();
        this.scoreboards = new ScoreboardTemplate[scoreboards.length];
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
            this.scoreboards[i] = template;
    	}
        for (int i=0;i<this.scoreboards.length-1;i++) { 
            //this looks horrible but I will fix later
            //that later never came
            for (int j=i+1;j<this.scoreboards.length;j++) {
                if (this.scoreboards[i].conditions() >= this.scoreboards[j].conditions()) continue;
                ScoreboardTemplate aux = this.scoreboards[i];
                this.scoreboards[i] = this.scoreboards[j];
                this.scoreboards[j] = aux;
            }
        }
        String[] animations = an_folder.list();
        for (int i=0;i<animations.length;i++) {
            FileConfiguration animation = loadConfig(folder + "/Animations/" + animations[i]);
            HarmonyAnimation anim = new HarmonyAnimation(animations[i].replace(".yml", ""), 
            animation.getInt("delay"), animation.getList("lines").toArray(new String[0]));
            HarmonyBoard.instance.getAnimationList().addAnimation(anim);
    	}
        String[] languages = lang_folder.list();
        for (int i=0;i<languages.length;i++) {
            FileConfiguration lang = loadConfig(folder + "/Language/" + languages[i]);
            text.put(languages[i], lang);
        }
    }
    public ScoreboardTemplate[] getScoreboards() {
        return scoreboards;
    }
    public ScoreboardTemplate getScoreboardTemplate(String name) {
        for (int i=0;i<scoreboards.length;i++) 
            if (scoreboards[i].getName().equals(name)) return scoreboards[i];
        return null;
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
    private FileConfiguration createEventsConfig(Plugin plugin, String path) throws IOException {
        File config_file = new File(path + ".yml");
        FileConfiguration file_config = (FileConfiguration)YamlConfiguration.loadConfiguration(config_file);
        EventEnum[] enums = EventEnum.values();
        for (int i=0;i<enums.length;i++) {
            if (file_config.contains(enums[i].toString())) continue;
            file_config.set(enums[i].toString()+".time", 10);
            file_config.set(enums[i].toString()+".enabled", true);
        }
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
	public boolean sendMessage(CommandSender sender, String index) {
        if (sender instanceof Player)
		{
            Player player = (Player) sender;
            String message = text.getOrDefault(player.getLocale() + ".yml", text.get(default_lang + ".yml")).getString("messages." + index);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
            return true;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + text.get(default_lang).getString(index)));
        return true;
	}
    public boolean isEventEnabled(EventEnum event) {
        return events.getBoolean(event.toString() + ".enabled");
    }
    public int getEventTime(EventEnum event) {
        return events.getInt(event.toString() + ".time");
    }
}
