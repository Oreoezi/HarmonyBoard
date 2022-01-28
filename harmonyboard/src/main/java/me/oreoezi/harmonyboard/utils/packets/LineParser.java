package me.oreoezi.harmonyboard.utils.packets;

import org.bukkit.ChatColor;

public class LineParser {
	public static String[] splitLine(String text) {
		text = ChatColor.translateAlternateColorCodes('&', text);
		String prefix = text.substring(0, text.length()/2);
		String suffix = "";
		if (prefix.endsWith("§")) {
			prefix = prefix.substring(0, prefix.length()-1);
			suffix += "§";
			suffix += text.substring(text.length()/2, text.length());
		}
		else {
			suffix += ChatColor.getLastColors(prefix);
			suffix += text.substring(text.length()/2, text.length());
		}
		return new String[] {prefix, suffix};
	}
}
