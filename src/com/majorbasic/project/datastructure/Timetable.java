package com.majorbasic.project.datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Timetable {
    private final int max_credit = 24; // 최대 학점
    private Map<Subject, String> subjects; // Subject는 과목, String은 성적
    private Map<Subject, Boolean> isReTake; // 재수강 여부
    private final int semester; // 학기
    private final int year;
    private final boolean isSeasonal; // 계절학기 여부

    public boolean isSeason() {
        return isSeasonal;
    }

    // 생성자: 기존 과목-성적 Map을 받아 초기화
    public Timetable(int year, int semester, Map<Subject, String> subjects) {
        this.year = year;
        this.semester = semester;
        this.subjects = new HashMap<>(subjects);
        this.isReTake = new HashMap<>();
        this.isSeasonal = false;
    }

    // 빈 시간표 생성
    public Timetable(int year, int semester) {
        this.year = year;
        this.semester = semester;
        this.subjects = new HashMap<>();
        this.isReTake = new HashMap<>();
        this.isSeasonal = false;
    }

    public Timetable(int year, int semester, boolean isSeasonal) {
        this.year = year;
        this.semester = semester;
        this.subjects = new HashMap<>();
        this.isReTake = new HashMap<>();
        this.isSeasonal = isSeasonal;
    }

    public Timetable(int year, int semester, List<Subject> subjectList) {
        this.year = year;
        this.semester = semester;
        this.subjects = new HashMap<>();
        this.isReTake = new HashMap<>();
        for (Subject sub : subjectList) {
            subjects.put(sub, null);
            isReTake.put(sub, false);
        }
        this.isSeasonal = false;
    }

    public Timetable(int year, int semester, List<Subject> subjectList, boolean isSeasonal) {
        this.year = year;
        this.semester = semester;
        this.subjects = new HashMap<>();
        this.isReTake = new HashMap<>();
        for (Subject sub : subjectList) {
            subjects.put(sub, null);
            isReTake.put(sub, false);
        }
        this.isSeasonal = isSeasonal;
    }

    // 과목 추가: 과목과 성적
    public void addSubject(Subject subject, String grade) {
        subjects.put(subject, grade);
        isReTake.put(subject, false);
    }

    // 과목 추가: 성적 없이
    public void addSubject(Subject subject) {
        subjects.put(subject, null);
        isReTake.put(subject, false);
    }

    // 과목 추가: 성적과 재수강 여부
    public void addSubject(Subject subject, String grade, boolean retaken) {
        subjects.put(subject, grade);
        isReTake.put(subject, retaken);
    }

    // 성적 조회
    public String getGrade(Subject subject) {
        return subjects.get(subject);
    }

    // 재수강 여부 조회
    public boolean isRetaken(Subject subject) {
        return isReTake.getOrDefault(subject, false);
    }

    /**
     * 학수번호 배열을 통해 성적 설정
     */
    public void setGrade(String[] subjectInfo, String grade) {
        if (subjectInfo.length != 3) {
            System.out.println("과목 튜플 정보를 제대로 입력해주세요");
            return;
        }
        Subject temp_sub = SubjectManager.findSubject(subjectInfo);
        if (subjects.containsKey(temp_sub)) {
            if (isRetaken(temp_sub) && "A+".equals(grade)) {
                System.out.println("재수강 과목은 A+를 받을 수 없습니다. A로 처리됩니다.");
                grade = "A";
            }
            subjects.replace(temp_sub, grade);
        } else {
            System.out.println("시간표에 그런 과목이 존재하지 않습니다");
        }
    }


    /**
     * 과목 코드로 성적 설정
     */
    public void setGrade(String subjectInfo, String grade) {
        Subject temp_sub = SubjectManager.findSubject(subjectInfo);
        if (subjects.containsKey(temp_sub)) {
            if (isRetaken(temp_sub) && "A+".equals(grade)) {
                System.out.println("재수강 과목은 A+를 받을 수 없습니다. A로 처리됩니다.");
                grade = "A";
            }
            subjects.replace(temp_sub, grade);
        } else {
            System.out.println("시간표에 그런 과목이 존재하지 않습니다");
        }
    }


    public Map<Subject, String> getSubjects() {
        return subjects;
    }

    public Map<Subject, Boolean> getRetakeMap() {
        return isReTake;
    }

    public int getSemester() {
        return semester;
    }

    public int getYear() {
        return year;
    }

    public int getMax_credit() {
        return max_credit;
    }

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
                ", isReTake=" + isReTake +
                ", semester=" + semester +
                ", year=" + year +
                ", isSeasonal=" + isSeasonal +
                '}';
    }
}
