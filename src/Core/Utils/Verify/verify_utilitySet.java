package Core.Utils;

import Core.DataStructure.Subject;
import Core.DataStructure.Timetable;
import Core.DataStructure.TimetableManager;
public class verify_utilitySet {

    public static void verifySubject(String[] tuples) {
        Subject subject = findSubjectClass.findSubject(tuples);

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
        System.out.println("학점: " + subject.getCredit());
        System.out.println("구분: " + subject.getCategory());
        System.out.println("강의실: " + subject.getLectureRoom());
        System.out.println("강의 코드: " + subject.getCourseCode());
    }

    public static void verifyTimetable(int year, int semester) {
        Timetable timetable = TimetableManager.getTimetable(year, semester);

        if (timetable == null) {
            System.out.println("해당 시간표를 찾을 수 없습니다.");
            return;
        }

        boolean[][] timetableArray = new boolean[5][15]; // 월(0)~금(4), 1~15교시

        for (Subject subject : timetable.getSubjects()) {
            for (String dayTime : subject.getSubjectDayTime()) {
                String[] parts = dayTime.split(",");
                if (parts.length != 2) continue;

                String day = parts[0];
                String[] timeParts = parts[1].split("~");
                int startTime = Integer.parseInt(timeParts[0].split(":")[0]);
                int endTime = Integer.parseInt(timeParts[1].split(":")[0]);

                int startPeriod = convertTimeToPeriod(startTime);
                int endPeriod = convertTimeToPeriod(endTime);

                int dayIndex = dayToIndex(day);

                if (dayIndex != -1) {
                    for (int i = startPeriod; i < endPeriod; i++) {
                        if (i >= 0 && i < 15) {
                            timetableArray[dayIndex][i] = true;
                        }
                    }
                }
            }
        }

        // 출력
        System.out.println("==== 시간표 (" + year + "년 " + semester + "학기) ====");
        System.out.print("교시\\요일\t월\t화\t수\t목\t금\n");
        for (int period = 0; period < 15; period++) {
            System.out.print((period + 1) + "교시\t");
            for (int day = 0; day < 5; day++) {
                System.out.print((timetableArray[day][period] ? "●" : "○") + "\t");
            }
            System.out.println();
        }
    }

    private static int convertTimeToPeriod(int hour) {
        // 간단히 시간대별 교시 매칭
        // 9시 -> 1교시, 10시 -> 2교시, 11시 -> 3교시... 가정
        return hour - 9;
    }

    private static int dayToIndex(String day) {
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
