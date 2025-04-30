package com.majorbasic.project.utils;

import com.majorbasic.project.datastructure.Subject;
import com.majorbasic.project.datastructure.Timetable;

public class Util {
    /**
     * 입력받은 과목이 입력받은 시간표에 있는 과목과 겹치는지 확인
     * @param subject 확인하고 싶은 과목
     * @param timetable 대상 시간표
     * @return 과목 겹침 여부
     */
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

    /**
     * 문자열이 수인지 아닌지 확인
     * @param str 확인하고자 하는 문자열
     * @return 문자열이 수인지 여부
     */
    public static boolean isNumeric(String str) {
        return str.matches("\\d");
    }

    /**
     * 분리되어 있는 요일과 시간 문자열을 하나로 합침
     * @param tuples 요일과 시간
     * @return 합쳐진 요일과 시간 튜플
     */
    public static String[] dayTimeArr(String[] tuples) {
        String[] day = tuples[1].split(",");
        String[] time = tuples[2].split(",");
        if (day.length == 2) {
            if (time.length == 2) {
                day[0] = day[0] + ","  + time[0];
                day[1] = day[1] + "," + time[1];
            }
            else {
                day[0] = day[0] + ","  + time[0];
                day[1] = day[1] + "," + time[0];
            }
        } else {
            day[0] = day[0] + ","  + time[0];
        }

        return day;
    }
}
