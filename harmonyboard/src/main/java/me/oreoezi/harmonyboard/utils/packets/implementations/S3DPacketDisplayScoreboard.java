package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.ClassType;

public class S3DPacketDisplayScoreboard extends Packet {
    private Object packetObject;
    public static Class<?> S3D = NMSUtils.getNMSClass("PacketPlayOutScoreboardDisplayObjective", ClassType.Packet);
    @Override
    public Object getPacketObject() {
        return packetObject;
    }
    public S3DPacketDisplayScoreboard(int state, ScoreboardObjective objective) {
        try {
            packetObject = S3D.getDeclaredConstructor(int.class, objective.getRaw().getClass()).newInstance(state, objective.getRaw());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
