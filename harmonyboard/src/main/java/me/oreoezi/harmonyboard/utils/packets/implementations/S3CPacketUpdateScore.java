package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.NMSPacket;

public class S3CPacketUpdateScore extends NMSPacket {
    private Object s3c;
    @Override
    public Object getRaw() {
        return s3c;
    }
    public S3CPacketUpdateScore(ScoreboardScore score) {
        try {
            s3c = NMSUtils.S3C.getDeclaredConstructor(score.getRaw().getClass()).newInstance(score.getRaw());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public S3CPacketUpdateScore(Object change, String objective, String player, int score) {
        try {
            s3c = NMSUtils.S3C.getDeclaredConstructor(change.getClass(), String.class, String.class, int.class).newInstance(change, objective, player, score);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
