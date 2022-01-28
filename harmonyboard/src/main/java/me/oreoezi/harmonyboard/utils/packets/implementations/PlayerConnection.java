package me.oreoezi.harmonyboard.utils.packets.implementations;

import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.NMSPacket;

public class PlayerConnection {
    private Object connection;
    public PlayerConnection(Player player) {
        try {
            Object craftplayer = player.getClass().getMethod("getHandle").invoke(player);
            if (NMSUtils.versionId < 17) connection = craftplayer.getClass().getField("playerConnection").get(craftplayer);
            else connection = craftplayer.getClass().getField("b").get(craftplayer);
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }
    public void sendPacket(NMSPacket... packets) {
        try {
            for (NMSPacket packet : packets) 
                connection.getClass().getMethod("sendPacket", NMSUtils.Packet).invoke(connection, packet.getRaw());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}