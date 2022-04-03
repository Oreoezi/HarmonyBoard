package me.oreoezi.harmonyboard.command.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.api.HarmonyBoard;
import me.oreoezi.harmonyboard.datamanagers.Configs;
import me.oreoezi.harmonyboard.datamanagers.ScoreboardTemplate;
import me.oreoezi.harmonyboard.utils.HarmonyCommand;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class ScoreboardCommand extends HarmonyCommand {
    @Override
    public boolean onExec(CommandSender player, String[] args) {
		Configs configs = HarmonyBoard.instance.getConfigs();
		if (args.length < 3) {
			configs.sendMessage(player, "error.invalid_arguments");
			return true;
		}
        switch(args[1]) {
            case "remove": {
				HarmonyPlayer target = HarmonyBoard.instance.getPlayerList().getPlayer(args[2]);
				if (target == null) {
					configs.sendMessage(player, "error.invalid_player");
					return true;
				}
				target.destroy();
				configs.sendMessage(player, "admin.scoreboard_removed");
				break;
			}
			case "set": {
				if (args.length < 4) {
					configs.sendMessage(player, "error.invalid_arguments");
					return true;
				}
				Player tgplayer = Bukkit.getPlayer(args[2]);
				if (tgplayer == null) {
					configs.sendMessage(player, "error.invalid_player");
					return true;
				}
				ScoreboardTemplate template = HarmonyBoard.instance.getConfigs().getScoreboardTemplate(args[3]);
				if (template == null) {
					configs.sendMessage(player, "error.invalid_scoreboard");
					return true;
				}
				HarmonyPlayer target = HarmonyBoard.instance.getPlayerList().getPlayer(args[2]);
				if (target != null) target.destroy();
				HarmonyPlayer hplayer = new HarmonyPlayer(tgplayer); 
            	hplayer.setPreset(template.getTitle(), template.getPreset());
            	hplayer.getScoreboard().create();
            	HarmonyBoard.instance.getPlayerList().addPlayer(hplayer);
				configs.sendMessage(player, "admin.scoreboard_set");
				break;
			}
			default: {
				configs.sendMessage(player, "error.invalid_arguments");
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
		ScoreboardTemplate[] templates = HarmonyBoard.instance.getConfigs().getScoreboards();
		args[2] = new String[templates.length];
		for (int i=0;i<templates.length;i++)
			args[2][i] = templates[i].getName();
		return args;
	}
}
