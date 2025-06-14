package com.majorbasic.project.datastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Timetable {
    private final Map<Subject, String> subjects; // Subject는 과목, String은 성적
    private final int semester; // 학기
    private final int year;     // 연도

    // 생성자: 기존의 과목-성적 Map을 받아 초기화
    public Timetable(int year, int semester, Map<Subject, String> subjects) {
        this.year = year;
        this.semester = semester;
        this.subjects = new HashMap<>(subjects);
    }

    // 생성자: 빈 시간표 생성
    public Timetable(int year, int semester) {
        this.year = year;
        this.semester = semester;
        this.subjects = new HashMap<>();
    }

    // 과목 추가: 과목과 성적을 함께 추가
    public void addSubject(Subject subject, String grade) {
        subjects.put(subject, grade);
    }

    // 성적 조회
    public String getGrade(Subject subject) {
        return subjects.get(subject);
    }

    // 전체 과목 및 성적 반환
    public Map<Subject, String> getSubjects() {
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
     * @param object 비교할 객체
     * @return 동일한 학기+년도면 true, 아니면 false
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Timetable timetable = (Timetable) object;
        return year == timetable.year && semester == timetable.semester;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, semester);
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
