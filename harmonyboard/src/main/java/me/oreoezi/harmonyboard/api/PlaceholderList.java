package me.oreoezi.harmonyboard.api;

import java.util.ArrayList;

import me.oreoezi.harmonyboard.placeholders.*;
import me.oreoezi.harmonyboard.utils.HarmonyPlaceholder;

public class PlaceholderList {
    private ArrayList<HarmonyPlaceholder> placeholders;
    public PlaceholderList() {
        placeholders = new ArrayList<HarmonyPlaceholder>();
        createPlaceholders();
    }
    private void createPlaceholders() {
		placeholders.add(new OnlinePlayers());
		placeholders.add(new PlayerName());
		placeholders.add(new Health());
		placeholders.add(new Ping());
		placeholders.add(new HealthHearts());
		placeholders.add(new PosX());
		placeholders.add(new PosY());
		placeholders.add(new PosZ());
		placeholders.add(new PosXOW());
		placeholders.add(new PosZOW());
	}
    public void registerPlaceholder(HarmonyPlaceholder placeholder) {
        placeholders.add(placeholder);
    }
    public void unregisterPlaceholder(HarmonyPlaceholder placeholder) {
        placeholders.remove(placeholder);
    }
    /**
     * Removing official placeholders is technically supported. Doing so won't save up on performance, however.
     * @param name The name of the placeholder.
     */
    public void unregisterPlaceholder(String name) {
        for (int i=0;i<placeholders.size();i++) {
            if (placeholders.get(i).getName() == name) {
                placeholders.remove(i);
                break;
            }
        }
    }
    public int size() {
        return placeholders.size();
    }
    public HarmonyPlaceholder getPlaceholder(int index) {
        return placeholders.get(index);
    }
}
