package me.oreoezi.harmonyboard.placeholders;


import me.oreoezi.harmonyboard.datamanagers.HarmonyPlaceholder;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class Health extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "health";
	}
	@Override
	public String getValue(HarmonyPlayer player) {
		return String.valueOf(player.getPlayer().getHealth());
	}

}
