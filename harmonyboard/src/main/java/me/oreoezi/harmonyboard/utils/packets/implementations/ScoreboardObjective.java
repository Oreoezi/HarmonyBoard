package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.IChatBaseComponent;

public class ScoreboardObjective {
    private Object objective;
    public Object getRaw() {
        return objective;
    }
    public void setDisplayName(String name) {
        try {
            objective.getClass().getMethod("setDisplayName", String.class).invoke(objective, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setDisplayName(IChatBaseComponent name) {
        try {
            objective.getClass().getMethod("setDisplayName", NMSUtils.NMSIChatBaseComponent).invoke(objective, name.getRaw());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ScoreboardObjective(Object objective) {
        this.objective = objective;
    }
}
