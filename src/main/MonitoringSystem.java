package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitoringSystem {
    // Attributes to store and manage students, exams, and rules
    private final Map<String, Student> students; // Using HashMap for O(1) average finding of students by ID
    private final Map<String, Exam> exams;       // Using HashMap for O(1) average finding of exams by ID
    private final List<Rule> rules;              // List of rules to apply during monitoring

    public MonitoringSystem() {
        this.students = new HashMap<>();
        this.exams = new HashMap<>();
        this.rules = new ArrayList<>();
    }


    public void addStudent(Student student) {
        students.put(student.getStudentId(), student);
        System.out.println("Student added: " + student.getName());
    }

    public void addExam(Exam exam) {
        exams.put(exam.getExamId(), exam);
        System.out.println("Exam added: " + exam.getCourseName());
    }

//  to add a new rule to the system
    public void addRule(Rule rule) {
        rules.add(rule);
        System.out.println("Rule added: " + rule.getClass().getSimpleName());
    }

    /**
     * Here we are simulating the students activity . In real time project the data will be obtained
     * from the api calls of monitor or webcam or keyboard strokes.
     * This method also adds the event to the respective student s behavior log.
     */
    public void simulateStudentActivity(String studentId, String examId, BehaviorEvent.EventType type, String description) {
        Student student = students.get(studentId);
        Exam exam = exams.get(examId);

        if (student == null) {
            System.err.println("Error: Student with ID " + studentId + " not found.");
            return;
        }
        if (exam == null) {
            System.err.println("Error: Exam with ID " + examId + " not found.");
            return;
        }

        BehaviorEvent event = new BehaviorEvent(type, description);
        student.addEvent(event);
       System.out.println("event for " + student.getName() + " on " + exam.getCourseName() + ": " + event);
    }

    /**
     * Generates a monitoring report for a particular student and exam by applying all pre-defined rules.
     *
     * @param studentId The ID of the student to generate the report for.
     * @param examId The ID of the exam context.
     * @return A consolidated DetectionReport for the student on the specified exam.
     */
    public DetectionReport generateMonitoringReport(String studentId, String examId) {
        Student student = students.get(studentId);
        Exam exam = exams.get(examId);
        if (student == null || exam == null) {
            System.err.println("Cannot generate report: Student or Exam not found.");
            return new DetectionReport(studentId, examId); // return empty report
        }


        DetectionReport finalReport = new DetectionReport(studentId, examId);
        List<BehaviorEvent> studentEvents = student.getBehaviorLog();

        System.out.println("\n--- Generating Report for " + student.getName() + " on " + exam.getCourseName() + " ---");

        for (Rule rule : rules) {
            System.out.println("  Applying rule: " + rule.getClass().getSimpleName());
            DetectionReport ruleReport = rule.apply(student, exam, studentEvents);
            //Respective apply method will be called due to polymorphism

            // Check if this  rule found anything suspicious
            if (ruleReport.isSuspicious()) {
                //  add its reasons to the final  report
                for (String specificReason : ruleReport.getFlaggedReasons()) {

                    finalReport.addReason(String.format("[%s] %s", rule.getClass().getSimpleName(), specificReason));
                }
            }
        }
        System.out.println("--- Report Generation Complete ---");
        return finalReport;
    }
}