package me.oreoezi.harmonyboard.utils;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import me.oreoezi.harmonyboard.App;

public class HarmonyCommand {
    public HarmonyCommand(App main) {
		
	}
	public boolean onExec(CommandSender player, String[] args) {
		return true;
	}
	public String getName() {
		return "";
	}
	public String getPermission() {
		return "";
	}
	public String getDescription() {
		return "";
	}
	public String getArgs() {
		return "";
	}
	public ArrayList<ArrayList<String>> getTabComplete() {
		return null;
	}
}
