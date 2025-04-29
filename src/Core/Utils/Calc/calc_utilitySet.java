package Core.Utils.Calc;

import Core.DataStructure.Subject;
import Core.DataStructure.Timetable;
import Core.DataStructure.TimetableManager;

public class calc_utilitySet {
//    1. <calc> <total> 총 학점(전 학기 시간표 대상) 계산
//    2. <calc> <term> 특정 학기 내 학점 계산
//    3. <calc> <remain> 잔여 학점(전 학기 시간표 대상) 계산

    static final int GRADUATION_CREDIT = 130;

    //    if (tokens.length < 2 || !tokens[0].equals("calc") || !tokens[0].equals("계산") || !tokens[0].equals("학점")) {
//        System.out.println("올바르지 않은 명령입니다.");
//        return;
//    } --> 삭제(메인함수에서 명령어 검증.)

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
                    if (tokens[2].matches("\\d") || tokens[3].matches("\\d")) {
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
    public static void calculateTotalCredits() {
        try {
            int totalCredits = 0;

            if (TimetableManager.timetableList.isEmpty()) {
                System.out.println("시간표가 존재하지 않습니다.");
                return;
            }

            // 각 시간표 파일을 순회
            for (Timetable timetable : TimetableManager.timetableList) {
                for (Subject subject : timetable.getSubjects()) {
                    totalCredits += subject.getCredit();
                }
            }

            // 총 학점 출력
            System.out.println("전체 이수 학점: " + totalCredits);
        } catch (Exception e) {
            System.out.println("학점 계산 실패" + e);
        }
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
        try {
            int totalCredits = 0;
            Timetable timetable = TimetableManager.getTimetable(year, semester);

            if (timetable == null) {
                System.out.println("시간표가 존재하지 않습니다.");
                return;
            }

            // 각 시간표 파일을 순회
            for (Subject subject : timetable.getSubjects()) {
                totalCredits += subject.getCredit();
            }

            // 총 학점 출력
            System.out.println("학기 이수 학점: " + totalCredits);
        } catch (Exception e) {
            System.out.println("학점 계산 실패" + e);
        }
    }

    // 3. 남은 학점 계산
    public static void calculateRemainingCredits() {
        try {
            int totalCredits = 0;

            if (TimetableManager.timetableList.isEmpty()) {
                System.out.println("시간표가 존재하지 않습니다.");
                return;
            }

            // 각 시간표 파일을 순회
            for (Timetable timetable : TimetableManager.timetableList) {
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
        } catch (Exception e) {
            System.out.println("학점 계산 실패" + e);
        }
    }
}


