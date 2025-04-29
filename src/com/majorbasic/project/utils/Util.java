package com.majorbasic.project.utils;

import com.majorbasic.project.datastructure.Subject;
import com.majorbasic.project.datastructure.Timetable;

public class Util {
    public static boolean isTimeConflicted(Subject subject, Timetable timetable) {
        if (timetable == null) {
            return false;
        }

        for (Subject existingSubject : timetable.getSubjects()) {
            // 기존 시간표의 과목과 비교하여 시간 겹침 여부를 확인
            if (existingSubject.getSubjectCode().equals(subject.getSubjectCode()) ||
                    existingSubject.getCategory().equals(subject.getCategory())) {
                return true; // 겹치는 과목이 존재하면 true 반환
            }
        }
        return false;  // 겹치는 과목이 없으면 false
    }

    public static boolean isNumeric(String str) {
        return str.matches("\\d");
    }

    public static boolean isTimetableCorrect(int year, int semester) {
        if(year > 4 || year < 1 || semester > 2 || semester < 1) {
            System.out.println("학년 또는 학기의 숫자가 범위를 넘어섰습니다");
            return false;
        }
        return true;
    }

    public static boolean isTimetableCorrect(String year, String semester) {
        return isTimetableCorrect(Integer.parseInt(year), Integer.parseInt(semester));
    }
}
