package me.oreoezi.harmonyboard.utils.packets;
import org.bukkit.Bukkit;


public class NMSUtils {
    public static String bukkitVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    public static int versionId = Integer.valueOf(Bukkit.getServer().getClass().getPackage().getName().split("v1_")[1].split("_")[0]);
    public static String legacyPath = "net.minecraft.server." + NMSUtils.bukkitVersion;
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
