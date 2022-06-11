package me.oreoezi.harmonyboard.utils.packets.implementations;

import me.oreoezi.harmonyboard.utils.packets.NMSUtils;
import me.oreoezi.harmonyboard.utils.packets.NMSUtils.ClassType;

public class ChatMessage extends ChatBaseComponent {
    private static Class<?> chatMessageClass = NMSUtils.getNMSClass("ChatMessage", ClassType.Chat);
    private Object message;
    public ChatMessage(String s) {
        try {
            this.message = chatMessageClass.getDeclaredConstructor(String.class).newInstance(s);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public Object getRaw() {
        return message;
    }
}
