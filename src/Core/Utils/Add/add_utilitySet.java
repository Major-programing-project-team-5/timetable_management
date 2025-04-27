package Core.Utils.Add;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Core.DataStructure.*;
import Core.Utils.*;

public class add_utilitySet {
    // 명령어가 add인 것을 외부에서 확인 후 접근.
    public static void AddCommand(String input){
        String[] tokens = input.trim().split("[ \t\n\r\f\u000B]");
        String currentTable = null;


    }

    // 현재 시간표에 과목 추가
    public static void addSubjectToCurrentTimetable(String[] subjectInfo, String current) {
        // subjectInfo 배열에서 과목 정보 추출
        Subject foundSubject = findSubjectClass.findSubject(subjectInfo);

        if (foundSubject == null) {
            System.out.println("일치하는 과목을 데이터베이스에서 찾을 수 없습니다.");
            return;
        }

        // 현재 시간표에 과목 추가 시 시간 겹침 체크
        if (isTimeConflicted(current, foundSubject)) {
            System.out.println("시간이 겹치는 과목이 이미 있습니다.");
            return;
        }

        // 과목을 현재 시간표에 추가
        Timetable currentTimetable = getCurrentTimetable(current);  // current로 시간표 객체 가져오기
        if (currentTimetable != null) {
            currentTimetable.addSubject(foundSubject);
            System.out.println("과목이 시간표에 추가되었습니다: " + foundSubject);
        } else {
            System.out.println("현재 시간표를 찾을 수 없습니다.");
        }
    }

    // 과목이 시간표에 겹치는지 체크
    public static boolean isTimeConflicted(String current, Subject subject) {
        Timetable timetable = getCurrentTimetable(current);
        if (timetable == null) {
            return false;
        }

        for (Subject existingSubject : timetable.getSubjects()) {
            // 기존 시간표의 과목과 비교하여 시간 겹침 여부를 확인
            if (existingSubject.getSubjectCode().equals(subject.getSubjectCode()) ||
                    existingSubject.getCategory().equals(subject.getCategory())) {
                return true; // 겹치는 과목이 존재하면 true 반환
            }
        }
        return false;  // 겹치는 과목이 없으면 false
    }

    // 현재 시간표 객체를 반환하는 메서드 (current를 통해 시간표를 찾는 로직)
    public static Timetable getCurrentTimetable(String current) {
        for (Timetable timetable : TimetableManager.timetableList) {
            if (timetable.equals(current)) {
                return timetable;
            }
        }
        return null;  // 시간표가 없으면 null 반환
    }

    // 데이터베이스에 과목을 추가하는 메서드
    public static void addSubjectToDatabase(String[] lectureInfo) {
        List<String> previousSubjectCode = new ArrayList<>();
        if (lectureInfo.length < 8) {
            System.out.println("과목 정보가 부족합니다.");
            return;
        } else if (lectureInfo.length == 8) {
            previousSubjectCode.add(null);
        }
        else {
            previousSubjectCode = Arrays.asList(lectureInfo).subList(8, lectureInfo.length);
        }

        // 과목 정보에서 튜플을 생성하고 데이터베이스에 추가
        String[] lectureDate = Arrays.copyOfRange(lectureInfo, 1, 2);
        Subject subject = new Subject(lectureInfo[0], lectureDate, lectureInfo[3], Integer.parseInt(lectureInfo[4]), lectureInfo[5], lectureInfo[6], lectureInfo[7], previousSubjectCode);
        boolean success = subjectManager.addSubjectToManager(subject);
        if (success) {
            System.out.println("과목이 데이터베이스에 추가되었습니다.");
        } else {
            System.out.println("과목 추가에 실패했습니다.");
        }
    }

    // 시간표 생성 메서드 (기존 로직 사용)
    public static void createTimetable(int year, int semester) {
        String dirName = "data";
        String fileName = year + "_" + semester + "_timetable.csv";
        File dir = new File(dirName);
        File file = new File(dirName + "/" + fileName);

        if (!dir.exists()) {
            dir.mkdir();
        }

        if (file.exists()) {
            System.out.println("이미 존재하는 시간표입니다: " + file.getName());
            return;
        }

        try (FileWriter writer = new FileWriter(file)) {
            System.out.println(year + "학년 " + semester + "학기 시간표가 생성되었습니다.");
        } catch (IOException e) {
            System.out.println("시간표 파일 생성 중 오류 발생: " + e.getMessage());
        }
    }

    // 현재 시간표를 설정하는 메서드
    public static String createAndSetCurrentTimetable(int year, int semester) {
        String dirName = "data";
        String fileName = year + "_" + semester + "_timetable.csv";
        File dir = new File(dirName);
        File file = new File(dirName + "/" + fileName);

        if (!dir.exists()) {
            dir.mkdir();
        }

        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                System.out.println(year + "학년 " + semester + "학기 시간표가 현재 시간표로 설정됨.");
                return fileName;
            } catch (IOException e) {
                System.out.println("파일 생성 중 오류: " + e.getMessage());
                return null;
            }
        } else {
            System.out.println(year + "학년 " + semester + "학기 시간표가 현재 시간표로 설정됨.");
            return fileName;
        }
    }

    // 유틸 함수
    private static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}
