package me.oreoezi.harmonyboard.placeholders;

import org.bukkit.World.Environment;

import me.oreoezi.harmonyboard.utils.HarmonyPlaceholder;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class PosZOW extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "poszow";
	}
	@Override
	public String getValue(HarmonyPlayer player) {
		if (player.getPlayer().getWorld().getEnvironment().equals(Environment.NETHER)) {
			return String.valueOf(Math.floor(player.getPlayer().getLocation().getZ() *10)/10 * 8);
		}
		return String.valueOf(Math.floor(player.getPlayer().getLocation().getZ()*10)/10);
	}

}
