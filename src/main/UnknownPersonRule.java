package main;
import java.util.List;

public class UnknownPersonRule implements Rule {
    public UnknownPersonRule() {

    }

    @Override
    public DetectionReport apply(Student student, Exam exam, List<BehaviorEvent> events) {
        DetectionReport report = new DetectionReport(student.getStudentId(), exam.getExamId());

        //search for unknownperson event in events list
        for (BehaviorEvent event : events) {
            if (event.getType() == BehaviorEvent.EventType.UNKNOWN_PERSON) {
                report.addReason(String.format("Unknown person detected at %s.", event.toString()));
            }
        }
        return report;
    }
}