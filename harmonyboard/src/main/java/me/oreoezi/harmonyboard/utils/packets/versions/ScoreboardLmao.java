package me.oreoezi.harmonyboard.utils.packets.versions;

import org.bukkit.entity.Player;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;
import me.oreoezi.harmonyboard.utils.HarmonyScoreboard;
import me.oreoezi.harmonyboard.utils.packets.LineParser;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.network.chat.IChatBaseComponent;
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
            sb_obj = scoreboard.a(player.getName(), IScoreboardCriteria.a, IChatBaseComponent.a(player.getName()), IScoreboardCriteria.EnumScoreboardHealthDisplay.a);
            sb_obj.a(IChatBaseComponent.a(ChatColor.translateAlternateColorCodes('&',hplayer.getTitle())));
            connection.a(new PacketPlayOutScoreboardObjective(sb_obj, 0));
            connection.a(new PacketPlayOutScoreboardDisplayObjective(1, sb_obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override 
	public void setTitleRaw(String title) {
	    sb_obj.a(IChatBaseComponent.a(ChatColor.translateAlternateColorCodes('&',title)));
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
            team.a(IChatBaseComponent.a("line"+pos));
            String pref = LineParser.splitLine(text)[0];
            String suf = LineParser.splitLine(text)[1];
            team.b(IChatBaseComponent.a(pref));
            team.c(IChatBaseComponent.a(suf));
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
        for (int i=hplayer.getPreset().length;i>0;i--) 
            connection.a(PacketPlayOutScoreboardTeam.a(new ScoreboardTeam(scoreboard, "line" + i)));
        connection.a(new PacketPlayOutScoreboardObjective(sb_obj, 1));
    }
}
