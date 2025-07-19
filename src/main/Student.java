package main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student {
    private final String studentId;
    private final String name;
    private final List<BehaviorEvent> behaviorLog;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.behaviorLog = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void addEvent(BehaviorEvent event) {
        behaviorLog.add(event);
    }

    public List<BehaviorEvent> getBehaviorLog() {
        return Collections.unmodifiableList(behaviorLog); // We do it so that the event log  is not changed or manipulated by anybody a
        // and its only readable
    }

    @Override
    public String toString() {
        return "Student [ID=" + studentId + ", Name=" + name + "]";
    }
}