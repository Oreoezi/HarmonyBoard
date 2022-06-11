package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.ReflectionUtils;

public class ScoreboardServer {
    private static Class<?> actionEnum = ReflectionUtils.getClass((NMSUtils.versionId < 17 ? NMSUtils.legacyPath : "net.minecraft.server") + ".ScoreboardServer$Action");
    public static class Action {
        public static Object change() {
            try {
                return actionEnum.getEnumConstants()[0];
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
