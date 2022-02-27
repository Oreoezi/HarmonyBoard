package me.oreoezi.harmonyboard.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.api.HarmonyBoard;
import me.oreoezi.harmonyboard.utils.HarmonyCommand;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class ToggleCommand extends HarmonyCommand {
    @Override
    public boolean onExec(CommandSender sender, String[] args) {
        if (!HarmonyBoard.instance.getConfigs().getConfig().getBoolean("save_scoreboard_preferences")) {
            sender.sendMessage(HarmonyBoard.instance.getConfigs().getMessage("error.toggle_disabled"));
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(HarmonyBoard.instance.getConfigs().getMessage("error.nonplayer"));
            return true;
        }
        Player player = (Player) sender;
        HarmonyPlayer target = HarmonyBoard.instance.getPlayerList().getPlayer(player);
        if (target == null) {
            HarmonyBoard.instance.getDatabaseInstance().runQuery("DELETE FROM toggle_off WHERE uuid='" + player.getUniqueId().toString() + "'");
            HarmonyBoard.instance.getPlayerList().addPlayer(HarmonyBoard.instance.getPlayerList().getPlayer(player));
            sender.sendMessage(HarmonyBoard.instance.getConfigs().getMessage("player.toggle_on"));
            return true;
        }
        target.destroy();
        HarmonyBoard.instance.getDatabaseInstance().runQuery("INSERT INTO toggle_off (uuid) VALUES ('" + player.getUniqueId().toString() + "')");
        sender.sendMessage(HarmonyBoard.instance.getConfigs().getMessage("player.toggle_off"));
        return true;
    }
    public String getName() {
		return "toggle";
	}
	public String getPermission() {
		return "harmonyboard.toggle";
	}
	public String getDescription() {
		return "Toggles your scoreboard.";
	}
}

