package main;

import java.util.List;

public interface Rule {
    /**
     * Applies this rule to a student's behavior events during an exam.
     *
     * @param student The student being proctored.
     * @param exam The exam context.
     * @param events The list of behavior events for the student during this exam.
     * @return A DetectionReport indicating if the rule was triggered and why.
     */
    DetectionReport apply(Student student, Exam exam, List<BehaviorEvent> events);
}
