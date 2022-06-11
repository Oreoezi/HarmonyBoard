package me.oreoezi.harmonyboard.utils.packets.versions;

import me.oreoezi.harmonyboard.utils.HarmonyPlayer;
import me.oreoezi.harmonyboard.utils.HarmonyScoreboard;
import me.oreoezi.harmonyboard.utils.packets.LineParser;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.implementations.ChatMessage;
import me.oreoezi.harmonyboard.utils.packets.implementations.IScoreboardCriteria;
import me.oreoezi.harmonyboard.utils.packets.implementations.PlayerConnection;
import me.oreoezi.harmonyboard.utils.packets.implementations.S3BPacketScoreboardObjective;
import me.oreoezi.harmonyboard.utils.packets.implementations.S3CPacketUpdateScore;
import me.oreoezi.harmonyboard.utils.packets.implementations.S3DPacketDisplayScoreboard;
import me.oreoezi.harmonyboard.utils.packets.implementations.S3EPacketTeams;
import me.oreoezi.harmonyboard.utils.packets.implementations.Scoreboard;
import me.oreoezi.harmonyboard.utils.packets.implementations.ScoreboardObjective;
import me.oreoezi.harmonyboard.utils.packets.implementations.ScoreboardScore;
import me.oreoezi.harmonyboard.utils.packets.implementations.ScoreboardTeam;
import me.oreoezi.harmonyboard.utils.packets.implementations.IScoreboardCriteria.EnumScoreboardHealthDisplay;
import me.oreoezi.harmonyboard.utils.packets.implementations.ScoreboardServer.Action;
public class ScoreboardLegacy extends HarmonyScoreboard {
    private HarmonyPlayer hplayer;
    private PlayerConnection connection;
    private Scoreboard scoreboard;
    private ScoreboardObjective objective;
    public ScoreboardLegacy(HarmonyPlayer hplayer) {
        super(hplayer);
        this.hplayer = hplayer;
    }
    @Override
    public void create() {
        try {
            connection = new PlayerConnection(hplayer.getPlayer());
            scoreboard = new Scoreboard();
            if (NMSUtils.versionId < 13) {
                Object criteria = IScoreboardCriteria.b();
                objective = scoreboard.registerObjective(hplayer.getPlayer().getName(), criteria);
                objective.setDisplayName(hplayer.getTitle());
            }
            else {
                Object criteria = NMSUtils.versionId < 17 ? IScoreboardCriteria.DUMMY() : IScoreboardCriteria.a();
                ChatMessage name = new ChatMessage(hplayer.getPlayer().getName());
                Object display = EnumScoreboardHealthDisplay.integer();
                objective = scoreboard.registerObjective(hplayer.getPlayer().getName(), criteria, name, display);
                objective.setDisplayName(new ChatMessage(hplayer.getTitle()));
            }
            connection.sendPacket(new S3BPacketScoreboardObjective(objective, 0), new S3DPacketDisplayScoreboard(1, objective));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setTitleRaw(String title) {
        try {
            if (NMSUtils.versionId < 13) 
                objective.setDisplayName(title);
            else
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
            ScoreboardTeam team = new ScoreboardTeam(scoreboard, "line" + pos);
            String[] line_parts = LineParser.splitLine(text);
            S3CPacketUpdateScore s3cpacket;
            S3EPacketTeams s3epacket;
            team.setNameset(txt);
            if (NMSUtils.versionId < 13) {
                team.setDisplayName("line" + pos);
                team.setPrefix(line_parts[0]);
                team.setSuffix(line_parts[1]);
                ScoreboardScore score = new ScoreboardScore(scoreboard, objective, txt); 
                score.setScore(pos);
                s3cpacket = new S3CPacketUpdateScore(score);
            }
            else {
                team.setDisplayName(new ChatMessage("line" + pos));
                team.setPrefix(new ChatMessage(line_parts[0]));
                team.setSuffix(new ChatMessage(line_parts[1]));
                s3cpacket = new S3CPacketUpdateScore(Action.change(), hplayer.getPlayer().getName(), txt, pos);
            }
            s3epacket = new S3EPacketTeams(team, create ? 0 : 2);
            connection.sendPacket(s3epacket, s3cpacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void destroy() {
        try {
            for (int i=hplayer.getPreset().length;i>0;i--) 
                connection.sendPacket(new S3EPacketTeams(new ScoreboardTeam(scoreboard, "line" + i), 1));
            connection.sendPacket(new S3BPacketScoreboardObjective(objective, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
