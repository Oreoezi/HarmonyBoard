package me.oreoezi.harmonyboard.placeholders;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.utils.HarmonyPlaceholder;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class Ping extends HarmonyPlaceholder{
	private int getPing(Player player) {
		//String version = Bukkit.getServer().getVersion();
		try {
			int version = Integer.valueOf(Bukkit.getServer().getClass().getPackage().getName().split("v1_")[1].split("_")[0]);
			if (version < 17) {
				Object craftplayer = player.getClass().getMethod("getHandle").invoke(player);
				return (int) craftplayer.getClass().getField("ping").get(craftplayer);
			}
			return player.getPing();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public String getName() {
		return "ping";
	}
	@Override
	public String getValue(HarmonyPlayer player) {
		return String.valueOf(getPing(player.getPlayer()));
	}
}
