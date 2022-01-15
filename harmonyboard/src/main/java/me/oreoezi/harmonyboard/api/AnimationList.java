package me.oreoezi.harmonyboard.api;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.configuration.file.FileConfiguration;

import me.oreoezi.harmonyboard.App;
import me.oreoezi.harmonyboard.utils.HarmonyAnimation;

public class AnimationList {
    private ArrayList<HarmonyAnimation> anims;
    public AnimationList(App main) {
        this.anims = new ArrayList<HarmonyAnimation>();
        for (Object key : main.getConfigs().animations.keySet()) {
			FileConfiguration an_config = main.getConfigs().animations.get(key);
			anims.add(new HarmonyAnimation(key.toString(), an_config.getInt("delay"), Arrays.copyOf(an_config.getList("lines").toArray(), an_config.getList("lines").toArray().length, String[].class)));
		}
    }
    public void tick(int delay) {
        for (int i=0;i<anims.size();i++) {
            anims.get(i).updateAnimation(delay);
        }
    }
    public boolean tick(int delay, String name) {
        for (int i=0;i<anims.size();i++) {
            if (!anims.get(i).getName().equals(name)) continue;
            anims.get(i).updateAnimation(delay);
            return true;
        }
        return false;
    }
    public ArrayList<HarmonyAnimation> getAnimations() {
        return anims;
    }
    public HarmonyAnimation getAnimation(String name) {
        for (int i=0;i<anims.size();i++) {
            if (!anims.get(i).getName().equals(name)) continue;
            return anims.get(i);
        }
        return null;
    }
    public HarmonyAnimation get(int index) {
        return anims.get(index);
    }
    public int size() {
        return anims.size();
    }
}
