package com.majorbasic.project.datastructure;

import java.util.ArrayList;
import java.util.Objects;

public class Timetable {
    private final ArrayList<Subject> subjects;
    private final int semester; // 학기
    private final int year; // 년도

    public Timetable(int year, int semester, ArrayList<Subject> subjects) {
        this.semester = semester;
        this.subjects = subjects;
        this.year = year;
    }

    public Timetable(int year, int semester) {
        this.year = year;
        this.semester = semester;
        this.subjects = new ArrayList<>();
    }

    public void addSubject(Subject subject){
        subjects.add(subject);
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
     * @param object 같은 객체인지 검사할 객체
     * @return 만약 같은 객체이면 true, 아니면 false 반환
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

    @Override
    public String toString() {
        return "Timetable{" +
                "subjects=" + subjects +
                ", semester=" + semester +
                ", year=" + year +
                '}';
    }
}