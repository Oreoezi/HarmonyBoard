package me.oreoezi.harmonyboard.command.commands;


import org.bukkit.command.CommandSender;

import me.oreoezi.harmonyboard.api.HarmonyBoard;
import me.oreoezi.harmonyboard.api.PlayerList;
import me.oreoezi.harmonyboard.utils.HarmonyCommand;

public class ReloadCommand extends HarmonyCommand {
    @Override
    public boolean onExec(CommandSender player, String[] args) {
        PlayerList playerlist = HarmonyBoard.instance.getPlayerList();
        for (int i=0;i<playerlist.size();i++) {
            playerlist.getPlayer(i).destroy();
        }
        HarmonyBoard.instance.init();
        HarmonyBoard.instance.getConfigs().sendMessage(player, "admin.reloaded");
        return true;
    }
    public String getName() {
		return "reload";
	}
	public String getPermission() {
		return "harmonyboard.reload";
	}
	public String getDescription() {
		return "Reloads the configs.";
	}
}
