package com.majorbasic.project.datastructure;

import java.util.Objects;

/**
 * 날짜 - 시간을 더 편하게 묶어서 저장하기 위해 만든 자료구조.
 */
public class DayTime {
    public String day;
    public int StartTimeHour;
    public int StartTimeMin;
    public int EndTimeHour;
    public int EndTimeMin;

    public DayTime(String day, String time){
        this.day = day;
        String[] temp = time.split("~");
        String[][] timeInfo = {temp[0].split(":"), temp[1].split(":")};
        this.StartTimeHour = Integer.parseInt(timeInfo[0][0]);
        this.StartTimeMin = Integer.parseInt(timeInfo[0][1]);
        this.EndTimeHour = Integer.parseInt(timeInfo[1][0]);
        this.EndTimeMin = Integer.parseInt(timeInfo[1][1]);
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return String.format("%02d:%02d~%02d:%02d", StartTimeHour, StartTimeMin, EndTimeHour, EndTimeMin);
    }

    @Override
    public String toString() {
        return day + "," + StartTimeHour + ":" + StartTimeMin + "~" + EndTimeHour + ":" + EndTimeMin;
    }
    public static boolean isOverlapping(DayTime t1, DayTime t2) {
        int t1Start = t1.StartTimeHour * 60 + t1.StartTimeMin;
        int t1End = t1.EndTimeHour * 60 + t1.EndTimeMin;
        int t2Start = t2.StartTimeHour * 60 + t2.StartTimeMin;
        int t2End = t2.EndTimeHour * 60 + t2.EndTimeMin;

        return t1Start < t2End && t2Start < t1End; // 시간이 겹치는 경우
    }

    //여기도 비슷합니다.
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DayTime dayTime = (DayTime) object;
        return StartTimeHour == dayTime.StartTimeHour && StartTimeMin == dayTime.StartTimeMin && EndTimeHour == dayTime.EndTimeHour && EndTimeMin == dayTime.EndTimeMin && Objects.equals(day, dayTime.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, StartTimeHour, StartTimeMin, EndTimeHour, EndTimeMin);
    }
}
