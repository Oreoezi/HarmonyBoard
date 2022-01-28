package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.NMSPacket;

public class S3DPacketDisplayScoreboard extends NMSPacket {
    private Object packet;
    @Override
    public Object getRaw() {
        return packet;
    }
    public S3DPacketDisplayScoreboard(int state, ScoreboardObjective objective) {
        try {
            packet = NMSUtils.S3D.getDeclaredConstructor(int.class, objective.getRaw().getClass()).newInstance(state, objective.getRaw());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
