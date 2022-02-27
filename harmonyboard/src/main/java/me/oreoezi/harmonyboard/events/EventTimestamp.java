package me.oreoezi.harmonyboard.events;

public class EventTimestamp {
    private EventEnum event;
    private long date;
    public EventTimestamp(EventEnum event) {
        this.event = event;
        this.date = System.currentTimeMillis();
    }
    public EventEnum getEvent() {
        return event;
    }
    public long getDate() {
        return date;
    }
}
