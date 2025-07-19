☺package main;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BehaviorEvent {
    public enum EventType {
        MOUSE_OUT_OF_WINDOW,
        EXCESSIVE_TYPING,
        BROWSER_SWITCH,
        IDLE_TIME,
        UNKNOWN_PERSON,
        OTHER_SUSPICIOUS_ACTIVITY,
        NORMAL_ACTIVITY
    }

    private final long timestamp;
    private final EventType type;
    private final String description;

    public BehaviorEvent(EventType type, String description) {
        this.timestamp = System.currentTimeMillis();
        this.type = type;
        this.description = description;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public EventType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(timestamp),
                java.time.ZoneId.systemDefault());
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        return String.format("[%s] %s: %s", dateTime.format(format), type, description);
    }
}