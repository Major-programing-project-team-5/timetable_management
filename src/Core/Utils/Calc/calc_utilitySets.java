package Core.Utils.Calc;

import Core.DataStructure.Subject;
import Core.Utils.findSubjectClass;
import Core.DataStructure.TimetableManager;

import java.io.*;
import java.util.List;

public class calc_utilitySets {
    static final String DB_PATH = "data/database.csv";
    static final String TIMETABLE_DIR = "data/";
    static final int GRADUATION_CREDIT = 130;

    public static void handleCalcCommand(String userInput) {
        String[] tokens = userInput.trim().split("\\s+");

        try {
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
        } catch (Exception e) {
            System.out.println("명령어 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 1. 전체 학점 계산
    public static void calculateTotalCredits() {
        int totalCredits = 0;

        // 각 시간표의 subjects 리스트를 순회하여 총 학점 계산
        List<Timetable> timetableList = TimetableManager.timetableList;
        for (Timetable timetable : timetableList) {
            for (Subject subject : timetable.getSubjects()) {
                totalCredits += subject.getCredit();
            }
        }

        System.out.println("전체 이수 학점: " + totalCredits);
    }

    // 2. 특정 학기의 학점 계산
    public static void calculateTermCredits(int year, int semester) {
        String fileName = TIMETABLE_DIR + year + "_" + semester + "_timetable.csv";
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("시간표 파일이 존재하지 않습니다: " + fileName);
            return;
        }

        int totalCredits = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            System.out.println(year + "학년 " + semester + "학기 학점 계산");

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 1) {
                    String subjectName = tokens[0].trim();
                    Subject subject = findSubjectClass.findSubject(new String[]{subjectName});
                    if (subject != null) {
                        int credit = subject.getCredit();
                        System.out.println(" - " + subjectName + ": " + credit + "학점");
                        totalCredits += credit;
                    }
                }
            }

            System.out.println("총 학점: " + totalCredits);
        } catch (IOException e) {
            System.out.println("시간표 파일을 읽는 도중 오류 발생: " + e.getMessage());
        }
    }

    // 3. 남은 학점 계산
    public static void calculateRemainingCredits() {
        int totalCredits = 0;

        // 각 시간표의 subjects 리스트를 순회하여 총 학점 계산
        List<Timetable> timetableList = TimetableManager.timetableList;
        for (Timetable timetable : timetableList) {
            for (Subject subject : timetable.getSubjects()) {
                totalCredits += subject.getCredit();
            }
        }

        int remainingCredits = GRADUATION_CREDIT - totalCredits;

        System.out.println("잔여 학점");
        System.out.println("---------");
        System.out.println("이수 학점: " + totalCredits + "학점");
        System.out.println("졸업 요건: " + GRADUATION_CREDIT + "학점");
        System.out.println("남은 학점: " + remainingCredits + "학점");
    }
}
