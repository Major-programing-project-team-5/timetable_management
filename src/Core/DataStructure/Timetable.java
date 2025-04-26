package Core.DataStructure;

import java.util.ArrayList;
import java.util.Objects;

public class Timetable {
    private ArrayList<Subject> subjects;
    private int semester; // 학기
    private int year; // 년도

    public Timetable(int year, int semester, ArrayList<Subject> subjects) {
        this.semester = semester;
        this.subjects = subjects;
        this.year = year;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public int getSemester() {
        return semester;
    }

    public int getYear() {
        return year;
    }

    /**
     * 시간표는 학기와 년도가 같으면 같은 시간표로 취급합니다.
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Timetable timetable = (Timetable) object;
        return semester == timetable.semester && year == timetable.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(semester, year);
    }
}