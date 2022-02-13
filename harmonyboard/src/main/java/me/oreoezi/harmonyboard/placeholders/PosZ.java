package me.oreoezi.harmonyboard.placeholders;

import me.oreoezi.harmonyboard.utils.HarmonyPlaceholder;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class PosZ extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "posz";
	}
	@Override
	public String getValue(HarmonyPlayer player) {
		return String.valueOf(Math.floor(player.getPlayer().getLocation().getZ()*10)/10);
	}
}
