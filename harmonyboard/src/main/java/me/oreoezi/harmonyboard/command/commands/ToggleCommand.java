package me.oreoezi.harmonyboard.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.App;
import me.oreoezi.harmonyboard.utils.ConfigUtils;
import me.oreoezi.harmonyboard.utils.HarmonyCommand;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class ToggleCommand extends HarmonyCommand {
    private App main;
    public ToggleCommand(App main) {
        super(main);
        this.main = main;
    }
    @Override
    public boolean onExec(CommandSender sender, String[] args) {
        ConfigUtils configutils = main.getConfigUtils();
        if (!main.getConfigs().getConfig("config").getBoolean("save_scoreboard_preferences")) {
            sender.sendMessage(configutils.getMessage("error.toggle_disabled"));
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(configutils.getMessage("error.nonplayer"));
            return true;
        }
        Player player = (Player) sender;
        HarmonyPlayer target = main.getPlayerList().getPlayer(player);
        if (target == null) {
            main.getDatabaseInstance().runQuery("DELETE FROM toggle_off WHERE uuid='" + player.getUniqueId().toString() + "'");
            main.addPlayer(player);
            sender.sendMessage(configutils.getMessage("player.toggle_on"));
        }
        else {
            target.destroy();
            main.getDatabaseInstance().runQuery("INSERT INTO toggle_off (uuid) VALUES ('" + player.getUniqueId().toString() + "')");
            sender.sendMessage(configutils.getMessage("player.toggle_off"));
        }
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

