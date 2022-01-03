package me.oreoezi.harmonyboard.utils.versions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;


import me.oreoezi.harmonyboard.utils.HarmonyPlayer;
import me.oreoezi.harmonyboard.utils.HarmonyScoreboard;
import me.oreoezi.harmonyboard.utils.PacketUtils;

public class ScoreboardUpdated extends HarmonyScoreboard {
    private HarmonyPlayer hplayer;
    private Player player;
    private Object connection;
    private Object scoreboard;
    private Object objective;
    private String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private Class<?> ChatMessage = PacketUtils.getClass("net.minecraft.server." + version + ".ChatMessage");
    private Class<?> Packet = PacketUtils.getClass("net.minecraft.server." + version + ".Packet");
    private Class<?> Scoreboard = PacketUtils.getClass("net.minecraft.server." + version + ".Scoreboard");
    private Class<?> ScoreboardObjective = PacketUtils.getClass("net.minecraft.server." + version + ".ScoreboardObjective");
    private Class<?> IScoreboardCriteria = PacketUtils.getClass("net.minecraft.server." + version + ".IScoreboardCriteria");
    private Class<?> ScoreboardTeam = PacketUtils.getClass("net.minecraft.server." + version + ".ScoreboardTeam");
    private Class<?> IChatBaseComponent = PacketUtils.getClass("net.minecraft.server." + version + ".IChatBaseComponent");
    private Class<?> Action = PacketUtils.getClass("net.minecraft.server." + version + ".ScoreboardServer$Action");
    private Class<?> EnumScoreboardHealthDisplay = PacketUtils.getClass("net.minecraft.server." + version + ".IScoreboardCriteria$EnumScoreboardHealthDisplay");
    private Class<?> S3B = PacketUtils.getClass("net.minecraft.server." + version + ".PacketPlayOutScoreboardObjective");
    private Class<?> S3D = PacketUtils.getClass("net.minecraft.server." + version + ".PacketPlayOutScoreboardDisplayObjective");
    private Class<?> S3E = PacketUtils.getClass("net.minecraft.server." + version + ".PacketPlayOutScoreboardTeam");
    private Class<?> S3C = PacketUtils.getClass("net.minecraft.server." + version + ".PacketPlayOutScoreboardScore");
    private void sendPacket(Object... packets) {
        try {
            for (Object packet : packets) 
                connection.getClass().getMethod("sendPacket", Packet).invoke(connection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ScoreboardUpdated(HarmonyPlayer hplayer) {
        super(hplayer);
        this.hplayer = hplayer;
        this.player = hplayer.getPlayer();
    }
    @Override
    public void create() {
        try {
            Object craftplayer = player.getClass().getMethod("getHandle").invoke(player);
            connection = craftplayer.getClass().getField("playerConnection").get(craftplayer);
            scoreboard = Scoreboard.getDeclaredConstructor().newInstance();
            Object criteria = IScoreboardCriteria.getField("DUMMY").get(null);
            Object name = ChatMessage.getDeclaredConstructor(String.class).newInstance(player.getName());
            Object display = EnumScoreboardHealthDisplay.getEnumConstants()[0];
            objective = scoreboard.getClass().getMethod("registerObjective", String.class, criteria.getClass(), IChatBaseComponent, display.getClass()).invoke(scoreboard, player.getName(), criteria, name, display);
            Object title = ChatMessage.getDeclaredConstructor(String.class).newInstance(hplayer.getTitle());
            objective.getClass().getMethod("setDisplayName", IChatBaseComponent).invoke(objective, title);
            Object s3bpacket = S3B.getDeclaredConstructor(ScoreboardObjective, int.class).newInstance(objective, 0);
            Object s3dpacket = S3D.getDeclaredConstructor(int.class, ScoreboardObjective).newInstance(1, objective);
            sendPacket(s3bpacket, s3dpacket);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    @Override
    public void setTitle(String title) {
        try {
            Object title_object = ChatMessage.getDeclaredConstructor(String.class).newInstance(title);
            objective.getClass().getMethod("setDisplayName", ChatMessage).invoke(objective, title_object);
            sendPacket(S3B.getDeclaredConstructor(ScoreboardObjective, int.class).newInstance(objective, 2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setLineRaw(int pos, String text, boolean create) {
        try {
            String txt = "§r";
            for (int i=0;i<pos;i++) {
                txt += "§r";
            }
            Object team = ScoreboardTeam.getDeclaredConstructor(Scoreboard, String.class).newInstance(scoreboard, "line" + pos);
            Object team_name = ChatMessage.getDeclaredConstructor(String.class).newInstance("line" + pos);
            Object prefix = ChatMessage.getDeclaredConstructor(String.class).newInstance(PacketUtils.splitLine(text)[0]);
            Object suffix = ChatMessage.getDeclaredConstructor(String.class).newInstance(PacketUtils.splitLine(text)[1]);
            team.getClass().getMethod("setDisplayName", IChatBaseComponent).invoke(team, team_name);
            team.getClass().getMethod("setPrefix", IChatBaseComponent).invoke(team, prefix);
            team.getClass().getMethod("setSuffix", IChatBaseComponent).invoke(team, suffix);
            Set<String> nameset = new HashSet<String>();
            nameset.add(txt);
            team = PacketUtils.setValue(team, "c", nameset);
            Object teampacket = S3E.getDeclaredConstructor(ScoreboardTeam, int.class).newInstance(team, create ? 0 : 2);
            Object change = Action.getEnumConstants()[0];
            Object scorepacket = S3C.getDeclaredConstructor(change.getClass(), String.class, String.class, int.class).newInstance(change, player.getName(), txt, pos);
            sendPacket(teampacket, scorepacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void destroy() {
        try {
            sendPacket(S3B.getDeclaredConstructor(ScoreboardObjective, int.class).newInstance(objective, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
