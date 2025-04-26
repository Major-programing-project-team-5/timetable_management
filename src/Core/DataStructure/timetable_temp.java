package Core.DataStructure;

import java.util.ArrayList;

public class Timetable {
    private ArrayList<Subject> subjects;
    private String semester; // 예: 2025-1학기

    public Timetable(String semester) {
        this.semester = semester;
        this.subjects = new ArrayList<>();
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}