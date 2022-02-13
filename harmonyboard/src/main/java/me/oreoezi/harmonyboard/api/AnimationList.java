package me.oreoezi.harmonyboard.api;

import java.util.ArrayList;
import me.oreoezi.harmonyboard.datamanagers.HarmonyAnimation;

public class AnimationList {
    private ArrayList<HarmonyAnimation> anims;
    public AnimationList() {
        this.anims = new ArrayList<HarmonyAnimation>();
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
    public boolean addAnimation(HarmonyAnimation animation) {
        return anims.add(animation);
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
