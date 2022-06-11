package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.ClassType;

public class IChatBaseComponent {
    public static Class<?> chatBaseComponentInterface = NMSUtils.getNMSClass("IChatBaseComponent", ClassType.Chat);
    public static Object componentFromString(String s) {
        return null;
    }
}
