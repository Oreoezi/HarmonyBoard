package me.oreoezi.harmonyboard.utils.packets.versions;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.utils.HarmonyPlayer;
import me.oreoezi.harmonyboard.utils.HarmonyScoreboard;
import me.oreoezi.harmonyboard.utils.packets.LineParser;
import me.oreoezi.harmonyboard.utils.packets.ReflectionUtils;
public class ScoreboardUtopic extends HarmonyScoreboard {
	private Object connection;
	private Object scoreboard;
	private Object sb_obj;
    private HarmonyPlayer hplayer;
    private Player player;
    private Class<?> Packet = ReflectionUtils.getClass("net.minecraft.network.protocol.Packet");
    private Class<?> ChatMessage = ReflectionUtils.getClass("net.minecraft.network.chat.ChatMessage");
    private Class<?> S3D = ReflectionUtils.getClass("net.minecraft.network.protocol.game.PacketPlayOutScoreboardDisplayObjective");
    private Class<?> S3B = ReflectionUtils.getClass("net.minecraft.network.protocol.game.PacketPlayOutScoreboardObjective");
    private Class<?> S3C = ReflectionUtils.getClass("net.minecraft.network.protocol.game.PacketPlayOutScoreboardScore");
    private Class<?> S3E = ReflectionUtils.getClass("net.minecraft.network.protocol.game.PacketPlayOutScoreboardTeam");
    private Class<?> Action = ReflectionUtils.getClass("net.minecraft.server.ScoreboardServer$Action");
    private Class<?> IScoreboardCriteria = ReflectionUtils.getClass("net.minecraft.world.scores.criteria.IScoreboardCriteria");
    private Class<?> EnumScoreboardHealthDisplay = ReflectionUtils.getClass("net.minecraft.world.scores.criteria.IScoreboardCriteria$EnumScoreboardHealthDisplay");
    private Class<?> ScoreboardTeam = ReflectionUtils.getClass("net.minecraft.world.scores.ScoreboardTeam");
    private Class<?> ScoreboardScore = ReflectionUtils.getClass("net.minecraft.world.scores.ScoreboardScore");
    private Class<?> ScoreboardObjective = ReflectionUtils.getClass("net.minecraft.world.scores.ScoreboardObjective");
    private Class<?> Scoreboard = ReflectionUtils.getClass("net.minecraft.world.scores.Scoreboard");
    private Class<?> IChatBaseComponent = ReflectionUtils.getClass("net.minecraft.network.chat.IChatBaseComponent");
    private void sendPacket(Object... packets) {
        try {
            for (Object packet : packets) 
                connection.getClass().getMethod("sendPacket", Packet).invoke(connection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ScoreboardUtopic(HarmonyPlayer hplayer) {
        super(hplayer);
        this.player = hplayer.getPlayer();
        this.hplayer = hplayer;
    }
    @Override
    public void create() {
        try {
            Object craftplayer = player.getClass().getMethod("getHandle").invoke(player);
            connection = craftplayer.getClass().getField("b").get(craftplayer);
            scoreboard = Scoreboard.getDeclaredConstructor().newInstance();
            Object criteria = IScoreboardCriteria.getField("a").get(null);
            Object display = EnumScoreboardHealthDisplay.getEnumConstants()[0];
            Object playername = ChatMessage.getDeclaredConstructor(String.class).newInstance(player.getName());
            sb_obj = scoreboard.getClass().getMethod("registerObjective", String.class, criteria.getClass(), IChatBaseComponent, display.getClass()).invoke(scoreboard, player.getName(), criteria , playername, display);
            Object displayname = ChatMessage.getDeclaredConstructor(String.class).newInstance(ChatColor.translateAlternateColorCodes('&',hplayer.getTitle()));
            sb_obj.getClass().getMethod("setDisplayName", IChatBaseComponent).invoke(sb_obj, displayname);
            Object s3bpacket = S3B.getDeclaredConstructor(ScoreboardObjective, int.class).newInstance(sb_obj, 0);
            Object s3dpacket = S3D.getDeclaredConstructor(int.class, ScoreboardObjective).newInstance(1, sb_obj);
            sendPacket(s3bpacket, s3dpacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override 
	public void setTitleRaw(String title) {
        try {
            Object displayname = ChatMessage.getDeclaredConstructor(String.class).newInstance(ChatColor.translateAlternateColorCodes('&',title));
            sb_obj.getClass().getMethod("setDisplayName", IChatBaseComponent).invoke(sb_obj, displayname);
            Object s3bpacket = S3B.getDeclaredConstructor(ScoreboardObjective, int.class).newInstance(sb_obj, 2);
            sendPacket(s3bpacket);
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
            Object team = ScoreboardTeam.getDeclaredConstructor(Scoreboard, String.class).newInstance(scoreboard, "line"+pos);
            Object displayname = ChatMessage.getDeclaredConstructor(String.class).newInstance("line"+pos);
            team.getClass().getMethod("setDisplayName", IChatBaseComponent).invoke(team, displayname);
            Object prefix = ChatMessage.getDeclaredConstructor(String.class).newInstance(LineParser.splitLine(text)[0]);
            Object suffix = ChatMessage.getDeclaredConstructor(String.class).newInstance(LineParser.splitLine(text)[1]);
            team.getClass().getMethod("setPrefix", IChatBaseComponent).invoke(team, prefix);
            team.getClass().getMethod("setSuffix", IChatBaseComponent).invoke(team, suffix);
            Set<String> nameset = new HashSet<String>();
            nameset.add(txt);
            team = ReflectionUtils.setValue(team, "f", nameset);
            Object sbs = ScoreboardScore.getDeclaredConstructor(Scoreboard, ScoreboardObjective, String.class).newInstance(scoreboard, sb_obj, txt);
            sbs.getClass().getMethod("setScore", int.class).invoke(sbs, pos);
            Object s3epacket = S3E.getMethod("a", ScoreboardTeam, boolean.class).invoke(null, team, create);
            Object change = Action.getEnumConstants()[0];
            Object s3cpacket = S3C.getDeclaredConstructor(change.getClass(), String.class, String.class, int.class).newInstance(change, player.getName(), txt, pos);
            sendPacket(s3epacket, s3cpacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void destroy() {
        try {
            for (int i=hplayer.getPreset().length;i>0;i--){
                Object team = ScoreboardTeam.getDeclaredConstructor(Scoreboard, String.class).newInstance(scoreboard, "line"+i);
                Object teampacket = S3E.getMethod("a", ScoreboardTeam).invoke(null, team);
                sendPacket(teampacket);
            }
            Object packet = S3B.getConstructor(ScoreboardObjective, int.class).newInstance(sb_obj, 1);
            sendPacket(packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
