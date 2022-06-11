package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.ClassType;

public class S3EPacketTeams extends Packet {
    private Object packetObject;
    private static Class<?> S3E = NMSUtils.getNMSClass("PacketPlayOutScoreboardTeam", ClassType.Packet);
    @Override
    public Object getPacketObject() {
        return packetObject;
    }
    public S3EPacketTeams(ScoreboardTeam team, int state) {
        try {
            if (NMSUtils.versionId < 17)
                packetObject = S3E.getDeclaredConstructor(team.getRaw().getClass(), int.class).newInstance(team.getRaw(), state);
            else if (state == 0 || state == 2)
                packetObject = S3E.getMethod("a", team.getRaw().getClass(), boolean.class).invoke(null, team.getRaw(), state==0);
            else packetObject = S3E.getMethod("a", team.getRaw().getClass()).invoke(null, team.getRaw());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
