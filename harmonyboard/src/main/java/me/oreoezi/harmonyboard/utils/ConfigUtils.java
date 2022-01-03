package me.oreoezi.harmonyboard.utils;

import org.bukkit.ChatColor;

import me.oreoezi.harmonyboard.datamanagers.Configs;

public class ConfigUtils {
    private Configs configs;
	public ConfigUtils(Configs configs) {
		this.configs = configs;
	} 
	public String getMessage(String index) {
		if (configs.getConfig("language").getString("messages." + index) != null)
		    return ChatColor.translateAlternateColorCodes('&', configs.getConfig("language").getString("prefix") + " " + configs.getConfig("language").getString("messages." + index));
        return "";
	}
	public String getMessageNoPrefix(String index) {
		if (configs.getConfig("language").getString("messages." + index) != null)
			return ChatColor.translateAlternateColorCodes('&', configs.getConfig("language").getString("messages." + index));
		return "";
	}
}
