package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit; // For time gaps

public class ExamMonitoringApp extends Application {

    private TextArea logTextArea; // The JavaFX component

    @Override
    public void start(Stage primaryStage) {
        logTextArea = new TextArea();
        logTextArea.setEditable(false);
        logTextArea.setWrapText(true);
        logTextArea.setPrefRowCount(25);
        logTextArea.setPrefColumnCount(80);


        VBox root = new VBox(logTextArea);
        root.setSpacing(10);

        Scene scene = new Scene(root, 700, 500);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

        primaryStage.setTitle("Automated Exam Monitoring Log");
        primaryStage.setScene(scene);
        primaryStage.show();

      // redirect printing statements to gui
        redirectSystemOutToTextArea();

        //for making ui not freeze
        new Thread(this::runSimulation).start();
    }


    private void redirectSystemOutToTextArea() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {

                Platform.runLater(() -> logTextArea.appendText(String.valueOf((char) b)));
            }
        };

        System.setOut(new PrintStream(out, true));

    }

    // method contains the main simulation logic, simulating events
    private void runSimulation() {

        MonitoringSystem monitoringSystem = new MonitoringSystem();


        Student student1 = new Student("S001", " Kiran");
        Student student2 = new Student("S002", "Jenny");
        monitoringSystem.addStudent(student1);
        monitoringSystem.addStudent(student2);

        Exam javaExam = new Exam("E101", "Java Programming Midterm", 60); // 60 minutes
        Exam historyExam = new Exam("E102", "Linear Algebra Final", 90);  // 90 minutes
        monitoringSystem.addExam(javaExam);
        monitoringSystem.addExam(historyExam);

        monitoringSystem.addRule(new BrowserSwitchRule(2, 30)); // max 2 browser switches in 30 seconds allowed
        monitoringSystem.addRule(new IdleTimeRule(10));         // max 10 seconds idle time allowed
        monitoringSystem.addRule(new UnknownPersonRule());      // detect unknown person

        try {

            System.out.println("\n--- Simulating Kiran's Java Exam ---");

            monitoringSystem.simulateStudentActivity(student1.getStudentId(), javaExam.getExamId(),
                    BehaviorEvent.EventType.NORMAL_ACTIVITY, "Exam started");
            TimeUnit.SECONDS.sleep(5); // Wait 5 seconds

            monitoringSystem.simulateStudentActivity(student1.getStudentId(), javaExam.getExamId(),
                    BehaviorEvent.EventType.NORMAL_ACTIVITY, "Answering question 1");
            TimeUnit.SECONDS.sleep(10);


            monitoringSystem.simulateStudentActivity(student1.getStudentId(), javaExam.getExamId(),
                    BehaviorEvent.EventType.BROWSER_SWITCH, "Switched to another application");
            TimeUnit.SECONDS.sleep(2); // Short time gap
            monitoringSystem.simulateStudentActivity(student1.getStudentId(), javaExam.getExamId(),
                    BehaviorEvent.EventType.BROWSER_SWITCH, "Switched back to exam");
            TimeUnit.SECONDS.sleep(3); // Short time gap
            monitoringSystem.simulateStudentActivity(student1.getStudentId(), javaExam.getExamId(),
                    BehaviorEvent.EventType.BROWSER_SWITCH, "Switched away again (3rd in 5s)");
            TimeUnit.SECONDS.sleep(1);
            monitoringSystem.simulateStudentActivity(student1.getStudentId(), javaExam.getExamId(),
                    BehaviorEvent.EventType.NORMAL_ACTIVITY, "Resumed typing");
            TimeUnit.SECONDS.sleep(20);

            // events  to trigger the IdleTimeRule
            monitoringSystem.simulateStudentActivity(student1.getStudentId(), javaExam.getExamId(),
                    BehaviorEvent.EventType.IDLE_TIME, "Student became idle");
            TimeUnit.SECONDS.sleep(15);
            monitoringSystem.simulateStudentActivity(student1.getStudentId(), javaExam.getExamId(),
                    BehaviorEvent.EventType.NORMAL_ACTIVITY, "Resumed activity after idle");
            TimeUnit.SECONDS.sleep(5);

            //  event  to trigger the UnknownPersonRule
            monitoringSystem.simulateStudentActivity(student1.getStudentId(), javaExam.getExamId(),
                    BehaviorEvent.EventType.UNKNOWN_PERSON, "Another person entered frame");
            TimeUnit.SECONDS.sleep(10);

            monitoringSystem.simulateStudentActivity(student1.getStudentId(), javaExam.getExamId(),
                    BehaviorEvent.EventType.NORMAL_ACTIVITY, "Exam ended");

            //  display the monitoring report for Kiran
            DetectionReport KiranReport = monitoringSystem.generateMonitoringReport(student1.getStudentId(), javaExam.getExamId());
            System.out.println(KiranReport);

            // --- Simulate Jenny's Linear Algebra Exam (Scenario: Normal behavior) ---
            System.out.println("\n--- Simulating Bob's History Exam ---");
            monitoringSystem.simulateStudentActivity(student2.getStudentId(), historyExam.getExamId(),
                    BehaviorEvent.EventType.NORMAL_ACTIVITY, "Exam started");
            TimeUnit.SECONDS.sleep(15);
            monitoringSystem.simulateStudentActivity(student2.getStudentId(), historyExam.getExamId(),
                    BehaviorEvent.EventType.NORMAL_ACTIVITY, "Reading questions");
            TimeUnit.SECONDS.sleep(20);
            monitoringSystem.simulateStudentActivity(student2.getStudentId(), historyExam.getExamId(),
                    BehaviorEvent.EventType.NORMAL_ACTIVITY, "Typing answers");
            TimeUnit.SECONDS.sleep(10);
            monitoringSystem.simulateStudentActivity(student2.getStudentId(), historyExam.getExamId(),
                    BehaviorEvent.EventType.NORMAL_ACTIVITY, "Reviewing answers");
            TimeUnit.SECONDS.sleep(5);
            monitoringSystem.simulateStudentActivity(student2.getStudentId(), historyExam.getExamId(),
                    BehaviorEvent.EventType.NORMAL_ACTIVITY, "Submitted exam");


            DetectionReport jennyReport = monitoringSystem.generateMonitoringReport(student2.getStudentId(), historyExam.getExamId());
            System.out.println(jennyReport);

        } catch (InterruptedException e) {
            System.err.println("Simulation interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }


    public static void main(String[] args) {
        launch(args); // This method calls the JavaFX Start()
    }
}
