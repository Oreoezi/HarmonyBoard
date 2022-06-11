package me.oreoezi.harmonyboard.utils.packets.implementations;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.ClassType;

public class S3CPacketUpdateScore extends Packet {
    private static Class<?> S3C = NMSUtils.getNMSClass("PacketPlayOutScoreboardScore", ClassType.Packet);
    private Object packetObject;
    @Override
    public Object getPacketObject() {
        return packetObject;
    }
    public S3CPacketUpdateScore(ScoreboardScore score) {
        try {
            packetObject = S3C.getDeclaredConstructor(score.getRaw().getClass()).newInstance(score.getRaw());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public S3CPacketUpdateScore(Object change, String objective, String player, int score) {
        try {
            packetObject = S3C.getDeclaredConstructor(change.getClass(), String.class, String.class, int.class).newInstance(change, objective, player, score);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
