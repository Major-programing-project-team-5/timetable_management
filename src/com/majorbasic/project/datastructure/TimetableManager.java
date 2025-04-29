package com.majorbasic.project.datastructure;

import com.majorbasic.project.exception.addException;

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
                throw new addException("TimetableManager - addTimeTabletoManager : 시간표가 이미 존재함.");
            }else{
                timetableSets.add(timetable);
                timetableList.add(timetable);
            }
        }catch (addException e){
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
}
