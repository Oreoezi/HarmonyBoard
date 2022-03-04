package me.oreoezi.harmonyboard.placeholders;


import me.oreoezi.harmonyboard.datamanagers.HarmonyPlaceholder;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class PlayerName extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "name";
	}
	@Override
	public String getValue(HarmonyPlayer player) {
		return player.getPlayer().getName();
	}
}
