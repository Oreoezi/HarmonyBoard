package me.oreoezi.harmonyboard.utils.versions;

import java.util.HashSet;
import java.util.Set;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.utils.HarmonyPlayer;
import me.oreoezi.harmonyboard.utils.HarmonyScoreboard;
import me.oreoezi.harmonyboard.utils.PacketUtils;

public class ScoreboardLegacy extends HarmonyScoreboard {
    private HarmonyPlayer hplayer;
    private Player player;
    private String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private Object connection;
    private Object scoreboard;
    private Object objective;
    private Class<?> Packet = PacketUtils.getClass("net.minecraft.server." + version + ".Packet");
    private Class<?> Scoreboard = PacketUtils.getClass("net.minecraft.server." + version + ".Scoreboard");
    private Class<?> ScoreboardObjective = PacketUtils.getClass("net.minecraft.server." + version + ".ScoreboardObjective");
    private Class<?> IScoreboardCriteria = PacketUtils.getClass("net.minecraft.server." + version + ".IScoreboardCriteria");
    private Class<?> ScoreboardTeam = PacketUtils.getClass("net.minecraft.server." + version + ".ScoreboardTeam");
    private Class<?> ScoreboardScore = PacketUtils.getClass("net.minecraft.server." + version + ".ScoreboardScore");
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
    public ScoreboardLegacy(HarmonyPlayer hplayer) {
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
            objective = scoreboard.getClass().getMethod("registerObjective", String.class, IScoreboardCriteria).invoke(scoreboard, player.getName(), IScoreboardCriteria.getField("b").get(IScoreboardCriteria));
            objective.getClass().getMethod("setDisplayName", String.class).invoke(objective, hplayer.getTitle());
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
            objective.getClass().getMethod("setDisplayName", String.class).invoke(objective, title);
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
            team.getClass().getMethod("setDisplayName", String.class).invoke(team, "line" + pos);
            team.getClass().getMethod("setPrefix", String.class).invoke(team, PacketUtils.splitLine(text)[0]);
            team.getClass().getMethod("setSuffix", String.class).invoke(team, PacketUtils.splitLine(text)[1]);
            Set<String> nameset = new HashSet<String>();
            nameset.add(txt);
            team = PacketUtils.setValue(team, "c", nameset);
            Object sbs = ScoreboardScore.getDeclaredConstructor(Scoreboard, ScoreboardObjective, String.class).newInstance(scoreboard, objective, txt);
            sbs.getClass().getMethod("setScore", int.class).invoke(sbs, pos);
            Object teampacket = S3E.getDeclaredConstructor(ScoreboardTeam, int.class).newInstance(team, create ? 0 : 2);
            Object scorepacket = S3C.getDeclaredConstructor(ScoreboardScore).newInstance(sbs);
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
