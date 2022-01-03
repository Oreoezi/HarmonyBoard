package me.oreoezi.harmonyboard.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.oreoezi.harmonyboard.App;
import me.oreoezi.harmonyboard.command.commands.ReloadCommand;
import me.oreoezi.harmonyboard.command.commands.ScoreboardCommand;
import me.oreoezi.harmonyboard.command.commands.ToggleCommand;
import me.oreoezi.harmonyboard.utils.ConfigUtils;
import me.oreoezi.harmonyboard.utils.HarmonyCommand;

public class CommandManager implements CommandExecutor, TabCompleter {
    private ArrayList<HarmonyCommand> commands;
    private ConfigUtils configutils;
    public CommandManager(App main) {
        this.configutils = main.getConfigUtils();
        commands = new ArrayList<HarmonyCommand>();
        commands.add(new ReloadCommand(main));
        commands.add(new ScoreboardCommand(main));
        commands.add(new ToggleCommand(main));
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1 || args[0] == "help") {
            commands.forEach(cmd -> {
                sender.sendMessage("§b/hboard " + cmd.getName() + " " + cmd.getArgs() + " | " + cmd.getDescription());
            });
        }
        else {
            for (int i=0;i<commands.size();i++) {
                if (!commands.get(i).getName().equals(args[0].toLowerCase())) continue;
                if (sender.hasPermission(commands.get(i).getPermission()))
                    commands.get(i).onExec(sender, args);
                else sender.sendMessage(configutils.getMessage("error.no_permission"));
                return true;
            }
            sender.sendMessage(configutils.getMessage("error.invalid_command"));
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String commandLabel, String[] args){
        List<String> resp = new ArrayList<String>();
        if (args.length == 1) {
            for (int i=0;i<commands.size();i++)
                resp.add(commands.get(i).getName());
            return resp;
        }
        for (int i=0;i<commands.size();i++) {
            if (!args[0].equals(commands.get(i).getName())) continue;
            if (commands.get(i).getTabComplete() == null) break;
            if (commands.get(i).getTabComplete().size() < args.length-1) break;
            resp = commands.get(i).getTabComplete().get(args.length-2);
            break;
        }        
        return resp;
    }
}
