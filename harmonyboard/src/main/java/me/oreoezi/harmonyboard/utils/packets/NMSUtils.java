package me.oreoezi.harmonyboard.utils.packets;
import org.bukkit.Bukkit;


public class NMSUtils {
    public static String bukkitVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    public static int versionId = Integer.valueOf(Bukkit.getServer().getClass().getPackage().getName().split("v1_")[1].split("_")[0]);
    public static String legacyPath = "net.minecraft.server." + NMSUtils.bukkitVersion;
    //public static Class<?> ScoreboardTeam = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".ScoreboardTeam") : null;
    //public static Class<?> NMSIChatBaseComponent = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".IChatBaseComponent") : null;
    //public static Class<?> NMSScoreboardObjective = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".ScoreboardObjective") : null;
    //public static Class<?> NMSScoreboardScore = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".ScoreboardScore") : null;
    //public static Class<?> NMSIScoreboardCriteria = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".IScoreboardCriteria") : null;
    //public static Constructor<?> ChatMessage = versionId < 17 ? ReflectionUtils.getConstructor("net.minecraft.server." + version + ".ChatMessage", String.class) : null;
    //public static Class<?> Action = versionId > 12 && versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".ScoreboardServer$Action") : null;
    //public static Class<?> EnumScoreboardHealthDisplay = versionId > 12 && versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".IScoreboardCriteria$EnumScoreboardHealthDisplay") : null;
    public enum ClassType {
        Packet, Scoreboard, Chat;
    }
    public static Class<?> getNMSClass(String className, ClassType type) {
        if (versionId < 17) 
            return ReflectionUtils.getClass(legacyPath + "." + className);
        String path = "";
        switch (type) {
            case Packet: path = "network.protocol.game"; break;
            case Scoreboard: path = "world.scores"; break;
            case Chat: path = "network.chat"; break;
        }
        return ReflectionUtils.getClass("net.minecraft." + path + "." + className);
    }
}
