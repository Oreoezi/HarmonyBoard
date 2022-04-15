package me.oreoezi.harmonyboard.placeholders;

import org.bukkit.Bukkit;

import me.oreoezi.harmonyboard.datamanagers.HarmonyPlaceholder;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class OnlinePlayers extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "online";
	}
	@Override
	public String getValue(HarmonyPlayer player) {
		return String.valueOf(Bukkit.getOnlinePlayers().size());
	}
}
