package Core.Utils.Add;

import java.io.*;
import java.util.Arrays;

import Core.DataStructure.*;

public class add_utilitySet {
    public static void AddCommand(String input) {
        String[] tokens = input.trim().split(" ");
        String currentTable = null;

        try {
            if (isNumeric(tokens[1]) && isNumeric(tokens[2])) {
                // 1. 지정 학기 시간표 생성 (학년, 학기)
                int year = Integer.parseInt(tokens[1]);
                int semester = Integer.parseInt(tokens[2]);
                createTimetable(year, semester);

            } else if (tokens[1].equals("current") && isNumeric(tokens[2]) && isNumeric(tokens[3])) {
                // 2. 현재 시간표 생성 및 설정
                int year = Integer.parseInt(tokens[2]);
                int semester = Integer.parseInt(tokens[3]);
                currentTable = createAndSetCurrentTimetable(year, semester);

            } else if (tokens[1].equals("current") && tokens.length > 2) {
                // 3. 시간표에 과목 추가
                String[] subjectInfo = Arrays.copyOfRange(tokens, 2, tokens.length);
                addSubjectToCurrentTimetable(subjectInfo, currentTable);

            } else if (tokens[1].equals("과목")) {
                // 4. 데이터베이스에 과목 추가
                String[] lectureInfo = Arrays.copyOfRange(tokens, 2, tokens.length);
                addSubjectToDatabase(lectureInfo);

            } else {
                System.out.println("잘못된 add 명령 형식입니다.");
            }

        } catch (Exception e) {
            System.out.println("명령어 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 시간표 생성 메서드
    public static void createTimetable(int year, int semester) {
        // 기존 시간표 파일을 읽어와 해당 학기의 시간표가 존재하는지 확인 후 없으면 새로 추가
        String filename = "timetable.txt";
        Timetable newTimetable = new Timetable(year, semester, new ArrayList<>());
        TimetableManager.addTimeTabletoManager(newTimetable);

        // 이 파일에 시간표 정보를 저장
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(newTimetable.toString());
            System.out.println(year + "학기 " + semester + " 시간표가 생성되었습니다.");
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 과목을 현재 시간표에 추가하는 메서드
    public static void addSubjectToCurrentTimetable(String[] subjectInfo, String current) {
        Subject foundSubject = findSubjectClass.findSubject(subjectInfo);

        if (foundSubject == null) {
            System.out.println("일치하는 과목을 데이터베이스에서 찾을 수 없습니다.");
            return;
        }

        // 현재 시간표에 과목 추가
        Timetable currentTimetable = getCurrentTimetable(current);  // current로 시간표 객체 가져오기
        if (currentTimetable != null) {
            if (!currentTimetable.getSubjects().contains(foundSubject)) {
                currentTimetable.getSubjects().add(foundSubject);
                System.out.println("과목이 시간표에 추가되었습니다: " + foundSubject);
            } else {
                System.out.println("이미 해당 과목이 시간표에 존재합니다.");
            }
        } else {
            System.out.println("현재 시간표를 찾을 수 없습니다.");
        }
    }

    // 현재 시간표 객체를 반환하는 메서드
    public static Timetable getCurrentTimetable(String current) {
        for (Timetable timetable : TimetableManager.timetableList) {
            if (timetable.equals(current)) {
                return timetable;
            }
        }
        return null;  // 시간표가 없으면 null 반환
    }

    // 데이터베이스에 과목 추가하는 메서드
    public static void addSubjectToDatabase(String[] subjectInfo) {
        // 과목 정보를 데이터베이스에 저장하는 로직 추가
        // 예를 들어, subjectInfo가 과목명, 교수명, 학점 등을 포함한다고 가정

        // 각 항목을 공백으로 구분하여 데이터베이스에 추가
        if (subjectInfo.length != 9) {
            System.out.println("잘못된 과목 정보입니다. 모든 항목을 입력해야 합니다.");
            return;
        }

        String subjectName = subjectInfo[0];   // 과목명
        String dayOfWeek = subjectInfo[1];     // 요일
        String time = subjectInfo[2];          // 시간
        String subjectNumber = subjectInfo[3]; // 과목번호
        int credit = Integer.parseInt(subjectInfo[4]);  // 학점
        String courseType = subjectInfo[5];    // 이수구분
        String semester = subjectInfo[6];      // 학기
        String courseCode = subjectInfo[7];    // 학수번호
        String classroom = subjectInfo[8];     // 강의실

        // 데이터베이스에 과목을 추가하는 코드
        // 예를 들어, CSV 파일에 저장
        String dbPath = "data/database.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dbPath, true))) {
            writer.write(subjectName + " " + dayOfWeek + " " + time + " " + subjectNumber + " " +
                    credit + " " + courseType + " " + semester + " " + courseCode + " " + classroom + "\n");
            System.out.println("과목이 데이터베이스에 추가되었습니다: " + subjectName);
        } catch (IOException e) {
            System.out.println("데이터베이스에 과목을 추가하는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
