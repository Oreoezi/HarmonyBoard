package me.oreoezi.harmonyboard.utils.packets;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;

public class NMSUtils {
    public static String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    public static int versionId = Integer.valueOf(Bukkit.getServer().getClass().getPackage().getName().split("v1_")[1].split("_")[0]);
    public static Class<?> Packet = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".Packet") : null;
    public static Class<?> NMSScoreboard = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".Scoreboard") : null;
    public static Class<?> S3B = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".PacketPlayOutScoreboardObjective") : null;
    public static Class<?> S3D = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".PacketPlayOutScoreboardDisplayObjective") : null;
    public static Class<?> S3E = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".PacketPlayOutScoreboardTeam") : null;
    public static Class<?> S3C = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".PacketPlayOutScoreboardScore") : null;
    public static Class<?> ScoreboardTeam = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".ScoreboardTeam") : null;
    public static Class<?> NMSIChatBaseComponent = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".IChatBaseComponent") : null;
    public static Class<?> NMSScoreboardObjective = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".ScoreboardObjective") : null;
    public static Class<?> NMSScoreboardScore = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".ScoreboardScore") : null;
    public static Class<?> NMSIScoreboardCriteria = versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".IScoreboardCriteria") : null;
    public static Constructor<?> ChatMessage = versionId < 17 ? ReflectionUtils.getConstructor("net.minecraft.server." + version + ".ChatMessage", String.class) : null;
    public static Method getHandle = ReflectionUtils.getMethod("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer", "getHandle");
    public static Class<?> Action = versionId > 12 && versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".ScoreboardServer$Action") : null;
    public static Class<?> EnumScoreboardHealthDisplay = versionId > 12 && versionId < 17 ? ReflectionUtils.getClass("net.minecraft.server." + version + ".IScoreboardCriteria$EnumScoreboardHealthDisplay") : null;
    public static class NMSPacket {
        public Object getRaw() {
            return null;
        }
    }
    public static class IChatBaseComponent {
        private Object component;
        public Object getRaw() {
            return component;
        }
        public IChatBaseComponent(Object component) {
            this.component = component;
        }
    }
    public static class ChatMessage extends IChatBaseComponent {
        private Object chatmessage;
        @Override 
        public Object getRaw() {
            return chatmessage;
        }
        public ChatMessage(Object component) {
            super(component);
            try {
                chatmessage = ChatMessage.newInstance(component);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static class IScoreboardCriteria {
        public static Object b() {
            try {
                return NMSIScoreboardCriteria.getField("b").get(NMSIScoreboardCriteria);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        public static Object DUMMY() {
            try {
                return NMSIScoreboardCriteria.getField("DUMMY").get(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public static class ScoreboardServer {
        public static class Action {
            public static Object change() {
                try {
                    return Action.getEnumConstants()[0];
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
    }
}
