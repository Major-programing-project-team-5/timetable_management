package Core.Utils.Calc;

import Core.DataStructure.Subject;
import Core.Utils.findSubjectClass;
import Core.DataStructure.TimetableManager;

import java.io.*;
import java.util.List;

public class calc_utilitySets {
    static final String TIMETABLE_DIR = "data/";
    static final int GRADUATION_CREDIT = 130;

    public static void calcCommand(String userInput) {
        String[] tokens = userInput.trim().split("\\s+");

        switch (tokens[1]) {
            case "total":
                calculateTotalCredits();
                break;

            case "term":
                if (tokens.length != 4) {
                    System.out.println("잘못된 calc term 명령 형식입니다.");
                    return;
                }
                int year = Integer.parseInt(tokens[2]);
                int semester = Integer.parseInt(tokens[3]);
                calculateTermCredits(year, semester);
                break;

            case "remain":
                calculateRemainingCredits();
                break;

            default:
                System.out.println("알 수 없는 calc 명령입니다.");
        }
    }

    // 총 학점 계산
    public static void calculateTotalCredits() {
        int totalCredits = 0;
        for (Timetable timetable : TimetableManager.timetableList) {
            for (Subject subject : timetable.getSubjects()) {
                totalCredits += subject.getCredit();
            }
        }
        System.out.println("전체 이수 학점: " + totalCredits);
    }

    // 특정 학기의 학점 계산
    public static void calculateTermCredits(int year, int semester) {
        Timetable timetable = TimetableManager.getTimetable(year, semester);
        if (timetable == null) {
            System.out.println("시간표 파일이 존재하지 않습니다: " + year + "학년 " + semester + "학기");
            return;
        }

        int totalCredits = 0;
        for (Subject subject : timetable.getSubjects()) {
            totalCredits += subject.getCredit();
        }

        System.out.println(year + "학기 " + semester + " 학점: " + totalCredits);
    }

    // 졸업을 위한 남은 학점 계산
    public static void calculateRemainingCredits() {
        int totalCredits = 0;
        for (Timetable timetable : TimetableManager.timetableList) {
            for (Subject subject : timetable.getSubjects()) {
                totalCredits += subject.getCredit();
            }
        }

        int remainingCredits = GRADUATION_CREDIT - totalCredits;
        System.out.println("잔여 학점: " + remainingCredits + "학점");
    }
}
