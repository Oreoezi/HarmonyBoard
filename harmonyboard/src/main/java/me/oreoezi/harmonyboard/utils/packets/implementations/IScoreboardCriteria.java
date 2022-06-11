package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.ReflectionUtils;

public interface IScoreboardCriteria {
    public static String path = (NMSUtils.versionId < 17 ? NMSUtils.legacyPath  : "net.minecraft.world.scores.criteria") + ".IScoreboardCriteria";
    public static Class<?> scoreboardCriteriaInterface = ReflectionUtils.getClass(path);
    public static Class<?> scoreboardHealthDisplayEnum = ReflectionUtils.getClass(path + "$EnumScoreboardHealthDisplay");
    public static Object a() {
        try {
            return scoreboardCriteriaInterface.getField("a").get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Object b() {
        try {
            return scoreboardCriteriaInterface.getField("b").get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Object DUMMY() {
        try {
            return scoreboardCriteriaInterface.getField("DUMMY").get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public interface EnumScoreboardHealthDisplay {
        public static Object integer() {
            return scoreboardHealthDisplayEnum.getEnumConstants()[0];
        } 
    }
}
