package main;

public class Exam {
    private final String examId;
    private final String courseName;
    private final long durationMillis;

    public Exam(String examId, String courseName, long durationMinutes) {
        this.examId = examId;
        this.courseName = courseName;
        this.durationMillis = durationMinutes * 60 * 1000; //For  converting the  minutes to milliseconds
    }

    public String getExamId() {
        return examId;
    }

    public String getCourseName() {
        return courseName;
    }

    public long getDurationMillis() {
        return durationMillis;
    }

    @Override
    public String toString() {
        return "Exam [ID=" + examId + ", Course=" + courseName + ", Duration=" + (durationMillis / (1000 * 60)) + " mins]";
    }
}