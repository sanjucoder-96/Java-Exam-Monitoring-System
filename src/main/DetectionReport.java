package main;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class DetectionReport {
    private final String studentId;
    private final String examId;
    private final List<String> flagged_reasons;
    private boolean suspicious;

    public DetectionReport(String studentId, String examId) {
        this.studentId = studentId;
        this.examId = examId;
        this.flagged_reasons = new ArrayList<>();
        this.suspicious = false;
    }

    public void addReason(String reason) {
        flagged_reasons.add(reason);
        suspicious = true; // Mark as suspicious if any reason is added
    }

    public boolean isSuspicious() {
        return suspicious;
    }

    public List<String> getFlaggedReasons() {
        return flagged_reasons;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getExamId() {
        return examId;
    }

    @Override
    public String toString() {
        if (!suspicious) {
            return "Report for Student " + studentId + " on Exam " + examId + ": No suspicious activity detected.";
        } else {
            StringJoiner reasonsJoiner = new StringJoiner("\n   - ", "   - ", "");

            for (String reason : flagged_reasons) {
                reasonsJoiner.add(reason);
            }

            return "Report for Student " + studentId + " on Exam " + examId + ": SUSPICIOUS!\n" +
                    "   Reasons:\n" + // Newline after "Reasons:"
                    reasonsJoiner.toString(); // Add the formatted reasons
        }
    }
}