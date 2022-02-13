package me.oreoezi.harmonyboard.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.oreoezi.harmonyboard.command.commands.ReloadCommand;
import me.oreoezi.harmonyboard.command.commands.ScoreboardCommand;
import me.oreoezi.harmonyboard.command.commands.ToggleCommand;
import me.oreoezi.harmonyboard.datamanagers.Configs;
import me.oreoezi.harmonyboard.utils.HarmonyCommand;

public class CommandManager implements CommandExecutor, TabCompleter {
    private ArrayList<HarmonyCommand> commands;
    private Configs configs;
    public CommandManager(Configs configs) {
        this.configs = configs;
        commands = new ArrayList<HarmonyCommand>();
        commands.add(new ReloadCommand());
        commands.add(new ScoreboardCommand());
        commands.add(new ToggleCommand());
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
                else sender.sendMessage(configs.getMessage("error.no_permission"));
                return true;
            }
            sender.sendMessage(configs.getMessage("error.invalid_command"));
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String commandLabel, String[] args){
        if (args.length == 1) {
            List<String> resp = new ArrayList<String>();
            for (int i=0;i<commands.size();i++)
                resp.add(commands.get(i).getName());
            return resp;
        }
        for (int i=0;i<commands.size();i++) {
            if (!args[0].equals(commands.get(i).getName())) continue;
            if (commands.get(i).getTabComplete() == null) break;
            if (commands.get(i).getTabComplete().length < args.length-1) break;
            return Arrays.asList(commands.get(i).getTabComplete()[args.length-2]);
        }        
        return null;
    }
}
