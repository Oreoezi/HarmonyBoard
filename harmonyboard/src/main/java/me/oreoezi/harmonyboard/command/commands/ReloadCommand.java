package me.oreoezi.harmonyboard.command.commands;


import org.bukkit.command.CommandSender;

import me.oreoezi.harmonyboard.App;
import me.oreoezi.harmonyboard.api.PlayerList;
import me.oreoezi.harmonyboard.utils.HarmonyCommand;

public class ReloadCommand extends HarmonyCommand {
    private App main;
    public ReloadCommand(App main) {
        super(main);
        this.main = main;
    }
    @Override
    public boolean onExec(CommandSender player, String[] args) {
        PlayerList playerlist = main.getPlayerList();
        for (int i=0;i<playerlist.size();i++) {
            playerlist.getPlayer(i).destroy();
        }
        main.init();
        player.sendMessage(main.getConfigUtils().getMessage("admin.reloaded"));
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
