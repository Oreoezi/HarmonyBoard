package me.oreoezi.harmonyboard.command.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.App;
import me.oreoezi.harmonyboard.datamanagers.ScoreboardTemplate;
import me.oreoezi.harmonyboard.utils.ConfigUtils;
import me.oreoezi.harmonyboard.utils.HarmonyCommand;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class ScoreboardCommand extends HarmonyCommand {
    private App main;
    public ScoreboardCommand(App main) {
        super(main);
        this.main = main;
    }
    @Override
    public boolean onExec(CommandSender player, String[] args) {
		ConfigUtils configutils = main.getConfigUtils();
		if (args.length < 3) {
			player.sendMessage(configutils.getMessage("error.invalid_arguments"));
			return true;
		}
        switch(args[1]) {
            case "remove": {
				HarmonyPlayer target = main.getPlayerList().getPlayer(args[2]);
				if (target == null) {
					player.sendMessage(configutils.getMessage("error.invalid_player"));
					return true;
				}
				target.destroy();
				player.sendMessage(configutils.getMessage("admin.scoreboard_removed"));
				break;
			}
			case "set": {
				if (args.length < 4) {
					player.sendMessage(configutils.getMessage("error.invalid_arguments"));
					return true;
				}
				Player tgplayer = Bukkit.getPlayer(args[2]);
				if (tgplayer == null) {
					player.sendMessage(configutils.getMessage("error.invalid_player"));
					return true;
				}
				ScoreboardTemplate template = null;
				for (int i=0;i<main.getConfigs().scoreboards.size()&&template==null;i++) {
					if (!main.getConfigs().scoreboards.get(i).getName().equals(args[3])) continue;
					template = main.getConfigs().scoreboards.get(i);
				}
				if (template == null) {
					player.sendMessage(configutils.getMessage("error.invalid_scoreboard"));
					return true;
				}
				HarmonyPlayer target = main.getPlayerList().getPlayer(args[2]);
				if (target != null) target.destroy();
				HarmonyPlayer hplayer = new HarmonyPlayer(tgplayer); 
            	hplayer.setPreset(template.getTitle(), template.getPreset());
            	hplayer.getScoreboard().create();
            	main.getPlayerList().addPlayer(hplayer);
				player.sendMessage(configutils.getMessage("admin.scoreboard_set"));
				break;
			}
			default: {
				player.sendMessage(configutils.getMessage("error.invalid_arguments"));
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
	public ArrayList<ArrayList<String>> getTabComplete() {
		ArrayList<ArrayList<String>> args = new ArrayList<ArrayList<String>>();
		ArrayList<String> modes = new ArrayList<String>() {{add("set");add("remove");}};
		args.add(modes);
		ArrayList<String> players = new ArrayList<String>();
		for (Player plr : Bukkit.getOnlinePlayers()) 
			players.add(plr.getName());
		args.add(players);
		ArrayList<String> scoreboards = new ArrayList<String>();
		for (int i=0;i<main.getConfigs().scoreboards.size();i++) 
			scoreboards.add(main.getConfigs().scoreboards.get(i).getName());
		args.add(scoreboards);
		return args;
	}
}
