package me.oreoezi.harmonyboard.utils.packets;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {
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
	public static Constructor<?> getConstructor(String path, Class<?>... parameters) {
		try {
			return getClass(path).getDeclaredConstructor(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Method getMethod(String path, String method, Class<?>... parameters) {
		try {
			return getClass(path).getMethod(method, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
