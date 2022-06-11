package me.oreoezi.harmonyboard.utils.packets.implementations;

import org.bukkit.entity.Player;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils;

public class PlayerConnection {
    private Object playerConnection;
    public PlayerConnection(Player player) {
        try {
            Object craftplayer = player.getClass().getMethod("getHandle").invoke(player);
            if (NMSUtils.versionId < 17) playerConnection = craftplayer.getClass().getField("playerConnection").get(craftplayer);
            else playerConnection = craftplayer.getClass().getField("b").get(craftplayer);
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }
    public void sendPacket(Packet... packets) {
        try {
            for (Packet packet : packets) 
                playerConnection.getClass().getMethod("sendPacket", Packet.packetClass).invoke(playerConnection, packet.getPacketObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}