package me.oreoezi.harmonyboard.utils;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;

public class PacketUtils {
    public static Object getValue(Object instance, String name) {
		Object result = null;	
		try {
			Field field = instance.getClass().getDeclaredField(name);
			field.setAccessible(true);
			result = field.get(instance);
			field.setAccessible(false);			
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
    public static Object setValue(Object instance, String name, Object value) {	
		try {
			Field field = instance.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(instance, value);
			field.setAccessible(false);			
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
		return instance;
	}
    public static Class<?> getClass(String path) {
        try {
            return Class.forName(path);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
	public static String[] splitLine(String text) {
		String prefix = text.substring(0, text.length()/2);
		String suffix = "";
		if (prefix.endsWith("§")) {
			prefix = prefix.substring(0, prefix.length()-1);
			suffix += "§";
			suffix += text.substring(text.length()/2, text.length());
		}
		else {
			suffix += ChatColor.getLastColors(prefix);
			suffix += text.substring(text.length()/2, text.length());
		}
		return new String[] {prefix, suffix};
	}
}
