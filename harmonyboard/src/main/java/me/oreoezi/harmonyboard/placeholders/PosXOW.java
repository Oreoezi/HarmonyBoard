package me.oreoezi.harmonyboard.placeholders;

import org.bukkit.World.Environment;

import me.oreoezi.harmonyboard.api.HarmonyPlaceholder;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class PosXOW extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "posxow";
	}
	@Override
	public String getValue(HarmonyPlayer player) {
		if (player.getPlayer().getWorld().getEnvironment().equals(Environment.NETHER)) {
			return String.valueOf(Math.floor(player.getPlayer().getLocation().getX() * 8));
		}
		return String.valueOf(Math.floor(player.getPlayer().getLocation().getX()*10)/10);
	}
}
