package me.oreoezi.harmonyboard.placeholders;

import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.datamanagers.HarmonyPlaceholder;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils;

public class Ping extends HarmonyPlaceholder {
	private int getPing(Player player) {
		try {
			if (NMSUtils.versionId < 17) {
				Object craftplayer = NMSUtils.getHandle.invoke(player);
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
