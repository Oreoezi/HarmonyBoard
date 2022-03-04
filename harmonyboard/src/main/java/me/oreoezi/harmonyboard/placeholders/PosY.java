package me.oreoezi.harmonyboard.placeholders;


import me.oreoezi.harmonyboard.datamanagers.HarmonyPlaceholder;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class PosY extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "posy";
	}
	@Override
	public String getValue(HarmonyPlayer player) {
		return String.valueOf(Math.floor(player.getPlayer().getLocation().getY()*10)/10);
	}

}
