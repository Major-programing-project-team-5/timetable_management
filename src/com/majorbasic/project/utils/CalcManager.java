package com.majorbasic.project.utils;

import com.majorbasic.project.datastructure.Subject;
import com.majorbasic.project.datastructure.Timetable;
import com.majorbasic.project.datastructure.TimetableManager;

public class CalcManager {
//    1. <calc> <total> 총 학점(전 학기 시간표 대상) 계산
//    2. <calc> <term> 특정 학기 내 학점 계산
//    3. <calc> <remain> 잔여 학점(전 학기 시간표 대상) 계산

    public final int GRADUATION_CREDIT = 130;

    public void calcInput(String userInput) {
        try {
            String[] tokens = userInput.trim().split("\\s+");

            if(tokens.length < 2) {
                System.out.println("인자가 올바르지 않습니다.");
                return;
            }

            switch (tokens[1]) {
                case "total":
                    calculateTotalCredits();
                    break;
                case "term":
                    if (tokens.length != 4) {
                        System.out.println("잘못된 calc term 명령 형식입니다.");
                        return;
                    }
                    if (!(tokens[2].matches("\\d") && tokens[3].matches("\\d"))) {
                        System.out.println("인자가 올바르지 않습니다.");
                        return;
                    }
                    int year = Integer.parseInt(tokens[2]);
                    int semester = Integer.parseInt(tokens[3]);
                    if (year > 4 || year < 1 || semester > 2 || semester < 1) {
                        System.out.println("학년 또는 학기의 숫자가 범위를 넘어섰습니다");
                        return;
                    }
                    calculateTermCredits(year, semester);
                    break;
                case "remain":
                    calculateRemainingCredits();
                    break;
                default:
                    System.out.println("인자가 올바르지 않습니다.");
            }
        } catch (Exception e) {
            System.out.println("명령어 처리 중 오류가 발생했습니다" + e);
        }
    }

    // 조건문에 사용되는 함수들
    // 1. 총 학점을 계산하는 함수
    public void calculateTotalCredits() {
        int totalCredits = calculateTotalCredit();

        if(totalCredits  == -1) {
            return;
        }
        // 총 학점 출력
        System.out.println("전체 이수 학점: " + totalCredits);
    }

    // 2. 특정 학기의 학점 계산
    public void calculateTermCredits(int year, int semester) {
        try {
            int termCredits = 0;
            Timetable timetable = TimetableManager.getTimetable(year, semester);

            if (timetable == null) {
                System.out.println("시간표가 존재하지 않습니다.");
                return;
            }

            // 각 시간표 파일을 순회
            for (Subject subject : timetable.getSubjects()) {
                termCredits += subject.getCredit();
            }

            // 총 학점 출력
            System.out.println("학기 이수 학점: " + termCredits);
        } catch (Exception e) {
            System.out.println("학점 계산 실패" + e);
        }
    }

    // 3. 남은 학점 계산
    public void calculateRemainingCredits() {
        int totalCredits = calculateTotalCredit();

        if (totalCredits == -1) {
            return;
        }

        int remainingCredits = GRADUATION_CREDIT - totalCredits;

        System.out.println("잔여 학점");
        System.out.println("---------");
        System.out.println("이수 학점: " + totalCredits + "학점");
        System.out.println("졸업 요건: " + GRADUATION_CREDIT + "학점");
        System.out.println("남은 학점: " + remainingCredits + "학점");
    }

    private int calculateTotalCredit() {
        try {
            int totalCredits = 0;

            if (TimetableManager.timetableList.isEmpty()) {
                System.out.println("시간표가 존재하지 않습니다.");
                return -1;
            }

            // 각 시간표 파일을 순회
            for (Timetable timetable : TimetableManager.timetableList) {
                for (Subject subject : timetable.getSubjects()) {
                    totalCredits += subject.getCredit();
                }
            }

            return totalCredits;
        } catch (Exception e) {
            System.out.println("학점 계산 실패" + e);
            return -1;
        }
    }
}


