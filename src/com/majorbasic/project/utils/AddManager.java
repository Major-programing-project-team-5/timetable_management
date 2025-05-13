package Core.Utils.Add;

import Core.DataStructure.Subject;
import Core.DataStructure.Timetable;
import Core.DataStructure.TimetableManager;
import Core.DataStructure.subjectManager;
import Core.Utils.findSubjectClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class add_promptSet {
    public void addMain(String args) {
        String[] tokens = args.split("\\s+");
        Select_add_prompt(tokens);
    }

    public void Select_add_prompt(String[] tokens) {
        try {
            if (tokens.length < 3) {
                System.out.println("잘못된 add 명령 형식입니다.");
            } else if ( tokens.length == 3 && isNumeric(tokens[1]) && isNumeric(tokens[2])  ) {
                // 1. 지정 학기 시간표 생성 (학년, 학기) - 정상 동작
                int year = Integer.parseInt(tokens[1]);
                int semester = Integer.parseInt(tokens[2]);
                if(year > 4 || year < 1 || semester > 2 || semester < 1) {
                    System.out.println("학년 또는 학기의 숫자가 범위를 넘어섰습니다");
                    return;
                }
                print_add_timetable(year, semester);
            } else if (tokens[1].equals("current") && isNumeric(tokens[2]) && isNumeric(tokens[3])) {
                // 2. 현재 시간표 생성 및 설정 -- 정상 동작
                int year = Integer.parseInt(tokens[2]);
                int semester = Integer.parseInt(tokens[3]);
                print_add_timetable_setcurrent(year, semester);
            } else if (tokens[1].equals("current") && tokens.length > 5) {
                // 3. 시간표에 과목 추가 -- 정상 동작
                String[] subjectInfo = Arrays.copyOfRange(tokens, 2, tokens.length);
                print_add_course_current(subjectInfo);
            } else if (tokens[1].equals("subject")) {
                // 4. 데이터베이스에 과목 추가 -- 정상 동작
                String[] lectureInfo = Arrays.copyOfRange(tokens, 2, tokens.length);
                print_add_course_database(lectureInfo);
            } else if (isNumeric(tokens[1]) && isNumeric(tokens[2]) && tokens.length > 5) {
                // 5. add 학년 학기 <과목 정보> 로 입력시 해당 시간표에 과목 추가 -- 정상 동작
                int year = Integer.parseInt(tokens[1]);
                int semester = Integer.parseInt(tokens[2]);
                String[] lectureInfo = Arrays.copyOfRange(tokens, 3, tokens.length);
                print_add_course_timetable(year, semester, lectureInfo);
            } else if (tokens[1].equals("past") && isNumeric(tokens[2]) && isNumeric(tokens[3]) && tokens.length == 4) {
                int year = Integer.parseInt(tokens[2]);
                int semester = Integer.parseInt(tokens[3]);
                Timetable tmp = TimetableManager.getTimetable(year, semester);
                if (tmp == null) {
                    System.out.println("대상 시간표가 존재하지 않습니다.");
                    return;
                }
                TimetableManager.presentTimetable = TimetableManager.getTimetable(year, semester);
                System.out.println("[ 현재 시간표가 " + year + "학년 " + semester + "학기로 설정되었습니다. ]");
            } else {
                System.out.println("잘못된 add 명령 형식입니다.");
            }
        } catch (Exception e) {
            System.out.println("명령어 처리 중 오류가 발생했습니다 " + e.toString());
        }
    }



    public void print_add_timetable(int year, int semester) {
        Timetable temp = new Timetable(year, semester);
        if(TimetableManager.timetableSets.contains(temp)){
            System.out.println("이미 존재하는 시간표입니다");
        }else{
            TimetableManager.addTimeTabletoManager(temp);
            System.out.println("[ " + year + "학년 " + semester + "학기 시간표가 생성되었습니다. ]");
        }

    }

    public void print_add_timetable_setcurrent(int year, int semester) {
        Timetable temp = new Timetable(year, semester);
        if (!TimetableManager.timetableSets.contains(temp)) {
            TimetableManager.addTimeTabletoManager(temp);
            System.out.println("[ " + year + "학년 " + semester + "학기 시간표가 생성되었습니다. ]");
        } else {
            for (Timetable i : TimetableManager.timetableList) {
                if (i.equals(temp)) {
                    temp = i;
                    break;
                }
            }
        }
        TimetableManager.presentTimetable = temp;
        System.out.println("[ 현재 시간표가 " + year + "학년 " + semester + "학기로 설정되었습니다. ]");
    }

    public void print_add_course_current(String[] tuples) {
        Subject temp = findSubjectClass.findSubject(tuples);
        if (temp == null) {
            System.out.println("잘못된 과목 튜플 입력입니다.");
        } else if (isTimeConflicted(temp, TimetableManager.presentTimetable)) {
            System.out.println("겹치는 강의가 있습니다.");
        } else {
            TimetableManager.presentTimetable.addSubject(temp);
            System.out.println("[ 과목 '" + temp.toString() + "'가 현재 시간표에 추가되었습니다.]");
        }
    }

    public void print_add_course_database(String[] lectureInfo) {
        if (lectureInfo.length < 8) {
            System.out.println("과목 정보가 부족합니다.");
            return;
        }

        List<String> previousSubjectCode = new ArrayList<>();
        if (lectureInfo.length > 8) {
            previousSubjectCode = Arrays.asList(lectureInfo).subList(8, lectureInfo.length);
        }

        String[] days = lectureInfo[1].split(",");   // 예: ["화", "목"]
        String[] times = lectureInfo[2].split(",");  // 예: ["10:30~12:00", "10:30~12:00"] 또는 ["10:30~12:00"]

        String[] lectureDate = new String[days.length];

        if (times.length == 1) {
            // 모든 요일에 동일한 시간 적용
            for (int i = 0; i < days.length; i++) {
                lectureDate[i] = days[i] + "," + times[0];
            }
        } else if (times.length == days.length) {
            // 각 요일마다 각각 시간 적용
            for (int i = 0; i < days.length; i++) {
                lectureDate[i] = days[i] + "," + times[i];
            }
        } else {
            System.out.println("요일과 시간 정보가 맞지 않습니다.");
            return;
        }

        Subject subject = new Subject(
                lectureInfo[0],              // 과목명
                lectureDate,                 // 요일,시간 쌍 배열
                lectureInfo[3],              // 과목코드
                Integer.parseInt(lectureInfo[4]), // 학점
                lectureInfo[5],              // 이수구분
                lectureInfo[6],              // 학수번호
                lectureInfo[7],              // 강의실
                previousSubjectCode.isEmpty() ? null : previousSubjectCode
        );

        boolean success = subjectManager.addSubjectToManager(subject);
        if (success) {
            System.out.println("과목이 데이터베이스에 추가되었습니다.");
        } else {
            System.out.println("과목 추가에 실패했습니다.");
        }
    }

    // 확인
    public void print_add_course_timetable(int year, int semester, String[] lectureInfo) {
        Subject temp = findSubjectClass.findSubject(lectureInfo);
        if (temp == null) {
            System.out.println("Subject가 없습니다.");
        }
        Timetable table = TimetableManager.getTimetable(year, semester);
        if (table == null) {
            System.out.println("Timetable이 없습니다.");
        }


        if(table == null){
            System.out.println("존재하지 않는 시간표입니다.");
            return;
        } else if(temp == null){
            System.out.println("잘못된 과목 튜플 입력입니다.");
            return;
        } else if (isTimeConflicted(temp, table)) {
            System.out.println("겹치는 강의가 있습니다.");
        } else {
            table.addSubject(temp);
            System.out.println("[ 과목 '" + temp.toString() + "'가 시간표에 추가되었습니다.]");
        }

    }

    public static boolean isTimeConflicted(Subject subject, Timetable timetable) {
        if (timetable == null) {
            return false;
        }

        for (Subject existingSubject : timetable.getSubjects()) {
            // 같은 과목 코드면 중복으로 간주
            if (existingSubject.getSubjectCode().equals(subject.getSubjectCode())) {
                return true;
            }

            for (String s1 : subject.getSubjectDayTime()) {
                String[] s1Parts = s1.split(",");
                String day1 = s1Parts[0];
                String time1 = s1Parts[1];

                for (String s2 : existingSubject.getSubjectDayTime()) {
                    String[] s2Parts = s2.split(",");
                    String day2 = s2Parts[0];
                    String time2 = s2Parts[1];

                    // 요일이 같고 시간이 겹치면 충돌로 간주
                    if (day1.equals(day2) && isOverlapping(time1, time2)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static boolean isOverlapping(String timeRange1, String timeRange2) {
        int start1 = toMinutes(timeRange1.split("~")[0]);
        int end1 = toMinutes(timeRange1.split("~")[1]);

        int start2 = toMinutes(timeRange2.split("~")[0]);
        int end2 = toMinutes(timeRange2.split("~")[1]);

        return start1 < end2 && start2 < end1;
    }

    private static int toMinutes(String time) {
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return hour * 60 + minute;
    }


    private static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}






