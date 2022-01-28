package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.NMSPacket;

public class S3EPacketTeams extends NMSPacket {
    private Object s3e;
    @Override
    public Object getRaw() {
        return s3e;
    }
    public S3EPacketTeams(ScoreboardTeam team, int state) {
        try {
            s3e = NMSUtils.S3E.getDeclaredConstructor(team.getRaw().getClass(), int.class).newInstance(team.getRaw(), state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
