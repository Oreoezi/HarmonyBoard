package me.oreoezi.harmonyboard.command.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.api.HarmonyBoard;
import me.oreoezi.harmonyboard.datamanagers.ScoreboardTemplate;
import me.oreoezi.harmonyboard.utils.HarmonyCommand;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class ScoreboardCommand extends HarmonyCommand {
    @Override
    public boolean onExec(CommandSender player, String[] args) {
		if (args.length < 3) {
			player.sendMessage(HarmonyBoard.instance.getConfigs().getMessage("error.invalid_arguments"));
			return true;
		}
        switch(args[1]) {
            case "remove": {
				HarmonyPlayer target = HarmonyBoard.instance.getPlayerList().getPlayer(args[2]);
				if (target == null) {
					player.sendMessage(HarmonyBoard.instance.getConfigs().getMessage("error.invalid_player"));
					return true;
				}
				target.destroy();
				player.sendMessage(HarmonyBoard.instance.getConfigs().getMessage("admin.scoreboard_removed"));
				break;
			}
			case "set": {
				if (args.length < 4) {
					player.sendMessage(HarmonyBoard.instance.getConfigs().getMessage("error.invalid_arguments"));
					return true;
				}
				Player tgplayer = Bukkit.getPlayer(args[2]);
				if (tgplayer == null) {
					player.sendMessage(HarmonyBoard.instance.getConfigs().getMessage("error.invalid_player"));
					return true;
				}
				ScoreboardTemplate template = HarmonyBoard.instance.getConfigs().getScoreboardTemplate(args[3]);
				if (template == null) {
					player.sendMessage(HarmonyBoard.instance.getConfigs().getMessage("error.invalid_scoreboard"));
					return true;
				}
				HarmonyPlayer target = HarmonyBoard.instance.getPlayerList().getPlayer(args[2]);
				if (target != null) target.destroy();
				HarmonyPlayer hplayer = new HarmonyPlayer(tgplayer); 
            	hplayer.setPreset(template.getTitle(), template.getPreset());
            	hplayer.getScoreboard().create();
            	HarmonyBoard.instance.getPlayerList().addPlayer(hplayer);
				player.sendMessage(HarmonyBoard.instance.getConfigs().getMessage("admin.scoreboard_set"));
				break;
			}
			default: {
				player.sendMessage(HarmonyBoard.instance.getConfigs().getMessage("error.invalid_arguments"));
			}
        }
		return true;
    }
    @Override
	public String getName() {
		return "scoreboard";
	}
	@Override
	public String getPermission() {
		return "harmonyboard.scoreboard";
	}
	@Override
	public String getDescription() {
		return "Change the scoreboard of a player.";
	}
	@Override
	public String getArgs() {
		return " (set/remove) (player) (scoreboard)";
	}
	@Override
	public String[][] getTabComplete() {
		String[][] args = new String[3][];
		args[0] = new String[]{"set", "remove"};
		int index = 0;
		args[1] = new String[Bukkit.getOnlinePlayers().size()];
		for (Player p : Bukkit.getOnlinePlayers())
			args[1][index++] = p.getName();
		ArrayList<ScoreboardTemplate> templates = HarmonyBoard.instance.getConfigs().getScoreboards();
		args[2] = new String[templates.size()];
		for (int i=0;i<templates.size();i++)
			args[2][i] = templates.get(i).getName();
		return args;
	}
}
