package me.oreoezi.harmonyboard.api;

import java.util.ArrayList;
import java.util.Map;

import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.datamanagers.ScoreboardTemplate;
import me.oreoezi.harmonyboard.utils.HarmonyPlayer;

public class PlayerList {
    private ArrayList<HarmonyPlayer> playerlist;
    private boolean hasOraxen;
    private boolean checkToggle;
    public PlayerList(boolean hasOraxen, boolean checkToggle) {
        this.hasOraxen = hasOraxen;
        this.checkToggle = checkToggle;
        playerlist = new ArrayList<HarmonyPlayer>();
    }
    public void addPlayer(HarmonyPlayer player) {
        playerlist.add(player);
    }
    public HarmonyPlayer getPlayer(Player player) {
        for (int i=0;i<playerlist.size();i++) {
            if (playerlist.get(i).getPlayer().getName().equals(player.getName())) return playerlist.get(i);
        }
        return null;
    }
    public HarmonyPlayer getPlayer(String name) {
        for (int i=0;i<playerlist.size();i++) {
            if (playerlist.get(i).getPlayer().getName().equals(name)) return playerlist.get(i);
        }
        return null;
    }
    public HarmonyPlayer getPlayer(int index) {
        return playerlist.get(index);
    }
    public int size() {
        return playerlist.size();
    }
    private void initPlayer(ScoreboardTemplate sb_template, HarmonyPlayer hplayer) {
        String title = sb_template.getTitle();
        String[] lines = sb_template.getPreset();
        if (hasOraxen) {
            Map<String, io.th0rgal.oraxen.font.Glyph> glyphs = io.th0rgal.oraxen.OraxenPlugin.get().getFontManager().getGlyphByPlaceholderMap();
            for (String key : glyphs.keySet()) {
                title = title.replace("<glyph:" + glyphs.get(key).getName() + ">", glyphs.get(key).getCharacter() + "");
                for (int i=0;i<lines.length;i++) 
                    lines[i] = lines[i].replace("<glyph:" + glyphs.get(key).getName() + ">", glyphs.get(key).getCharacter() + "");
            }
        }
        hplayer.setPreset(title, lines);
        hplayer.getScoreboard().create();
        addPlayer(hplayer);
    }
    public boolean addPlayerWithScoreboard(HarmonyPlayer hplayer) {
       if (!isToggled(hplayer.getPlayer().getUniqueId().toString())) return false;
       ArrayList<ScoreboardTemplate> templates = HarmonyBoard.instance.getConfigs().getScoreboards();
        for (int i=0;i<templates.size();i++) {
            if (templates.get(i).isDefault()) continue; //priority for nondefault
            if (!templates.get(i).isMatching(hplayer)) continue;
            ScoreboardTemplate sb_template = templates.get(i);
            initPlayer(sb_template, hplayer);
            return true;
        }
        for (int i=0;i<templates.size();i++) {
            if (!templates.get(i).isDefault()) continue;
            ScoreboardTemplate sb_template = templates.get(i);
            initPlayer(sb_template, hplayer);
            return true;
        }
        return false;
    }
    public boolean isToggled(String uuid) {
        if (checkToggle) {
            String query = "SELECT * FROM toggle_off WHERE uuid='" + uuid + "' LIMIT 1";
            if (HarmonyBoard.instance.getDatabaseInstance().executeQuery(query).size() > 0) return false;
        }
        return true;
    }
    /**
     * Do not use this method unless you want the thread to error out.
     * Use HarmonyPlayer#destroy instead.
     * @param player
     */
    public void removePlayer(HarmonyPlayer player) {
        playerlist.remove(player);
    }
}
