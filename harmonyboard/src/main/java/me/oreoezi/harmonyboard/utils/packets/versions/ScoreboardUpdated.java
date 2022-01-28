package me.oreoezi.harmonyboard.utils.packets.versions;

import me.oreoezi.harmonyboard.utils.HarmonyPlayer;
import me.oreoezi.harmonyboard.utils.HarmonyScoreboard;
import me.oreoezi.harmonyboard.utils.packets.LineParser;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.ChatMessage;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.ScoreboardServer.Action;
import me.oreoezi.harmonyboard.utils.packets.implementations.PlayerConnection;
import me.oreoezi.harmonyboard.utils.packets.implementations.S3BPacketScoreboardObjective;
import me.oreoezi.harmonyboard.utils.packets.implementations.S3CPacketUpdateScore;
import me.oreoezi.harmonyboard.utils.packets.implementations.S3DPacketDisplayScoreboard;
import me.oreoezi.harmonyboard.utils.packets.implementations.S3EPacketTeams;
import me.oreoezi.harmonyboard.utils.packets.implementations.Scoreboard;
import me.oreoezi.harmonyboard.utils.packets.implementations.ScoreboardObjective;
import me.oreoezi.harmonyboard.utils.packets.implementations.ScoreboardTeam;

public class ScoreboardUpdated extends HarmonyScoreboard {
    private HarmonyPlayer hplayer;
    private PlayerConnection connection;
    private Scoreboard scoreboard;
    private ScoreboardObjective objective;
    public ScoreboardUpdated(HarmonyPlayer hplayer) {
        super(hplayer);
        this.hplayer = hplayer;
    }
    @Override
    public void create() {
        try {
            connection = new PlayerConnection(hplayer.getPlayer());
            scoreboard = new Scoreboard();
            Object criteria = NMSUtils.IScoreboardCriteria.DUMMY();
            ChatMessage name = new ChatMessage(hplayer.getPlayer().getName());
            Object display = NMSUtils.EnumScoreboardHealthDisplay.getEnumConstants()[0];
            objective = scoreboard.registerObjective(hplayer.getPlayer().getName(), criteria, name, display);
            objective.setDisplayName(new ChatMessage(hplayer.getTitle()));
            connection.sendPacket(new S3BPacketScoreboardObjective(objective, 0), new S3DPacketDisplayScoreboard(1, objective));
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    @Override
    public void setTitleRaw(String title) {
        try {
            objective.setDisplayName(new ChatMessage(title));
            connection.sendPacket(new S3BPacketScoreboardObjective(objective, 2));
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
            ScoreboardTeam team = new ScoreboardTeam(scoreboard, "line"+pos);
            team.setDisplayName(new ChatMessage("line" + pos));
            team.setPrefix(new ChatMessage(LineParser.splitLine(text)[0]));
            team.setSuffix(new ChatMessage(LineParser.splitLine(text)[1]));
            team.setNameset(txt);
            S3CPacketUpdateScore s3cpacket = new S3CPacketUpdateScore(Action.change(), hplayer.getPlayer().getName(), txt, pos);
            connection.sendPacket(new S3EPacketTeams(team, create ? 0 : 2), s3cpacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void destroy() {
        try {
            connection.sendPacket(new S3BPacketScoreboardObjective(objective, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
