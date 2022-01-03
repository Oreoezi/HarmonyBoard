package me.oreoezi.harmonyboard.datamanagers;

import java.util.HashMap;

public class Row {
    private HashMap<String, Object> indexes;
    public Row() {
        indexes = new HashMap<String, Object>();
    }
    public void setColumnValue(String column, Object value) {
        indexes.put(column, value);
    }
    public int getInt(String column) {
        return (int) indexes.get(column);
    }
    public String getString(String column) {
        return (String) indexes.get(column);
    }
}
