package me.oreoezi.harmonyboard.datamanagers;


public class HarmonyAnimation {
    private String[] frames;
    private int frame = 0;
    private int frame_rate;
    private String name;
    public HarmonyAnimation(String name, int frame_rate, String[] frames) {
        this.frames = frames;
        this.name = name;
        this.frame_rate = frame_rate;
    }
    public void updateAnimation(int rate) {
        frame+= rate;
        if (frame >= frames.length * frame_rate) frame = 0;
    }
    public String getCurrentFrame() {
        return frames[frame/frame_rate];
    }
    public String getName() {
        return name;
    }
}
