package me.oreoezi.harmonyboard.utils.packets.versions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.utils.HarmonyPlayer;
import me.oreoezi.harmonyboard.utils.HarmonyScoreboard;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.chat.IChatBaseComponent.ChatSerializer;
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
	public void setTitleRaw(String title) {
	    sb_obj.a(new ChatMessage(ChatColor.translateAlternateColorCodes('&',title)));
		connection.a(new PacketPlayOutScoreboardObjective(sb_obj, 2));
	}
    private String handleHexColor(String input) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(input);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of(input.substring(matcher.start(), matcher.end())).toString());
        }
        return matcher.appendTail(buffer).toString();
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
            String pref = ComponentSerializer.toString(new TextComponent(handleHexColor(text)));
            player.sendMessage();
            team.b(ChatSerializer.a(pref));
            //team.c(ChatSerializer.a(suf));
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
