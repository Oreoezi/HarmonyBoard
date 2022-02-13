package me.oreoezi.harmonyboard.placeholders;


import me.oreoezi.harmonyboard.utils.HarmonyPlaceholder;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class PlayerName extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "player";
	}
	@Override
	public String getValue(HarmonyPlayer player) {
		return player.getPlayer().getName();
	}
}
