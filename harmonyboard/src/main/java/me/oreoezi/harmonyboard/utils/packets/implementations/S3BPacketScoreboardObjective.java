package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.ClassType;

public class S3BPacketScoreboardObjective extends Packet {
    private Object packetObject;
    private static Class<?> S3B = NMSUtils.getNMSClass("PacketPlayOutScoreboardObjective", ClassType.Packet);
    @Override
    public Object getPacketObject() {
        return packetObject;
    }
    public S3BPacketScoreboardObjective(ScoreboardObjective objective, int state) {
        try {
            packetObject = S3B.getDeclaredConstructor(ScoreboardObjective.scoreboardObjectiveClass, int.class).newInstance(objective.getRaw(), state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
