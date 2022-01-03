package me.oreoezi.harmonyboard.utils.versions;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.utils.HarmonyPlayer;
import me.oreoezi.harmonyboard.utils.HarmonyScoreboard;
import me.oreoezi.harmonyboard.utils.PacketUtils;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardScore;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardTeam;
import net.minecraft.server.ScoreboardServer.Action;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.ScoreboardScore;
import net.minecraft.world.scores.ScoreboardTeam;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;

public class ScoreboardLmao extends HarmonyScoreboard {
    private PlayerConnection connection;
	private Scoreboard scoreboard;
	private ScoreboardObjective sb_obj;
    private HarmonyPlayer hplayer;
    private Player player;
    public ScoreboardLmao(HarmonyPlayer hplayer) {
        super(hplayer);
        this.player = hplayer.getPlayer();
        this.hplayer = hplayer;
    }
    @Override
    public void create() {
        try {
            Object craftplayer = player.getClass().getMethod("getHandle").invoke(player);
            connection = (PlayerConnection) craftplayer.getClass().getField("b").get(craftplayer);
            scoreboard = new Scoreboard();
            sb_obj = scoreboard.a(player.getName(), IScoreboardCriteria.a,new ChatMessage(player.getName()), IScoreboardCriteria.EnumScoreboardHealthDisplay.a);
            sb_obj.a(new ChatMessage(ChatColor.translateAlternateColorCodes('&',hplayer.getTitle())));
            connection.a(new PacketPlayOutScoreboardObjective(sb_obj, 0));
            connection.a(new PacketPlayOutScoreboardDisplayObjective(1, sb_obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override 
	public void setTitle(String title) {
	    sb_obj.a(new ChatMessage(ChatColor.translateAlternateColorCodes('&',title)));
		connection.a(new PacketPlayOutScoreboardObjective(sb_obj, 2));
	}
    @Override
    public void setLineRaw(int pos, String text, boolean create) {
        try {
            String txt = "§r";
            for (int i=0;i<pos;i++) {
                txt += "§r";
            }
            ScoreboardTeam team = new ScoreboardTeam(scoreboard, "line"+pos);
            team.a(new ChatMessage("line"+pos));
            team.b(new ChatMessage(PacketUtils.splitLine(text)[0]));
            team.c(new ChatMessage(PacketUtils.splitLine(text)[1]));
            team.g().add(txt);
            ScoreboardScore sbs = new ScoreboardScore(scoreboard, sb_obj, txt);
            sbs.a(pos);
            connection.a(PacketPlayOutScoreboardTeam.a(team, create));
            connection.a(new PacketPlayOutScoreboardScore(Action.a, player.getName(), txt, pos));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void destroy() {
        connection.a(new PacketPlayOutScoreboardObjective(sb_obj, 1));
    }
}
