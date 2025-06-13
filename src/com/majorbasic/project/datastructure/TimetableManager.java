package com.majorbasic.project.datastructure;

import com.majorbasic.project.exception.AddException;

import java.util.HashSet;
import java.util.List;

public class TimetableManager {
    public static HashSet<Timetable> timetableSets;
    public static List<Timetable> timetableList;
    public static Timetable presentTimetable;
    /**
     * TimeTableManager에 시간표를 추가하는 메소드입니다.
     * @param timetable 시간표에 추가할 시간표입니다
     */
    public static void addTimeTabletoManager(Timetable timetable){
        try{
            if(timetableSets.contains(timetable)){
                throw new AddException("TimetableManager - addTimeTabletoManager : 시간표가 이미 존재함.");
            }else{
                timetableSets.add(timetable);
                timetableList.add(timetable);
            }
        }catch (AddException e){
            System.out.println(e.getMessage());
        }
    }
    public static Timetable getTimetable(int year, int semester) {
        Timetable temptimeTable = new Timetable(year, semester);
            for (Timetable temp : timetableList) {
                if (temp.equals(temptimeTable)) {
                    return temp;
                }
            }
            return null;
    }

    /**
     * 타임테이블의 학년과 학기의 값이 유효한지 검사
     * @param year 학년
     * @param semester 학기
     * @return 학년과 학기의 값이 유효한지 여부
     */
    public static boolean isTimetableCorrect(int year, int semester) {
        if(year > 2025 || year < 1930 || semester > 2 || semester < 1) {
            System.out.println("학년 또는 학기의 숫자가 범위를 넘어섰습니다");
            return false;
        }
        return true;
    }

    /**
     * 타임테이블의 학년과 학기의 값이 유효한지 검사
     * @param year 학년
     * @param semester 학기
     * @return 학년과 학기의 값이 유효한지 여부
     */
    public static boolean isTimetableCorrect(String year, String semester) {
        return isTimetableCorrect(Integer.parseInt(year), Integer.parseInt(semester));
    }

    public static void editSubject(int year, int semester, String[] subjectInfo) {
        if (subjectInfo.length != 4) {
            System.out.println("[ 과목 튜플 형식이 올바르지 않습니다. ]");
            return;
        }

        Timetable targetTimetable = getTimetable(year, semester);

        if (targetTimetable == null) {
            System.out.println(year + "년" + semester + "학기 시간표를 찾을 수 없습니다.");
            return;
        }

        String targetSubjectName = subjectInfo[0];
        String targetCourseCode = subjectInfo[1];
        int targetCredit;
        try {
            targetCredit = Integer.parseInt(subjectInfo[2]);
        } catch (NumberFormatException e) {
            System.out.println("학점이 숫자가 아닙니다.");
            return;
        }
        String newGrade = subjectInfo[3];

        boolean subjectFound = false;
        for (Subject subject : targetTimetable.getSubjects()) {
            if (subject.getSubjectName().equals(targetSubjectName) &&
                subject.getCourseCode().equals(targetCourseCode) &&
                subject.getCredit() == targetCredit) {
                subject.setGrade(newGrade);
                System.out.println("성적이 추가되었습니다. " + subject.getSubjectName());
                subjectFound = true;
                break;
            }
        }
        if (!subjectFound) {
            System.out.println("해당 조건의 과목을 " + year + "년 " + semester + "학기 시간표에서 찾을 수 없습니다.");
        }
        // temp 시간표에서 subjectInfo에 해당하는 것(subjectInfo[0~(length-1)]이 같은 라인) 찾아서
        // 그 라인을 각 요소가 공백으로 구분된 하나의 string으로 바꾸어 대체.

    }
}
