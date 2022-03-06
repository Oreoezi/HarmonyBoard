package me.oreoezi.harmonyboard.utils.packets;

import net.md_5.bungee.api.ChatColor;

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
			String last_col = "";
			for (int i=prefix.length()-1;i>=0;i--) {
				if (prefix.charAt(i) != '§') continue;
				last_col = prefix.charAt(i) + "" + prefix.charAt(i+1);
			}
			suffix += last_col;
			suffix += text.substring(text.length()/2, text.length());
		}
		return new String[] {prefix, suffix};
	}
}
