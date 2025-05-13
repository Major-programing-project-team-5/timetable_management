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
