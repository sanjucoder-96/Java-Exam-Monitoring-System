package main;

import java.util.ArrayList;
import java.util.List;

public class BrowserSwitchRule implements Rule {
    private final int maxSwitches;
    private final long timeWindowMillis; // in milliseconds

    public BrowserSwitchRule(int maxSwitches, long timeWindowSeconds) {
        this.maxSwitches = maxSwitches;
        this.timeWindowMillis = timeWindowSeconds * 1000; // Converting the seconds to milliseconds
    }

    @Override
    public DetectionReport apply(Student student, Exam exam, List<BehaviorEvent> events) {
        DetectionReport report = new DetectionReport(student.getStudentId(), exam.getExamId());

        //Filter for only browser events
        List<BehaviorEvent> browserSwitchEvents = new ArrayList<>();
        for (BehaviorEvent event : events) {
            if (event.getType() == BehaviorEvent.EventType.BROWSER_SWITCH) { // Check the condition
                browserSwitchEvents.add(event); // Add to the new list if condition is met
            }
        }

        //Apply the Sliding Window logic
        for (int i = 0; i < browserSwitchEvents.size(); i++) {
            BehaviorEvent currentEvent = browserSwitchEvents.get(i);
            long windowStartTime = currentEvent.getTimestamp() - timeWindowMillis;
            int count = 0;


            for (int j = i; j >= 0; j--) {
                BehaviorEvent prevEvent = browserSwitchEvents.get(j);
                if (prevEvent.getTimestamp() >= windowStartTime) {
                    count++;
                } else {
                    // Events are already chronologically(according to time ), so we can stop here
                    break;
                }
            }

            // Check if the switches are more than the set threshold
            if (count > maxSwitches) {
                report.addReason(String.format("Excessive browser switches detected (%d in %d seconds) around %s.",
                        count, timeWindowMillis / 1000, currentEvent.toString()));
            }
        }
        return report;
    }
}