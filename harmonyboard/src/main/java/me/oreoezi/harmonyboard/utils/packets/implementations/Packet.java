package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.ReflectionUtils;
public class Packet {
    public static Class<?> packetClass = ReflectionUtils.getClass((NMSUtils.versionId < 17 ? NMSUtils.legacyPath : 
    "net.minecraft.network.protocol") +".Packet");
    public Object getPacketObject() {
        return null;
    }
}
