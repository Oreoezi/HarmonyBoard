package me.oreoezi.harmonyboard.utils.packets.versions;

import me.oreoezi.harmonyboard.utils.HarmonyPlayer;
import me.oreoezi.harmonyboard.utils.HarmonyScoreboard;
import me.oreoezi.harmonyboard.utils.packets.LineParser;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.implementations.PlayerConnection;
import me.oreoezi.harmonyboard.utils.packets.implementations.S3BPacketScoreboardObjective;
import me.oreoezi.harmonyboard.utils.packets.implementations.S3CPacketUpdateScore;
import me.oreoezi.harmonyboard.utils.packets.implementations.S3DPacketDisplayScoreboard;
import me.oreoezi.harmonyboard.utils.packets.implementations.S3EPacketTeams;
import me.oreoezi.harmonyboard.utils.packets.implementations.Scoreboard;
import me.oreoezi.harmonyboard.utils.packets.implementations.ScoreboardObjective;
import me.oreoezi.harmonyboard.utils.packets.implementations.ScoreboardScore;
import me.oreoezi.harmonyboard.utils.packets.implementations.ScoreboardTeam;
import net.md_5.bungee.api.ChatColor;

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
            Object criteria = NMSUtils.IScoreboardCriteria.b();
            objective = scoreboard.registerObjective(hplayer.getPlayer().getName(), criteria);
            objective.setDisplayName(hplayer.getTitle());
            connection.sendPacket(new S3BPacketScoreboardObjective(objective, 0), new S3DPacketDisplayScoreboard(1, objective));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setTitleRaw(String title) {
        try {
            objective.setDisplayName(title);
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
            team.setDisplayName("line" + pos);
            team.setPrefix(ChatColor.translateAlternateColorCodes('&',LineParser.splitLine(text)[0]));
            team.setSuffix(ChatColor.translateAlternateColorCodes('&',LineParser.splitLine(text)[1]));
            team.setNameset(txt);
            ScoreboardScore score = new ScoreboardScore(scoreboard, objective, txt); 
            score.setScore(pos);
            S3EPacketTeams s3epacket = new S3EPacketTeams(team, create ? 0 : 2);
            S3CPacketUpdateScore s3cpacket = new S3CPacketUpdateScore(score);
            connection.sendPacket(s3epacket, s3cpacket);
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
