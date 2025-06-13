package com.majorbasic.project.utils;

import java.util.Arrays;
import com.majorbasic.project.datastructure.*;
import static com.majorbasic.project.utils.Util.*;

/* =========================
Verify에 필요한 명령어들에 대한 정의를 만들어둔 클래스.

[명령어 정보]

1. 과목 정보 확인하기

verify subject 과목명 시간 날짜 과목 코드

해당 과목의 정보를 확인 가능합니다.

ex ) verify subject 데이터베이스 화,목 10:30~12:00,10:30~12:00 3136

목업 1.
    과목명: 데이터베이스
    요일 및 시간:
      화,목
      10:30~12:00,10:30~12:00
    과목 코드: 3136
    학수번호: BBAB12001
    학점: 3
    이수 구분: 전공선택
    강의실: 공C487


2. 시간표 확인하기

verify 년도 학기

해당 년도의 시간표를 확인 가능합니다.

ex) verify 2025 1

목업 1. 


 */
public class VerifyManager {


    /**
     * 튜플 입력 확인해서 기능 수행하는 쪽
     * @param input 입력값
     */
    public void verifyMain(String input) {
        try {
            String[] tokens = input.split("\\s+");

            if (tokens.length == 1) {
                if (TimetableManager.presentTimetable == null) {
                    System.out.println("현재 확인 대상으로 지목된 시간표가 없습니다");
                    return;
                }
                Timetable table = TimetableManager.presentTimetable;
                verifyTimetable(table.getYear(), table.getSemester());
            } else if (tokens[1].equals("subject")) {
                String[] output = Arrays.copyOfRange(tokens, 2, tokens.length);
                verifySubject(output);
            } else if (tokens.length == 3 && isNumeric(tokens[1]) && isNumeric(tokens[2])) {
                //2차때는 테스트 관련 데이터 넣어줘야 함.
                if(!TimetableManager.isTimetableCorrect(tokens[1], tokens[2])) {
                    return;
                }
                verifyTimetable(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
            } else {
                System.out.println("올바른 인자가 아닙니다.");
            }
        } catch (Exception e) {
            System.out.println("명령어 처리 중 오류가 발생했습니다 " + e);
        }
    }

    public void verifySubject(String[] tuples) {
        Subject subject = SubjectManager.findSubject(tuples);

        if (subject == null) {
            System.out.println("해당 과목을 찾을 수 없습니다.");
            return;
        }

        System.out.println("==== 과목 정보 ====");
        System.out.println("과목명: " + subject.getSubjectName());
        System.out.println("요일 및 시간: ");
        for (String dayTime : subject.getSubjectDayTime()) {
            System.out.println("  " + dayTime);
        }
        System.out.println("과목 코드: " + subject.getSubjectCode());
        System.out.println("학수번호: " + subject.getCourseCode());
        System.out.println("학점: " + subject.getCredit());
        System.out.println("이수 구분: " + subject.getCategory());
        System.out.println("강의실: " + subject.getLectureRoom());
    }

    public void verifyTimetable(int year, int semester) {
        Timetable timetable = TimetableManager.getTimetable(year, semester);

        if (timetable == null) {
            System.out.println("해당 시간표를 찾을 수 없습니다.");
            return;
        }

        boolean[][] timetableArray = new boolean[5][22]; // 월(0)~금(4), 9 : 00 ~ 20:00까지. 30분 단위.

        for (Subject subject : timetable.getSubjects()) {

            for (DayTime dayTime : subject.getSubjectDayTimes()) {
                if (dayTime == null) continue;
                int dayIndex = dayToIndex(dayTime.day);
                if (dayIndex != -1) {
                    for (int i = dayTime.StartTimeHour, j = (i-9); i < dayTime.EndTimeHour; i++) {
                        //아래에 이상한 숫자 더하는 과정은 index는 0~22인데 시간 표기는 9~20인 문제 해결 위함.
                        if (i >= 9 && i < 20) {
                            //0 ~ 11
                            if(dayTime.StartTimeMin == 30 && i == dayTime.StartTimeHour){
                                timetableArray[dayIndex][i-8 + j] = true;
                            }
                            else{
                                timetableArray[dayIndex][i-9 + j] = true;
                                timetableArray[dayIndex][i-8 + j] = true;
                                timetableArray[dayIndex][i-7 + j] = true;
                                //두 칸 연속으로 true 박히도록
                            }
                            if(dayTime.EndTimeMin == 30 && i == dayTime.EndTimeHour - 1){
                                timetableArray[dayIndex][i-8 + j] = true;
                            }
                        }
                        j++;

                    }

                }

            }
        }

        // 출력
        System.out.println("==== 시간표 (" + year + "년 " + semester + "학기) ====");
        System.out.print("시간\\요일\t월\t화\t수\t목\t금\n");
        int i = 0;
        for (int period = 0; period < 22; period = period + 2) {
            System.out.print((period + 9 - i) + "시 \t\t");
            for (int day = 0; day < 5; day++) {
                System.out.print((timetableArray[day][period] ? "●" : "○") + "\t");
            }
            System.out.println();
            System.out.print((period + 9 - i) + "시 30분 \t");
            for (int day = 0; day < 5; day++) {
                System.out.print((timetableArray[day][period+1] ? "●" : "○") + "\t");
            }

            System.out.println();
            i++;
        }
    }



    private int dayToIndex(String day) {
        switch (day) {
            case "월": return 0;
            case "화": return 1;
            case "수": return 2;
            case "목": return 3;
            case "금": return 4;
            default: return -1;
        }
    }
}
