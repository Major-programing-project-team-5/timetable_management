package Core.Utils.Calc;

import Core.DataStructure.Subject;
import Core.Utils.findSubjectClass;

import java.io.*;

public class calc_utilitySets {
//    1. <calc> <total> 총 학점(전 학기 시간표 대상) 계산
//    2. <calc> <term> 특정 학기 내 학점 계산
//    3. <calc> <remain> 잔여 학점(전 학기 시간표 대상) 계산

    // calc 명령 종류인 것을 확인하고 이 함수를 호출.
    static final String DB_PATH = "data/database.csv";
    static final String TIMETABLE_DIR = "data/";
    static final int GRADUATION_CREDIT = 130;

    private final String[] tokens;
//    if (tokens.length < 2 || !tokens[0].equals("calc") || !tokens[0].equals("계산") || !tokens[0].equals("학점")) {
//        System.out.println("올바르지 않은 명령입니다.");
//        return;
//    } --> 삭제(메인함수에서 명령어 검증.)

    public calc_utilitySets(String userInput) {
        tokens = userInput.trim().split("\\s+");

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

    // 조건문에 사용되는 함수들
    // 1. 총 학점을 계산하는 함수
    public static void calculateTotalCredits() {
        int totalCredits = 0;

        // 디렉토리에서 시간표 파일 목록 불러오기 (파일명: *_timetable.csv)
        File folder = new File(TIMETABLE_DIR);
        File[] files = folder.listFiles((dir, name) -> name.endsWith("_timetable.csv"));

        if (files == null) {
            System.out.println("시간표 디렉토리를 읽을 수 없습니다.");
            return;
        }

        // 각 시간표 파일을 순회
        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;

                // 파일 내 각 줄마다 과목명을 읽고, 학점을 찾아 더함
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (tokens.length >= 1) {
                        String subjectName = tokens[0].trim(); // 과목명 추출
                        Subject subject = findSubjectClass.findSubject(new String[] {subjectName});
                        if (subject != null) {
                            totalCredits += subject.getCredit();
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("파일 읽기 오류: " + file.getName());
            }
        }

        // 총 학점 출력
        System.out.println("전체 이수 학점: " + totalCredits);
    }

//    // 2. 이수한 학기의 총 credit을 계산
//    private static int getCreditFromDatabase(String subjectName) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(DB_PATH))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] tokens = line.split(" ");
//                if (tokens.length >= 5 && tokens[0].equals(subjectName)) {
//                    return Integer.parseInt(tokens[4]); // 5번째 요소가 학점
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("데이터베이스 읽기 오류: " + e.getMessage());
//        }
//        return 0;
//    } --> findSubject 사용하면서 불필요해짐.

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
                    Subject subject = findSubjectClass.findSubject(new String[] {subjectName});
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

        File folder = new File(TIMETABLE_DIR);
        File[] files = folder.listFiles((dir, name) -> name.endsWith("_timetable.csv"));

        if (files == null) {
            System.out.println("시간표 파일을 찾을 수 없습니다.");
            return;
        }

        // 모든 시간표 파일을 읽어서 총 학점 계산
        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (tokens.length >= 1) {
                        String subjectName = tokens[0].trim();
                        Subject subject = findSubjectClass.findSubject(new String[]{subjectName});
                        if (subject != null) {
                            totalCredits += subject.getCredit();
                        }
                    }
                }

            } catch (IOException e) {
                System.out.println("파일 읽기 오류: " + file.getName());
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


