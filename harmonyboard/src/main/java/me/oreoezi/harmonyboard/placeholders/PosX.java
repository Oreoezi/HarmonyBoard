package me.oreoezi.harmonyboard.placeholders;


import me.oreoezi.harmonyboard.utils.HarmonyPlaceholder;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class PosX extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "posx";
	}
	@Override
	public String getValue(HarmonyPlayer player) {
		return String.valueOf(Math.floor(player.getPlayer().getLocation().getX()*10)/10);
	}
}
