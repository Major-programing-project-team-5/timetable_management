package com.majorbasic.project.utils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.majorbasic.project.datastructure.*;
import static com.majorbasic.project.utils.Util.*;

/* =========================
Verify에 필요한 명령어들에 대한 정의를 만들어둔 클래스.

[명령어 정보]

1. 과목 정보 확인하기

verify subject 과목명 날짜 시간 과목 코드

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
            } else if (tokens.length == 2 && tokens[1].equals("graduation")) {
                verifyGraduation();
            } else if (tokens.length == 3) {
                try {
                    int year = Integer.parseInt(tokens[1]);
                    int semester = Integer.parseInt(tokens[2]);

                    if(!TimetableManager.isTimetableCorrect(year, semester)) {
                        return;
                    }
                    verifyTimetable(year, semester);
                } catch (NumberFormatException e) {
                    System.out.println("연도와 학기는 숫자로 입력해야 합니다.");
                }
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
            System.out.println(year + "년 " + semester + "학기 시간표를 찾을 수 없습니다.");
            return;
        }

        // 시간표용 boolean 배열
        boolean[][] timetableArray = new boolean[5][22];

        // 과목 이름 확인용 String 배열
        String[][] symbolGrid = new String[5][22];
        for (String[] row : symbolGrid) {
            Arrays.fill(row, "○"); //빈칸 초기화
        }

        // 과목-기호 연결을 위한 맵과 기호 리스트
        Map<String, String> subjectLegend = new LinkedHashMap<>();
        List<String> symbols = Arrays.asList("■", "★", "▲", "▼", "◆", "♥");
        int symbolCounter = 0;

        for (Subject subject : timetable.getSubjects()) {
            // 새로운 과목에 새로운 기호 할당
            if (!subjectLegend.containsKey(subject.getSubjectName())) {
                String symbol = (symbolCounter < symbols.size()) ? symbols.get(symbolCounter++) : "?";
                subjectLegend.put(subject.getSubjectName(), symbol);
            }
            String currentSymbol = subjectLegend.get(subject.getSubjectName());

            if (subject.getSubjectDayTimes() == null) continue;

            for (DayTime dayTime : subject.getSubjectDayTimes()) {
                if (dayTime == null) continue;

                int dayIndex = dayToIndex(dayTime.day);
                if (dayIndex != -1 && dayTime.StartTimeHour >= 9 && dayTime.EndTimeHour <= 21) {
                    int startRow = (dayTime.StartTimeHour - 9) * 2 + (dayTime.StartTimeMin / 30);
                    int endRow = (dayTime.EndTimeHour - 9) * 2 + (dayTime.EndTimeMin / 30);

                    for (int i = startRow; i < endRow; i++) {
                        if (i >= 0 && i < 22) {
                            // boolean 배열에는 true 값을 할당
                            timetableArray[dayIndex][i] = true;
                            // String 배열에는 해당 과목의 기호를 할당
                            symbolGrid[dayIndex][i] = currentSymbol;
                        }
                    }
                }
            }
        }

        // 시간표 출력
        System.out.println("===== 시간표 (" + year + "년 " + semester + "학기) =====");
        System.out.print("시간\\요일\t월\t화\t수\t목\t금\n");

        for (int i = 0; i < 22; i++) {
            int hour = 9 + (i / 2);
            boolean isHalfHour = (i % 2 != 0);

            String timeLabel;
            if (isHalfHour) {
                timeLabel = hour + "시 30분";
            } else {
                timeLabel = hour + "시";
            }
            System.out.printf("%-10s", timeLabel);

            for (int day = 0; day < 5; day++) {
                System.out.print(symbolGrid[day][i] + "\t");
            }
            System.out.println();
        }

        // 기호별로 나타내는 과목 출력
        System.out.println();
        for (Map.Entry<String, String> entry : subjectLegend.entrySet()) {
            System.out.println(entry.getValue() + " : " + entry.getKey());
        }
    }

    public void verifyGraduation(){
        System.out.println("==== 졸업 요건 정보 ====");

        // 졸업 요건 정보 불러오기
        System.out.println("총 이수 요구 학점: " + Graduation.totalCreditsRequired + "학점"); //

        // 과목 구분별 요구 학점 출력
        if (Graduation.CreditRequiredEachMajor != null && !Graduation.CreditRequiredEachMajor.isEmpty()) {
            System.out.println("\n[과목 구분별 요구 학점]");
            for (Map.Entry<String, Integer> entry : Graduation.CreditRequiredEachMajor.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + "학점");
            }
        }

        // 필수 수강 과목 목록 출력
        if (Graduation.requiredSubject != null && !Graduation.requiredSubject.isEmpty()) {
            System.out.println("\n[필수 수강 과목]");
            for (Subject subject : Graduation.requiredSubject) { //
                System.out.println(subject.toString()); //
            }
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
