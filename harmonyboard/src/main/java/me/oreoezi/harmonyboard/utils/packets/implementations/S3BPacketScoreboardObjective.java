package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.NMSPacket;

public class S3BPacketScoreboardObjective extends NMSPacket {
    private Object packet;
    @Override
    public Object getRaw() {
        return packet;
    }
    public S3BPacketScoreboardObjective(ScoreboardObjective objective, int state) {
        try {
            packet = NMSUtils.S3B.getDeclaredConstructor(objective.getRaw().getClass(), int.class).newInstance(objective.getRaw(), state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
