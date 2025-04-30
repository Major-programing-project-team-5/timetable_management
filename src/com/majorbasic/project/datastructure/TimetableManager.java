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
        if(year > 4 || year < 1 || semester > 2 || semester < 1) {
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
}
