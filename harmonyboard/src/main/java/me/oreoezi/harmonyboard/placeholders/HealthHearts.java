package me.oreoezi.harmonyboard.placeholders;


import me.oreoezi.harmonyboard.api.HarmonyPlaceholder;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class HealthHearts extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "health_hearts";
	}
	@Override
	public String getValue(HarmonyPlayer player) {
		String out = "";
		for (int i=0;i<player.getPlayer().getHealth()/2;i++) {
			out += "❤";
		}
		if (player.getPlayer().getHealth() % 2 == 1) out += "♡";
		return out;
	}
}
