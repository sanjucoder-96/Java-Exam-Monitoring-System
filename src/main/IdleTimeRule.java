package main;
import java.util.List;

public class IdleTimeRule implements Rule {
    private final long maxIdleTime_millis; // in milliseconds

    public IdleTimeRule(long maxIdleTimeSeconds) {
        this.maxIdleTime_millis = maxIdleTimeSeconds * 1000; // Converting the  seconds to milliseconds to maintain a same unit across all the project
    }

    @Override
    public DetectionReport apply(Student student, Exam exam, List<BehaviorEvent> events) {
        DetectionReport report = new DetectionReport(student.getStudentId(), exam.getExamId());

        if (events.isEmpty()) {
            // if no events recorded, and exam has a duration, that means student just sat idle for the whole exam
            // so just return
            if (exam.getDurationMillis() > 0) {
                report.addReason("Student was completely idle for the duration of the exam or had no recorded activity.");
            }
            return report;
        }

        // Check for time student been ideal between consecutive events
        for (int i = 0; i < events.size() - 1; i++) {
            BehaviorEvent current = events.get(i);
            BehaviorEvent next = events.get(i + 1);
            long idleDuration = next.getTimestamp() - current.getTimestamp(); // time difference

            if (idleDuration > maxIdleTime_millis) {
                report.addReason(String.format("Idle time detected for %d seconds between events at %s and %s.",
                        idleDuration / 1000, current.toString(), next.toString())); // dividing by 1000 so that the output is in seconds which is readable format
            }
        }

       // an extra check if student performed some actions and left idle for the remaining time till exam completed
        long timeSinceLastEvent = System.currentTimeMillis() - events.getLast().getTimestamp();

        if (timeSinceLastEvent > maxIdleTime_millis) {
            report.addReason(String.format("Student was idle for %d seconds since last recorded activity (%s).",
                    timeSinceLastEvent / 1000, events.getLast().toString()));
        }

        return report;
    }
}